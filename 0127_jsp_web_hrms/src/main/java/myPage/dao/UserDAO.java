package myPage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import myPage.dto.UserDepartmentDTO;

public class UserDAO {

	// [2] 내 정보 조회
	public UserDepartmentDTO selectUserById(Connection conn, String userId) throws SQLException {
		String sql = "SELECT u.user_id, u.name, u.email, u.phone, u.birth_date, u.join_date, u.retire_date, "
				+ "u.position, u.department_id, u.is_admin, u.emp_status, u.work_status, u.login_status, d.department_name "
				+ "FROM user_tbl u LEFT JOIN department_tbl d ON u.department_id = d.department_id "
				+ "WHERE u.user_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					UserDepartmentDTO user = new UserDepartmentDTO();
					user.setUserId(rs.getString("user_id"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setPhone(rs.getString("phone"));
					user.setBirthDate(rs.getDate("birth_date"));
					user.setJoinDate(rs.getDate("join_date"));
					user.setRetireDate(rs.getDate("retire_date"));
					user.setPosition(rs.getString("position"));
					user.setDepartmentId(rs.getInt("department_id"));
					user.setIsAdmin(rs.getString("is_admin"));
					user.setEmpStatus(rs.getString("emp_status"));
					user.setWorkStatus(rs.getString("work_status"));
					user.setLoginStatus(rs.getString("login_status"));
					user.setDepartmentName(rs.getString("department_name"));
					return user;
				}
			}
		}
		return null;
	}

	public int updateUser(Connection conn, UserDepartmentDTO user) throws SQLException {
		String sql = "UPDATE user_tbl SET name=?, email=?, phone=? WHERE user_id=?";
		try ( PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getUserId());
			return ps.executeUpdate();
		}
	}

	public int updateUserInfo(Connection conn, String userId, String name, String email, String phone, String birthDate, String joinDate,
			String retireDate) throws Exception {
		String sql = "UPDATE user_tbl SET email=?, phone=?"
				+ " WHERE user_id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			ps.setString(2, phone);
			ps.setString(3, userId);

			return ps.executeUpdate();
		} catch (SQLException e) {
// 예외 로그 (System.out 사용 또는 Logger 사용 가능)
			System.out.println("[ERROR] updateUserInfo 실패: " + e.getMessage());
// 필요하다면 상세 스택트레이스 출력
			e.printStackTrace();

// 래핑해서 다시 던지기 (필요에 따라)
			throw new Exception("사용자 정보 업데이트 중 오류 발생", e);
		}
	}

}
