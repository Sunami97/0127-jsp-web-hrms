package user.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화 (로그아웃)
        }
        // 로그인 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return null; // 리다이렉트 했으므로 null 반환
    }
}
