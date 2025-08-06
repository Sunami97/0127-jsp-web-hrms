package admin.service;

import java.sql.Connection;
import java.sql.SQLException;

import admin.dao.AdminDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AdminUpdateService {
	
	AdminDao userDao = new AdminDao();
	
	public void userUpdate(String userId, String[] UpdateList, String[] reqVal) {
		Connection conn = null;
		try {
		conn = ConnectionProvider.getConnection();
		conn.setAutoCommit(false);
		userDao.Update(conn, userId, UpdateList, reqVal);
		conn.commit();
		
	}catch(SQLException e) {
		JdbcUtil.rollback(conn);
		e.printStackTrace();
	}finally {
		JdbcUtil.close(conn);
	}
	
}
}
