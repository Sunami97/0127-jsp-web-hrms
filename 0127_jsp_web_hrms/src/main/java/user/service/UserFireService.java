package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import user.dao.UserDao;

public class UserFireService {
UserDao userDao = new UserDao();
	
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
