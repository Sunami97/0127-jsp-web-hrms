package admin.service;

import java.sql.Connection;
import java.sql.SQLException;

import admin.dao.AdminDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AdminDeleteService {
	private AdminDao user = new AdminDao();
			public void Delete(String[] userId) {
		
				Connection conn = null;
				try {
					
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			user.Delete(conn, userId); //dao 삭제 메소드 호출, 매개변수 전달 dao削除メソッド呼び出し、パラメータ伝達
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
