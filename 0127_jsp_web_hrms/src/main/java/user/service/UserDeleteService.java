package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import user.dao.UserDao;

public class UserDeleteService {
	private UserDao user = new UserDao();
			public void Delete(String[] userId) {
		
				Connection conn = null;
				try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			user.Delete(conn, userId);
			conn.commit();
		} catch(SQLException e) {
			JdbcUtil.rollback(conn);
			System.out.print(e.getMessage());
			e.printStackTrace();
		}finally {
			
			JdbcUtil.close(conn);
		}
	}
}
