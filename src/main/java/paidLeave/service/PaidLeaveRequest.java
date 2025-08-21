package paidLeave.service;

import java.util.Date;

public class PaidLeaveRequest {
    private String userId;
    private Date startDate;
    private Date endDate;
    private double days;
    private String reason;
    private String approvedBy;

    public PaidLeaveRequest(String userId, Date startDate, Date endDate, double days, String reason, String approvedBy) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.reason = reason;
        this.approvedBy = approvedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getUserId() {
        return userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getDays() {
        return days;
    }

    public String getReason() {
        return reason;
    }
}
