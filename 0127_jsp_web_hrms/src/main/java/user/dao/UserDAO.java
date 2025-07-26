package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
