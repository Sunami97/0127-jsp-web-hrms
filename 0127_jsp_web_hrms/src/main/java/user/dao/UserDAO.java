package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.model.UserDTO;
import util.BCryptUtil; // 비밀번호 암호화/검증 유틸리티 클래스


// UserDAO (Data Access Object)
// - user_tbl 테이블과 관련된 데이터베이스 작업을 수행하는 클래스
// - 로그인, 아이디 존재 여부 확인, 로그인 상태 변경 등을 처리

public class UserDAO {

    //
    // 로그인 처리 메소드
    // 1. 입력한 user_id로 DB에서 사용자 정보를 가져옴
    // 2. DB에 저장된 암호화된 비밀번호와 사용자가 입력한 비밀번호를 비교
    // 3. 일치하면 UserDTO 객체에 사용자 정보를 담아 반환
     
    public UserDTO login(Connection conn, String user_id, String plainPassword) throws Exception {
        String sql = "SELECT * FROM user_tbl WHERE user_id = ?"; // 특정 사용자 검색 쿼리
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id); // 첫 번째 ? 에 user_id 바인딩
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 해당 ID의 사용자가 존재하는 경우
                    String dbHashedPassword = rs.getString("password"); // DB에 저장된 해시 비밀번호
                    
                    // BCrypt를 이용해 평문 비밀번호와 해시값 비교
                    if (BCryptUtil.check(plainPassword, dbHashedPassword)) {
                        // 비밀번호 일치 -> UserDTO에 사용자 정보 저장
                        UserDTO user = new UserDTO();
                        user.setUser_id(rs.getString("user_id"));
                        user.setPassword(dbHashedPassword); // 해시값 저장
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
                        return user; // 로그인 성공 시 UserDTO 반환
                    } else {
                        // 비밀번호 불일치 -> 로그인 실패
                        return null;
                    }
                }
            }
        }
        // 해당 ID의 사용자가 없으면 null 반환
        return null;
    }

    
    // 아이디 존재 여부 확인 메소드
    // - 회원가입 또는 로그인 시, 해당 아이디가 이미 존재하는지 확인
    public boolean isUserExist(Connection conn, String user_id) throws Exception {
        String sql = "SELECT COUNT(*) FROM user_tbl WHERE user_id=?"; // 아이디 개수 세기
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id); // SQL 쿼리의 첫 번째 ?에 user_id 값 넣기
            try (ResultSet rs = pstmt.executeQuery()) { // 쿼리 실행 후 결과 받기
                if (rs.next()) { // 결과 집합에서 첫 번째 행으로 이동
                    return rs.getInt(1) > 0; // COUNT(*) 값이 0보다 크면 해당 아이디가 존재
                }
            }
        }
     // 조건에 맞는 데이터가 없으면 false 반환
        return false;
    }

    
    // 로그인 상태 업데이트 메소드
    // - 로그인 시 "Y", 로그아웃 시 "N"으로 상태 변경

    public void updateLoginStatus(Connection conn, String userId, String status) throws SQLException {
        String sql = "UPDATE user_tbl SET login_status = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status); // 첫 번째 ?에 status 값 넣기
            pstmt.setString(2, userId); // 두 번째 ?에 userId 값 넣기
            pstmt.executeUpdate(); // UPDATE 실행 (DB 값 변경)
        }
    }
}
