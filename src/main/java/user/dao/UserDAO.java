package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.model.UserDTO;

public class UserDAO {

	public UserDTO login(Connection conn, String user_id, String password) throws Exception{
		String sql = "SELECT * FROM user_tbl WHERE user_id = ? AND password = ?"; 
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, user_id);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					
				UserDTO user = new UserDTO();
				user.setUser_id(rs.getString("user_id"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setBirth_date(rs.getDate("birth_date"));
                user.setJoin_date(rs.getDate("join_date"));
                user.setRetire_date(rs.getDate("retire_date"));
                user.setPosition(rs.getString("position"));
                user.setDepartment_id(rs.getInt("department_id"));
                user.setIs_admin(rs.getString("is_admin"));
                user.setEmp_status(rs.getString("emp_status"));
                user.setWork_status(rs.getString("work_status"));
                user.setLogin_status(rs.getString("login_status"));
                return user;
				}
			}
		}
		
		
		
		return null;
	}
	
	public boolean isUserExist(Connection conn, String user_id) throws Exception {
	    String sql = "SELECT COUNT(*) FROM user_tbl WHERE user_id=?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, user_id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}

	// 관리자
	public List<String> getAdminUsernames(Connection conn) throws SQLException {
		String sql = "SELECT user_id FROM user_tbl WHERE is_admin='Y'" ;
		try (PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()) {
			List<String> names = new ArrayList<>();
			while (rs.next()) {
				names.add(rs.getString("user_id"));
			}
			return names;
		}
	}

}
