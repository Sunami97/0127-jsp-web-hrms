package myPage.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import myPage.dao.UserDAO;
import myPage.dto.UserDepartmentDTO;

public class UserService {

    // 회원정보 조회
    public UserDepartmentDTO getUserById(String userId) throws SQLException {
        try (Connection conn = ConnectionProvider.getConnection()) {
            UserDAO dao = new UserDAO();
            return dao.selectUserById(conn, userId);
        }
    }

    // 회원정보 수정
    public boolean updateUser(UserDepartmentDTO user) throws SQLException {
        try (Connection conn = ConnectionProvider.getConnection()) {
            UserDAO dao = new UserDAO();
            int result = dao.updateUser(conn, user);
            return result > 0;
        }
    }

    // 비밀번호 변경
    public boolean updatePassword(String userId, String currentPw, String newPw) throws SQLException {
        try (Connection conn = ConnectionProvider.getConnection()) {
            UserDAO dao = new UserDAO();
            int result = dao.updatePassword(conn, userId, currentPw, newPw);
            return result > 0;
        }
    }
}
