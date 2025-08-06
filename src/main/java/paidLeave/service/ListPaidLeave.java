package paidLeave.service;

import jdbc.connection.ConnectionProvider;
import paidLeave.dao.PaidLeaveDao;
import paidLeave.model.PaidLeave;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListPaidLeave {
    private PaidLeaveDao paidLeaveDao = new PaidLeaveDao();
    private int size = 10;

    public PaidLeavePage getPaidLeavePage(int pageNum) {
        try (Connection conn = ConnectionProvider.getConnection()) {
            // 전체 게시글의 개수
            int total = paidLeaveDao.selectCount(conn);
            // 게시글 목록
            List<PaidLeave> content = paidLeaveDao.select(
                    conn, (pageNum - 1) * size, size);
            return new PaidLeavePage(total, pageNum, size, content);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
