package paidLeave.service;

import jdbc.connection.ConnectionProvider;
import paidLeave.dao.PaidLeaveDao;
import paidLeave.model.PaidLeave;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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

    public void updatePaidLeave(int leaveId, String status) {
        try (Connection conn = ConnectionProvider.getConnection()) {
            if ("승인".equals(status)) {
                Date approvedAt = new Date();
                paidLeaveDao.updateStatus(conn, leaveId, status, approvedAt);
            } else {
                paidLeaveDao.updateStatus(conn, leaveId, status, null);
            }
        } catch (Exception e) {
            throw new RuntimeException("상태 업데이트 실패", e);
        }
    }
}
