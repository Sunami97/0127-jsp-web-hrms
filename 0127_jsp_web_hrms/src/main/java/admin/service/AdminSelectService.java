package admin.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import admin.dao.AdminDao;
import admin.dto.AdminDto;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AdminSelectService {
	private AdminDao userDao = new AdminDao();
	
	public List<AdminDto> Select(String keyWord, String keyField,String date, String[] sDate) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			List<AdminDto> user = userDao.selectList(conn, keyWord, keyField,date,sDate);
			
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
