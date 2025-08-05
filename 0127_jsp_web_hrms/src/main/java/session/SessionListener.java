package session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import jdbc.connection.ConnectionProvider;
import user.model.UserDTO;
import user.service.UserService;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        UserDTO user = (UserDTO) session.getAttribute("loginUser");

        if (user != null) {
            String userId = user.getUser_id(); // UserDTO의 변수명에 맞게!
            try (Connection conn = ConnectionProvider.getConnection()) {
                UserService userService = new UserService();
                userService.updateLoginStatus(conn, userId, "N"); // 세션 만료 → 로그아웃
            } catch (Exception e) {
                e.printStackTrace(); // 필요 시 로깅
            }
        }
    }
}
