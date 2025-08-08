package myPage.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import myPage.dao.UserDepartmentDAO;
import myPage.model.UserDepartmentDTO;
import util.BCryptUtil;

/**
 * UserService 클래스 - 마이페이지에서 사용하는 비즈니스 로직(실제 서비스 동작)을 처리하는 클래스다 - 데이터베이스(DzB)와
 * 직접 연결하지 않고, DAO를 통해서만 DB 작업을 한다 - 핸들러(컨트롤러)와 DAO의 중간 역할을 한다
 */
public class UserService {

	// 회원정보 조회(유저ID로 내 정보+부서명까지 가져오기)
	public UserDepartmentDTO getUserById(String userId) throws SQLException {
		// 1. ConnectionProvider.getConnection()을 실행해서 DB 연결(Connection 객체)을 얻어서,
		// try-with-resources문 안의 conn 변수에 저장한다 (자동 close)
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 2. UserDepartmentDAO 타입의 dao 변수를 새로 만들어서, UserDepartmentDAO 객체를 담는다
			UserDepartmentDAO dao = new UserDepartmentDAO();
			// 3. dao.selectUserById(conn, userId)를 실행해서 DB에서 유저 정보를 조회하고,
			// 그 결과(UserDepartmentDTO 객체)를 바로 반환한다
			return dao.selectUserById(conn, userId);
		}
	}

	// 회원정보 수정(이름/이메일/전화번호 등)
	public boolean updateUser(UserDepartmentDTO user) throws SQLException {
		// 1. DB 연결(Connection 객체)을 얻어서 conn 변수에 저장한다 (자동 close)
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 2. UserDepartmentDAO 타입의 dao 변수를 새로 만들어서, UserDepartmentDAO 객체를 담는다
			UserDepartmentDAO dao = new UserDepartmentDAO();
			// 3. dao.updateUser(conn, user)를 실행해서 DB에 수정요청을 보낸다
			// 그리고 결과(수정된 행의 개수)를 result 변수에 담는다
			int result = dao.updateUser(conn, user);
			// 4. 만약 result가 1 이상이면 true(성공), 아니면 false(실패)를 반환한다
			return result > 0;
		}
	}

	// 로그인 시 검증 + (레거시면) 해시 업그레이드
	public boolean loginAndMaybeUpgrade(String userId, String inputPw) throws SQLException {
		try (Connection conn = ConnectionProvider.getConnection()) {
			UserDepartmentDAO dao = new UserDepartmentDAO();
			String stored = dao.selectPasswordHashById(conn, userId);
			if (stored == null)
				return false;

			// BCryptUtil.check는 레거시(평문)도 안전 비교
			boolean ok = BCryptUtil.check(inputPw, stored);
			if (!ok)
				return false;

			// 평문이었다면 성공 시 해시로 업그레이드
			boolean isBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");
			if (!isBcrypt) {
				String newHash = BCryptUtil.hash(inputPw);
				dao.updatePasswordHash(conn, userId, newHash);
			}
			return true;
		}
	}

	// 마이페이지에서 비밀번호 변경 (현재 비번 검증 → 새 비번은 항상 해시 저장)
	public boolean updatePassword(String userId, String currentPw, String newPw) throws SQLException {
		try (Connection conn = ConnectionProvider.getConnection()) {
			UserDepartmentDAO dao = new UserDepartmentDAO();
			String stored = dao.selectPasswordHashById(conn, userId);
			if (stored == null)
				return false;

			if (!BCryptUtil.check(currentPw, stored))
				return false;

			String newHash = BCryptUtil.hash(newPw);
			return dao.updatePasswordHash(conn, userId, newHash) > 0;
		}
	}
}
