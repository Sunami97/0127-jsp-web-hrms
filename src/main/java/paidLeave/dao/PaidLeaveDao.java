package paidLeave.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import paidLeave.model.PaidLeave;

public class PaidLeaveDao {
    // 有給休暇申請をデータベースに挿入する
    public PaidLeave insert(Connection conn, PaidLeave paidleave) throws SQLException {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // INSERT文の準備（leave_idはシーケンスで自動採番される）
            pstmt = conn.prepareStatement("INSERT INTO paid_leave_tbl (user_id, start_date, end_date, days, status, reason, applied_at, approved_by, approved_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // 各カラムに値をセット
            pstmt.setString(1, paidleave.getUserId());
            pstmt.setTimestamp(2, toTimestamp(paidleave.getStartDate()));
            pstmt.setTimestamp(3, toTimestamp(paidleave.getEndDate()));
            pstmt.setDouble(4, paidleave.getDays());
            pstmt.setString(5, paidleave.getStatus());
            pstmt.setString(6, paidleave.getReason());
            pstmt.setTimestamp(7, toTimestamp(paidleave.getAppliedAt()));
            pstmt.setString(8, paidleave.getApprovedBy());

            // 承認日がある場合は設定、ない場合はnull
            if (paidleave.getApprovedAt() != null) {
                pstmt.setTimestamp(9, toTimestamp(paidleave.getApprovedAt()));
            } else {
                pstmt.setTimestamp(9, null);
            }

            int insertedCount = pstmt.executeUpdate();

            // INSERT成功後、自動採番されたleave_idを取得
            if (insertedCount > 0) {
                stmt = conn.createStatement();
                // Oracleのシーケンスから直近の値を取得
                rs = stmt.executeQuery("SELECT seq_leave_id.CURRVAL FROM dual");

                if (rs.next()) {
                    Integer leaveId = rs.getInt(1);
                    return new PaidLeave(
                            // DBに保存された情報を元にPaidLeaveオブジェクトを返す
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

    // ページネーション用：指定範囲の有給休暇申請を取得
    public List<PaidLeave> select(Connection conn, int startRow, int size) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // ROWNUMを利用したページネーションSQL
            pstmt = conn.prepareStatement("select * from (select inner_query.*, rownum as rnum from(select * from paid_leave_tbl order by leave_id desc) inner_query where rownum <= ?) where rnum > ?");

            int endRow = startRow + size;
            pstmt.setInt(1, endRow);
            pstmt.setInt(2, startRow);
            rs = pstmt.executeQuery();

            List<PaidLeave> result = new ArrayList<>();
            // ResultSetから1件ずつレコードを取り出し、PaidLeaveオブジェクトに変換してリストへ追加
            while (rs.next()) {
                result.add(convertPaidLeave(rs));
            }
            // 指定されたページに対応する有給休暇申請のリストを返却
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

    // 全件数を取得（一覧のページングに使用）
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

    // 主キー（leave_id）で有給休暇申請を検索
    public PaidLeave selectById(Connection conn, int no) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM paid_leave_tbl WHERE leave_id = ?");
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();
            PaidLeave paidLeave = null;
            if (rs.next()) {
                paidLeave = convertPaidLeave(rs);
            }
            return paidLeave;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    // ステータスを更新する（承認・却下など）
    public void updateStatus(Connection conn, int leaveId, String status) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            if ("承認".equals(status)) {
                // 承認の場合、approved_at に現在時刻を設定
                pstmt = conn.prepareStatement(
                        "UPDATE paid_leave_tbl SET status = ?, approved_at = ? WHERE leave_id = ?"
                );
                pstmt.setString(1, status);
                pstmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                pstmt.setInt(3, leaveId);
            } else {
                pstmt = conn.prepareStatement(
                        "UPDATE paid_leave_tbl SET status = ?, approved_at = null WHERE leave_id = ?"
                );
                pstmt.setString(1, status);
                pstmt.setInt(2, leaveId);
            }
            pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }

    // java.util.Date を java.sql.Timestamp に変換
    private Timestamp toTimestamp (Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }
}
