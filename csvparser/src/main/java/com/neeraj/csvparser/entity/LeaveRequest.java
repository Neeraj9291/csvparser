package com.neeraj.csvparser.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import io.micrometer.common.lang.NonNull;

@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NonNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NonNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "Leave type is required")
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;
    
    private String comments;
    
    public enum LeaveType {
        ANNUAL("Annual Leave"),
        SICK("Sick Leave"),
        PERSONAL("Personal Leave"),
        MATERNITY("Maternity Leave"),
        PATERNITY("Paternity Leave"),
        BEREAVEMENT("Bereavement Leave"),
        OTHER("Other");
        
        private final String displayName;
        
        LeaveType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum Status {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        CANCELLED("Cancelled");
        
        private final String displayName;
        
        Status(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public LeaveRequest() {}
    
    public LeaveRequest(User user, LocalDate startDate, LocalDate endDate, LeaveType leaveType, String reason) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
    
    public LeaveType getLeaveType() {
        return leaveType;
    }
    
    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public User getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    // Helper methods
    public long getNumberOfDays() {
        return startDate.until(endDate.plusDays(1), ChronoUnit.DAYS);
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 