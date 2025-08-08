package myPage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import myPage.model.UserDepartmentDTO;

public class UserDepartmentDAO {

	// [1] 유저ID로 사용자(부서명 포함) 정보를 조회하는 메소드

	public UserDepartmentDTO selectUserById(Connection conn, String userId) throws SQLException {
		// 1. 유저 정보를 가져오는 SQL문을 만든다 (부서명까지 한 번에 가져옴)
		String sql = "SELECT u.user_id, u.name, u.email, u.phone, u.birth_date, u.join_date, u.retire_date, "
				+ "u.position, u.department_id, u.is_admin, u.emp_status, u.work_status, u.login_status, d.department_name "
				+ "FROM user_tbl u LEFT JOIN department_tbl d ON u.department_id = d.department_id "
				+ "WHERE u.user_id = ?";

		// 2. 위에서 만든 SQL문을 실행할 PreparedStatement 타입의 ps 변수를 만든다
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			// 3. SQL문에서 ?(물음표)에 userId 값을 넣는다
			ps.setString(1, userId);

			// 4. SQL문을 실행해서 결과(ResultSet)를 rs 변수에 담는다
			try (ResultSet rs = ps.executeQuery()) {
				// 5. 만약 결과가 한 줄이라도 있으면
				if (rs.next()) {
					// 6. UserDepartmentDTO 타입의 user 변수를 새로 만들어서,
					// 각각의 컬럼 값을 user에 하나씩 담는다
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
					// 7. 값을 다 담은 user 변수를 반환한다
					return user;
				}
			}
		}
		// 8. 만약 결과가 없으면 null을 반환한다
		return null;
	}

	// [2] 이름, 이메일, 전화번호 수정 (유저ID로 찾음)
	public int updateUser(Connection conn, UserDepartmentDTO user) throws SQLException {
		// 1. 사용자 정보(이름, 이메일, 전화번호)를 수정하는 SQL문을 만든다
		String sql = "UPDATE user_tbl SET name=?, email=?, phone=? WHERE user_id=?";
		// 2. PreparedStatement 타입의 ps 변수를 만든다
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			// 3. 각각 ?에 user 객체에서 꺼낸 값들을 차례대로 넣는다
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getUserId());
			// 4. SQL문을 실행해서 영향을 받은 행(row) 개수를 반환한다 (성공이면 1)
			return ps.executeUpdate();
		}
	}

	// [3] 이메일, 전화번호 등 정보 수정 (확장용, 거의 사용 안함)
	public int updateUserInfo(Connection conn, String userId, String name, String email, String phone, String birthDate,
			String joinDate, String retireDate) throws Exception {
		// 1. 이메일, 전화번호만 수정하는 SQL문을 만든다
		String sql = "UPDATE user_tbl SET email=?, phone=?" + " WHERE user_id=?";
		// 2. PreparedStatement 타입의 ps 변수를 만든다
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			// 3. 각각 ?에 파라미터로 받은 값을 넣는다
			ps.setString(1, email);
			ps.setString(2, phone);
			ps.setString(3, userId);
			// 4. SQL문을 실행해서 결과(1:성공, 0:실패)를 반환한다
			return ps.executeUpdate();
		} catch (SQLException e) {
			// 5. 만약 에러가 발생하면, 에러 메시지를 출력하고 예외를 다시 던진다
			System.out.println("[ERROR] updateUserInfo 실패: " + e.getMessage());
			e.printStackTrace();
			throw new Exception("사용자 정보 업데이트 중 오류 발생", e);
		}
	}

	// [4] 비밀번호 변경 (현재 비밀번호가 맞는지 체크 후 변경)
	public int updatePassword(Connection conn, String userId, String currentPw, String newPw) throws SQLException {
		// 1. 비밀번호를 변경하는 SQL문을 만든다 (조건: user_id와 기존 비밀번호 일치)
		String sql = "UPDATE user_tbl SET password=? WHERE user_id=? AND password=?";
		// 2. PreparedStatement 타입의 ps 변수를 만든다
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			// 3. 각각 ?에 새 비밀번호, 유저ID, 현재 비밀번호 값을 넣는다
			ps.setString(1, newPw);
			ps.setString(2, userId);
			ps.setString(3, currentPw);
			// 4. SQL문을 실행해서 결과(1:성공, 0:실패)를 반환한다
			return ps.executeUpdate();
		}
	}

	// 저장된 비밀번호(해시/레거시) 조회 — TRIM으로 CHAR 패딩 방지
	public String selectPasswordHashById(Connection conn, String userId) throws SQLException {
		final String sql = "SELECT TRIM(password) FROM user_tbl WHERE user_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? rs.getString(1) : null;
			}
		}
	}

	// 새 비번 해시 저장 (updated_at 있으면 SYSDATE 추가)
	public int updatePasswordHash(Connection conn, String userId, String newHash) throws SQLException {
		final String sql = "UPDATE user_tbl SET password = ? WHERE user_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, newHash);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

}
