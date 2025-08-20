package com.neeraj.csvparser.dto;

import com.neeraj.csvparser.entity.LeaveRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaveRequestDto {
    
    private Long id;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "Leave type is required")
    private LeaveRequest.LeaveType leaveType;
    
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;
    
    private LeaveRequest.Status status;
    private String userFullName;
    private String approverFullName;
    private String comments;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Constructors
    public LeaveRequestDto() {}
    
    public LeaveRequestDto(LocalDate startDate, LocalDate endDate, LeaveRequest.LeaveType leaveType, String reason) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.reason = reason;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public LeaveRequest.LeaveType getLeaveType() {
        return leaveType;
    }
    
    public void setLeaveType(LeaveRequest.LeaveType leaveType) {
        this.leaveType = leaveType;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public LeaveRequest.Status getStatus() {
        return status;
    }
    
    public void setStatus(LeaveRequest.Status status) {
        this.status = status;
    }
    
    public String getUserFullName() {
        return userFullName;
    }
    
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
    
    public String getApproverFullName() {
        return approverFullName;
    }
    
    public void setApproverFullName(String approverFullName) {
        this.approverFullName = approverFullName;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Helper methods
    public long getNumberOfDays() {
        if (startDate != null && endDate != null) {
            return startDate.until(endDate.plusDays(1), ChronoUnit.DAYS);
        }
        return 0;
    }
} 