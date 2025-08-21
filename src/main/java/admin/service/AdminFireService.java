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
		userDao.fire(conn, userId, date); //dao 해고 메소드 호출, 매개변수 전달 daoメソッド呼び出し、パラメータ伝達
		conn.commit();
		
	}catch(SQLException e) {
		JdbcUtil.rollback(conn);
		e.printStackTrace();
	}finally {
		JdbcUtil.close(conn);
	}
	
}
}
