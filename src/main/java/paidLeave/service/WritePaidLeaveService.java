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

    public Integer write(PaidLeaveRequest req) {
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            PaidLeave paidLeave = toPaidLeave(req);
            PaidLeave savedPaidLeave = paidLeaveDao.insert(conn, paidLeave);
            if (savedPaidLeave == null) {
                throw new RuntimeException("fail to insert paidleave");
            }
            conn.commit();

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

    private PaidLeave toPaidLeave(PaidLeaveRequest req) {
        Date now = new Date();
        return new PaidLeave(
                null,
                req.getUserId(),
                req.getStartDate(),
                req.getEndDate(),
                req.getDays(),
                "申請中",
                req.getReason(),
                now,
                req.getApprovedBy(),
                null);
    }
}
