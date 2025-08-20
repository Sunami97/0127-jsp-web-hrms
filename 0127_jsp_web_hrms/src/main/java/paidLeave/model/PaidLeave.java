package paidLeave.model;

import java.util.Date;

public class PaidLeave {
    private Integer leaveId;        // 有給休暇申請の固有ID
    private String userId;          // 申請者ID
    private Date startDate;         // 有給開始日
    private Date endDate;           // 有給終了日
    private double days;            // 使用日数（0.5日単位）
    private String status;          // ステータス（申請中、承認、却下など）
    private String reason;          // 申請理由
    private Date appliedAt;         // 申請日
    private String approvedBy;      // 承認者ID（null可）
    private Date approvedAt;        // 承認日（null可）

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
