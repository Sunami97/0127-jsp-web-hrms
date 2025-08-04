package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import user.dao.UserDao;
import user.dto.UserDto;

public class UserInsertService {
	private UserDao userDao = new UserDao();
	
	public void serviceInsert(String[] insertList, String[] reqVal) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			userDao.insert(conn,insertList,reqVal);
			
			conn.commit();
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			System.out.print(e.getMessage());
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn);
		}
	}
}
