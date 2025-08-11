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

                // 1. 아이디 존재여부 확인
                boolean userExist = userService.isUserExist(conn, user_id);

                if (!userExist) {
                    request.setAttribute("msg", "존재하지 않는 아이디입니다.");
                    return "/index.jsp";
                } else {
                    // 2. 아이디는 있는데 비밀번호 일치 여부 확인
                    UserDTO user = userService.login(conn, user_id, password);
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser", user);
                        return "/WEB-INF/view/main.jsp"; // 로그인 성공시 메인화면
                    } else {
                        request.setAttribute("msg", "비밀번호가 틀렸습니다.");
                        return "/index.jsp";
                    }
                }
            }
        }
        return "/index.jsp";
    }
}
