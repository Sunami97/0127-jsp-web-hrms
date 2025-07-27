package paidLeave.dao;

import java.sql.*;
import java.util.Date;

import jdbc.JdbcUtil;
import paidLeave.model.PaidLeave;

public class PaidLeaveDao {
    public PaidLeave insert(Connection conn, PaidLeave paidleave) throws SQLException {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO paid_leave_tbl (user_id, start_date, end_date, days, status, reason, applied_at, approved_by, approved_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, paidleave.getUserId());
            pstmt.setTimestamp(2, toTimestamp(paidleave.getStartDate()));
            pstmt.setTimestamp(3, toTimestamp(paidleave.getEndDate()));
            pstmt.setDouble(4, paidleave.getDays());
            pstmt.setString(5, paidleave.getStatus());
            pstmt.setString(6, paidleave.getReason());

            // appliedAt이 null이면 현재 시간으로 대체 가능 (DB에서 DEFAULT SYSDATE도 처리되지만 명시 가능)
            if (paidleave.getAppliedAt() != null) {
                pstmt.setTimestamp(7, toTimestamp(paidleave.getAppliedAt()));
            } else {
                pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            }

            // approved_by와 approved_at은 null 허용
            pstmt.setString(8, paidleave.getApprovedBy());

            if (paidleave.getApprovedAt() != null) {
                pstmt.setTimestamp(9, toTimestamp(paidleave.getApprovedAt()));
            } else {
                pstmt.setNull(9, Types.TIMESTAMP);
            }

            int insertedCount = pstmt.executeUpdate();

            // 트리거로 leave_id가 자동 생성되었으므로 다시 조회 필요
            if (insertedCount > 0) {
                // 마지막 insert된 leave_id를 조회
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_id() FROM paid_leave_tbl");

                if (rs.next()) {
                    Integer leaveId = rs.getInt(1);
                    return new PaidLeave(
                            leaveId,
                            paidleave.getUserId(),
                            paidleave.getStartDate(),
                            paidleave.getEndDate(),
                            paidleave.getDays(),
                            paidleave.getStatus(),
                            paidleave.getReason(),
                            paidleave.getAppliedAt(),
                            paidleave.getApprovedBy(),
                            paidleave.getApprovedAt()
                    );
                }
            }

            return null;

        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
            JdbcUtil.close(pstmt);
        }
    }

    private Timestamp toTimestamp (Date date) {
        return new Timestamp(date.getTime());
    }
}
