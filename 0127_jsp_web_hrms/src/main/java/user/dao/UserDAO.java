package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.model.UserDTO;
import util.BCryptUtil;

public class UserDAO {

	public UserDTO login(Connection conn, String user_id, String plainPassword) throws Exception{
		String sql = "SELECT * FROM user_tbl WHERE user_id = ? "; 
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, user_id);
			
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					String dbHashedPassword = rs.getString("password");
				
					if (BCryptUtil.check(plainPassword, dbHashedPassword)) {
					UserDTO user = new UserDTO();
					user.setUser_id(rs.getString("user_id"));
					user.setPassword(dbHashedPassword);
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
					}else {
						return null;
					}
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
	
	
	public void updateLoginStatus(Connection conn, String userId, String status) throws SQLException {
	    String sql = "UPDATE user_tbl SET login_status = ? WHERE user_id = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, status);
	        pstmt.setString(2, userId);
	        pstmt.executeUpdate();
	    }
	}


}
