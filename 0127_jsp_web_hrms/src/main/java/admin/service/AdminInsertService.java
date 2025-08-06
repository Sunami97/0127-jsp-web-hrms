package admin.service;

import java.sql.Connection;
import java.sql.SQLException;

import admin.dao.AdminDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AdminInsertService {
	private AdminDao userDao = new AdminDao();
	
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
