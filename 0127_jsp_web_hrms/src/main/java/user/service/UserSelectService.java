package user.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import user.dao.UserDao;
import user.dto.UserDto;

public class UserSelectService {
	private UserDao userDao = new UserDao();
	
	public List<UserDto> Select(String keyWord, String keyField,String date, String[] sDate) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			List<UserDto> user = userDao.selectList(conn, keyWord, keyField,date,sDate);
			
			conn.commit();
			return user;
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			System.out.print(e.getMessage());
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
}
