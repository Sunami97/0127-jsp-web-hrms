package paidLeave.service;

import jdbc.connection.ConnectionProvider;
import paidLeave.dao.PaidLeaveDao;
import paidLeave.model.PaidLeave;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListPaidLeave {
    private PaidLeaveDao paidLeaveDao = new PaidLeaveDao();
    // 1ページあたりの表示件数
    private int size = 10;

    // 指定ページの有給休暇情報を取得
    public PaidLeavePage getPaidLeavePage(int pageNum) {
        try (Connection conn = ConnectionProvider.getConnection()) {
            // 全件数を取得
            int total = paidLeaveDao.selectCount(conn);
            // 指定ページの有給休暇リストを取得
            List<PaidLeave> content = paidLeaveDao.select(
                    conn, (pageNum - 1) * size, size);
            // ページ情報オブジェクトを生成して返却
            return new PaidLeavePage(total, pageNum, size, content);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
