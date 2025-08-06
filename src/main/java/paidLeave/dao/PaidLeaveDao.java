package paidLeave.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            pstmt.setTimestamp(7, toTimestamp(paidleave.getAppliedAt()));
            pstmt.setString(8, paidleave.getApprovedBy());

            if (paidleave.getApprovedAt() != null) {
                pstmt.setTimestamp(9, toTimestamp(paidleave.getApprovedAt()));
            } else {
                pstmt.setTimestamp(9, null);
            }

            int insertedCount = pstmt.executeUpdate();

            // 트리거로 leave_id가 자동 생성되었으므로 다시 조회 필요
            if (insertedCount > 0) {
                // 마지막 insert된 leave_id를 조회
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT seq_leave_id.CURRVAL FROM dual");

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
        if (date == null) {
            return null; // null 처리
        }
        return new Timestamp(date.getTime());
    }
}
