package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import user.dao.UserDao;

public class UserUpdateService {
	
	UserDao userDao = new UserDao();
	
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
