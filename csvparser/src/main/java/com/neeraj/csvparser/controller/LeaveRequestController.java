package com.neeraj.csvparser.controller;

import com.neeraj.csvparser.dto.LeaveRequestDto;
import com.neeraj.csvparser.entity.LeaveRequest;
import com.neeraj.csvparser.entity.User;
import com.neeraj.csvparser.service.LeaveRequestService;
import com.neeraj.csvparser.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/leave")
public class LeaveRequestController {

    private static final Logger logger = LoggerFactory.getLogger(LeaveRequestController.class);

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private UserService userService;

    private Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    @GetMapping("/request")
    public String showLeaveRequestForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequestDto());
        model.addAttribute("leaveTypes", LeaveRequest.LeaveType.values());
        return "leave/request-form";
    }

    @PostMapping("/request")
    public String submitLeaveRequest(@Valid @ModelAttribute("leaveRequest") LeaveRequestDto leaveRequestDto,
                                     BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("leaveTypes", LeaveRequest.LeaveType.values());
            return "leave/request-form";
        }

        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/leave/request";
        }

        User user = userOpt.get();

        // Overlapping check
        if (leaveRequestService.hasOverlappingLeave(user, leaveRequestDto.getStartDate(),
                leaveRequestDto.getEndDate(), null)) {
            result.rejectValue("startDate", "error.leaveRequest", "You have overlapping leave requests for these dates");
            model.addAttribute("leaveTypes", LeaveRequest.LeaveType.values());
            return "leave/request-form";
        }

        LeaveRequest leaveRequest = new LeaveRequest(user,
                leaveRequestDto.getStartDate(),
                leaveRequestDto.getEndDate(),
                leaveRequestDto.getLeaveType(),
                leaveRequestDto.getReason());

        leaveRequestService.saveLeaveRequest(leaveRequest);
        redirectAttributes.addFlashAttribute("success", "Leave request submitted successfully");

        return "redirect:/leave/my-requests";
    }

    @GetMapping("/my-requests")
    public String showMyLeaveRequests(Model model, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<LeaveRequest> leaveRequests = leaveRequestService.findByUser(user);
            model.addAttribute("leaveRequests", leaveRequests);
        } else {
            redirectAttributes.addFlashAttribute("error", "Unable to load user leave requests.");
        }
        return "leave/my-requests";
    }

    @GetMapping("/requests")
    public String showAllLeaveRequests(Model model, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<LeaveRequest> leaveRequests;

            switch (user.getRole()) {
                case ADMIN:
                    leaveRequests = leaveRequestService.findAll();
                    break;
                case MANAGER:
                    leaveRequests = leaveRequestService.findByUserRole(User.Role.EMPLOYEE);
                    break;
                default:
                    return "redirect:/leave/my-requests";
            }

            model.addAttribute("leaveRequests", leaveRequests);
        } else {
            redirectAttributes.addFlashAttribute("error", "Unable to fetch leave requests.");
        }
        return "leave/all-requests";
    }

    @GetMapping("/requests/{id}")
    public String showLeaveRequestDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<LeaveRequest> leaveRequestOpt = leaveRequestService.findById(id);

        if (leaveRequestOpt.isPresent()) {
            model.addAttribute("leaveRequest", leaveRequestOpt.get());
            return "leave/request-details";
        }

        redirectAttributes.addFlashAttribute("error", "Leave request not found.");
        return "redirect:/leave/requests";
    }

    @PostMapping("/requests/{id}/approve")
    public String approveLeaveRequest(@PathVariable Long id, @RequestParam String comments,
                                      RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            User approver = userOpt.get();
            LeaveRequest approvedRequest = leaveRequestService.approveLeaveRequest(id, approver, comments);

            if (approvedRequest != null) {
                redirectAttributes.addFlashAttribute("success", "Leave request approved successfully.");
                logger.info("Leave request {} approved by {}", id, approver.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to approve leave request.");
            }
        }

        return "redirect:/leave/requests";
    }

    @PostMapping("/requests/{id}/reject")
    public String rejectLeaveRequest(@PathVariable Long id, @RequestParam String comments,
                                     RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            User approver = userOpt.get();
            LeaveRequest rejectedRequest = leaveRequestService.rejectLeaveRequest(id, approver, comments);

            if (rejectedRequest != null) {
                redirectAttributes.addFlashAttribute("success", "Leave request rejected successfully.");
                logger.info("Leave request {} rejected by {}", id, approver.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to reject leave request.");
            }
        }

        return "redirect:/leave/requests";
    }

    @PostMapping("/requests/{id}/cancel")
    public String cancelLeaveRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            LeaveRequest cancelledRequest = leaveRequestService.cancelLeaveRequest(id, user);

            if (cancelledRequest != null) {
                redirectAttributes.addFlashAttribute("success", "Leave request cancelled successfully.");
                logger.info("Leave request {} cancelled by {}", id, user.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("error", "Failed to cancel leave request.");
            }
        }

        return "redirect:/leave/my-requests";
    }
}
