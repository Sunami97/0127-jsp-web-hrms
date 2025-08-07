package paidLeave.service;

import jdbc.connection.ConnectionProvider;
import paidLeave.dao.PaidLeaveDao;
import paidLeave.model.PaidLeave;

import java.sql.Connection;
import java.sql.SQLException;

public class ReadPaidLeave {
    private PaidLeaveDao paidLeaveDao = new PaidLeaveDao();

    public PaidLeaveData getPaidLeave(int leaveNum) {
        try(Connection conn = ConnectionProvider.getConnection()) {
            PaidLeave paidLeave = paidLeaveDao.selectById(conn, leaveNum);
            if (paidLeave == null) {
                throw new PaidLeaveNotFoundException();
            }
            return new PaidLeaveData(paidLeave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
