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

    public List<PaidLeave> select(Connection conn, int startRow, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from (select inner_query.*, rownum as rnum from(select * from paid_leave_tbl order by leave_id desc) inner_query where rownum <= ?) where rnum > ?");

            int endRow = startRow + size;
            pstmt.setInt(1, endRow);
            pstmt.setInt(2, startRow);
            rs = pstmt.executeQuery();

            List<PaidLeave> result = new ArrayList<>();
            while (rs.next()) {
                result.add(convertPaidLeave(rs));
            }
            return result;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    private PaidLeave convertPaidLeave(ResultSet rs) throws SQLException {
        return new PaidLeave(rs.getInt("leave_id"),
                rs.getString("user_id"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getDouble("days"),
                rs.getString("status"),
                rs.getString("reason"),
                rs.getDate("applied_at"),
                rs.getString("approved_by"),
                rs.getDate("approved_at"));
    }

    public int selectCount(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM paid_leave_tbl");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }
    }

    private Timestamp toTimestamp (Date date) {
        if (date == null) {
            return null; // null 처리
        }
        return new Timestamp(date.getTime());
    }
}
