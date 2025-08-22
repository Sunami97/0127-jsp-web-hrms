package paidLeave.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import paidLeave.dao.PaidLeaveDao;
import paidLeave.model.PaidLeave;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class WritePaidLeaveService {
    private PaidLeaveDao paidLeaveDao = new PaidLeaveDao();

    // 有給休暇申請データを新規登録するメソッド
    public Integer write(PaidLeaveRequest req) {
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            // リクエストデータをPaidLeaveエンティティに変換
            PaidLeave paidLeave = toPaidLeave(req);

            // DBに挿入
            PaidLeave savedPaidLeave = paidLeaveDao.insert(conn, paidLeave);
            if (savedPaidLeave == null) {
                throw new RuntimeException("fail to insert paidleave");
            }
            conn.commit();

            // 登録された有給休暇申請のIDを返す
            return savedPaidLeave.getLeaveId();

        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            JdbcUtil.rollback(conn);
            throw e;
        } finally {
            JdbcUtil.close(conn);
        }
    }

    // PaidLeaveRequestをPaidLeaveオブジェクトに変換
    private PaidLeave toPaidLeave(PaidLeaveRequest req) {
        Date now = new Date(); // 現在日時を取得（申請日時用）
        return new PaidLeave(
                null,           // ID（自動採番）
                req.getUserId(),       // ユーザーID
                null,
                req.getStartDate(),    // 開始日
                req.getEndDate(),      // 終了日
                req.getDays(),         // 使用日数
                "申請中",               // ステータス（初期値: 申請中）
                req.getReason(),       // 申請理由
                now,                   // 申請日
                req.getApprovedBy(),   // 承認者ID
                null                   // 承認日（初期は未設定）
        );
    }
}
