package user.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import user.model.UserDTO;
import user.service.UserService;

public class LoginHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(request.getMethod().equalsIgnoreCase("GET")) {
            return "/index.jsp";
        } else if(request.getMethod().equalsIgnoreCase("POST")) {
            String user_id = request.getParameter("user_id");
            String password = request.getParameter("password");

            try (Connection conn = ConnectionProvider.getConnection()) {
                UserService userService = new UserService();
                UserDTO user = userService.login(conn, user_id, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser", user);

                    // 로그인 상태 플래그 수정 필요시 추가
                    // 토큰/세션 타임아웃 등도 추후 확장 가능

                    return "/WEB-INF/view/main.jsp"; // 로그인 성공시 메인화면
                } else {
                    request.setAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
                    return "/index.jsp";
                }
            }
        }
        return "/index.jsp";
    }
}