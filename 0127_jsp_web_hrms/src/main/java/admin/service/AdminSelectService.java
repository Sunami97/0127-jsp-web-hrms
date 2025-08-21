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
			
			List<AdminDto> user = userDao.selectList(conn, keyWord, keyField,date,sDate); //dao 조회 메소드 호출, 매개변수 전달 daoメソッド呼び出し、パラメータ伝達
			
			conn.commit();
			return user; //객체 리턴 オブジェクト·リターン
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			System.out.print(e.getMessage());
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
	
	public List<AdminDto> AllSelect() {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			List<AdminDto> user = userDao.AllSelect(conn); //dao 조회 메소드 호출 daoメソッド呼び出し
			
			conn.commit();
			return user; //객체 리턴 オブジェクト·リターン
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			System.out.print(e.getMessage());
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
	
	public AdminDto userInfo(String userId) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			AdminDto user = userDao.userInfo(conn, userId); //dao 조회 메소드 호출 daoメソッド呼び出し
			
			conn.commit();
			return user; //객체 리턴 オブジェクト·リターン
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
