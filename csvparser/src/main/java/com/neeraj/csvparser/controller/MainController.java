package com.neeraj.csvparser.controller;

import com.neeraj.csvparser.entity.LeaveRequest;
import com.neeraj.csvparser.entity.User;
import com.neeraj.csvparser.service.LeaveRequestService;
import com.neeraj.csvparser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LeaveRequestService leaveRequestService;
    
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOpt = userService.findByUsername(auth.getName());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            
            // Get user's leave requests
            List<LeaveRequest> userRequests = leaveRequestService.findByUser(user);
            model.addAttribute("userRequests", userRequests);
            
            // Get pending requests count for managers/admins
            if (user.getRole() == User.Role.MANAGER || user.getRole() == User.Role.ADMIN) {
                List<LeaveRequest> pendingRequests = leaveRequestService.findByStatus(LeaveRequest.Status.PENDING);
                model.addAttribute("pendingRequests", pendingRequests);
                model.addAttribute("pendingCount", pendingRequests.size());
            }
            
            // Get statistics
            long approvedCount = leaveRequestService.findByUserAndStatus(user, LeaveRequest.Status.APPROVED).size();
            long rejectedCount = leaveRequestService.findByUserAndStatus(user, LeaveRequest.Status.REJECTED).size();
            long pendingCount = leaveRequestService.findByUserAndStatus(user, LeaveRequest.Status.PENDING).size();
            
            model.addAttribute("approvedCount", approvedCount);
            model.addAttribute("rejectedCount", rejectedCount);
            model.addAttribute("pendingCount", pendingCount);
        }
        
        return "dashboard";
    }
} 