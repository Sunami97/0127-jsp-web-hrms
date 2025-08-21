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

	// 로그인 시 비밀번호 검증 + (레거시 비밀번호라면) 해시 업그레이드 수행
	public boolean loginAndMaybeUpgrade(String userId, String inputPw) throws SQLException {
		// 1. DB 연결 (try-with-resources로 자동 close)
		try (Connection conn = ConnectionProvider.getConnection()) {

			// 2. DAO 생성 → DB에서 사용자 정보 가져오기 위한 객체
			UserDepartmentDAO dao = new UserDepartmentDAO();

			// 3. 해당 userId의 저장된 비밀번호(해시 또는 평문)를 조회
			String stored = dao.selectPasswordHashById(conn, userId);

			// 4. 비밀번호 정보가 없으면 로그인 실패
			if (stored == null)
				return false;

			// 5. BCryptUtil.check() → 입력 비밀번호와 저장된 값 비교
			// 저장된 값이 평문이어도 안전하게 비교 가능
			boolean ok = BCryptUtil.check(inputPw, stored);

			// 6. 비밀번호가 일치하지 않으면 로그인 실패
			if (!ok)
				return false;

			// 7. 저장된 비밀번호가 BCrypt 해시인지 확인
			boolean isBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");

			// 8. 만약 BCrypt 해시가 아니면(=레거시 평문) 해시로 업그레이드
			if (!isBcrypt) {
				// 새 비밀번호 해시 생성
				String newHash = BCryptUtil.hash(inputPw);
				// DB에 해시값 업데이트
				dao.updatePasswordHash(conn, userId, newHash);
			}

			// 9. 모든 검증이 통과하면 true 반환 → 로그인 성공
			return true;
		}
	}

	// 마이페이지에서 비밀번호 변경
	// 1) 현재 비밀번호 검증 → 2) 새 비밀번호를 항상 BCrypt 해시로 저장
	public boolean updatePassword(String userId, String currentPw, String newPw) throws SQLException {
		// 1. DB 연결
		try (Connection conn = ConnectionProvider.getConnection()) {

			// 2. DAO 생성
			UserDepartmentDAO dao = new UserDepartmentDAO();

			// 3. 현재 저장된 비밀번호(해시 또는 평문) 조회
			String stored = dao.selectPasswordHashById(conn, userId);

			// 4. 비밀번호 정보가 없으면 실패
			if (stored == null)
				return false;

			// 5. 현재 비밀번호 검증
			if (!BCryptUtil.check(currentPw, stored))
				return false;

			// 6. 새 비밀번호를 BCrypt 해시로 변환
			String newHash = BCryptUtil.hash(newPw);

			// 7. 해시된 새 비밀번호를 DB에 저장 → 저장된 행이 1개 이상이면 성공
			return dao.updatePasswordHash(conn, userId, newHash) > 0;
		}
	}
}