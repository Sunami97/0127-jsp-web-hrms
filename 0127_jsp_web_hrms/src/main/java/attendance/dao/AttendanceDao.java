package attendance.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import attendance.model.AttendanceRecord;

public class AttendanceDao {

    public boolean existsOpenStatus(Connection c, String userId) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM user_status_tbl WHERE user_id=? AND is_current='Y'";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    /** 출근(새 기록 시작) */
    public int insertStart(Connection c, String userId, String statusType) throws SQLException {
        final String sql = "INSERT INTO user_status_tbl (user_id, status_type, status_start, is_current) " +
                           "VALUES (?, ?, SYSDATE, 'Y')";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, statusType); // 예: 'WORK'
            return ps.executeUpdate();
        }
    }

    /** 퇴근(특정 status_id 종료) */
    public int closeById(Connection c, String userId, long statusId) throws SQLException {
        final String sql = "UPDATE user_status_tbl " +
                           "   SET status_end = SYSDATE, is_current = 'N' " +
                           " WHERE status_id = ? AND user_id = ? AND is_current = 'Y'";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, statusId);
            ps.setString(2, userId);
            return ps.executeUpdate();
        }
    }

    /** work_status 업데이트 (user_tbl) */
    public int updateWorkStatus(Connection c, String userId, String workStatus) throws SQLException {
        final String sql = "UPDATE user_tbl SET work_status = ? WHERE user_id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, workStatus);
            ps.setString(2, userId);
            return ps.executeUpdate();
        }
    }

    /** 최신순 전체 조회 */
    public List<AttendanceRecord> selectAllByUserDesc(Connection c, String userId) throws SQLException {
        final String sql = "SELECT status_id, user_id, status_type, status_start, status_end, is_current " +
                           "  FROM user_status_tbl WHERE user_id=? ORDER BY status_start DESC";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<AttendanceRecord> list = new ArrayList<>();
                while (rs.next()) {
                    AttendanceRecord r = new AttendanceRecord();
                    r.setStatusId(rs.getLong("status_id"));
                    r.setUserId(rs.getString("user_id"));
                    r.setStatusType(rs.getString("status_type"));
                    Timestamp s = rs.getTimestamp("status_start");
                    Timestamp e = rs.getTimestamp("status_end");
                    r.setStatusStart(s != null ? new java.util.Date(s.getTime()) : null);
                    r.setStatusEnd(e != null ? new java.util.Date(e.getTime()) : null);
                    r.setCurrent("Y".equals(rs.getString("is_current")));
                    list.add(r);
                }
                return list;
            }
        }
    }
    
    /** 해당 유저 출퇴근 내역 총 개수 */
    public int countByUser(Connection c, String userId) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM user_status_tbl WHERE user_id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    /** 페이지 조회 (최신순), Oracle 호환 ROWNUM 방식 */
    public List<attendance.model.AttendanceRecord> selectPageByUserDesc(
            Connection c, String userId, int startRow, int endRow) throws SQLException {

        final String sql =
            "SELECT * FROM (" +
            "  SELECT t.*, ROWNUM rn FROM (" +
            "    SELECT status_id, user_id, status_type, status_start, status_end, is_current " +
            "    FROM user_status_tbl WHERE user_id=? ORDER BY status_start DESC" +
            "  ) t" +
            ") WHERE rn BETWEEN ? AND ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setInt(2, startRow);
            ps.setInt(3, endRow);
            try (ResultSet rs = ps.executeQuery()) {
                List<attendance.model.AttendanceRecord> list = new java.util.ArrayList<>();
                while (rs.next()) {
                    attendance.model.AttendanceRecord r = new attendance.model.AttendanceRecord();
                    r.setStatusId(rs.getLong("status_id"));
                    r.setUserId(rs.getString("user_id"));
                    r.setStatusType(rs.getString("status_type"));
                    java.sql.Timestamp s = rs.getTimestamp("status_start");
                    java.sql.Timestamp e = rs.getTimestamp("status_end");
                    r.setStatusStart(s != null ? new java.util.Date(s.getTime()) : null);
                    r.setStatusEnd(e != null ? new java.util.Date(e.getTime()) : null);
                    r.setCurrent("Y".equals(rs.getString("is_current")));
                    list.add(r);
                }
                return list;
            }
        }
    }
}
	