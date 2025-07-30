package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import myPage.dao.UserDAO;

public class PwUpdateProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String currentPw = request.getParameter("currentPw");
        String newPw = request.getParameter("newPw");
        String newPw2 = request.getParameter("newPw2");

        String msg = "";
        if (userId == null || currentPw == null || newPw == null || newPw2 == null || !newPw.equals(newPw2)) {
            msg = "입력값을 확인하세요.";
        } else {
            try (Connection conn = ConnectionProvider.getConnection()) {
                UserDAO dao = new UserDAO();
                int result = dao.updatePassword(conn, userId, currentPw, newPw);
                if (result > 0) {
                    msg = "비밀번호가 성공적으로 변경되었습니다.";
                } else {
                    msg = "현재 비밀번호가 변경에 실패했습니다.";
                }
            }
        }
        // 결과 메시지를 JSP에 넘김
        request.setAttribute("msg", msg);
        return "/WEB-INF/view/pwUpdateResult.jsp"; // forward로 팝업 띄움
    }
}
