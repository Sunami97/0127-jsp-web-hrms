package admin.service;

import java.sql.Connection;
import java.sql.SQLException;

import admin.dao.AdminDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AdminFireService {
AdminDao userDao = new AdminDao();
	
	public void fire(String userId, String date) {
		Connection conn = null;
		try {
		conn = ConnectionProvider.getConnection();
		conn.setAutoCommit(false);
		userDao.fire(conn, userId, date);
		conn.commit();
		
	}catch(SQLException e) {
		JdbcUtil.rollback(conn);
		e.printStackTrace();
	}finally {
		JdbcUtil.close(conn);
	}
	
}
}
