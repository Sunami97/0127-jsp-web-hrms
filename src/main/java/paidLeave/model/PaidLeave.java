package paidLeave.model;

import java.util.Date;

public class PaidLeave {
    private Integer leaveId;        // 연차 내역 고유 ID
    private String userId;          // 신청자 ID
    private Date startDate;         // 연차 시작일
    private Date endDate;           // 연차 종료일
    private double days;            // 사용 일수 (0.5일 단위)
    private String status;          // 상태 (신청중, 승인, 반려 등)
    private String reason;          // 신청 사유
    private Date appliedAt;         // 신청 일자
    private String approvedBy;      // 승인자 ID (nullable)
    private Date approvedAt;        // 승인 일자 (nullable)

    public PaidLeave(Integer leaveId, String userId, Date startDate, Date endDate, double days, String status, String reason, Date appliedAt, String approvedBy, Date approvedAt) {
        this.leaveId = leaveId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.status = status;
        this.reason = reason;
        this.appliedAt = appliedAt;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
    }

    public Date getAppliedAt() {
        return appliedAt;
    }

    public Date getApprovedAt() {
        return approvedAt;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public double getDays() {
        return days;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }
}
