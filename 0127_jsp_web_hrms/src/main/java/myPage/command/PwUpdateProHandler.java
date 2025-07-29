package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myPage.service.UserService;
import mvc.command.CommandHandler;

//[컨트롤러: 사용자의 비밀번호 변경 요청 처리]
public class PwUpdateProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 사용자 입력값 받기
    	String userId = request.getParameter("userId");
        String currentPw = request.getParameter("currentPw");
        String newPw = request.getParameter("newPw");
        String newPw2 = request.getParameter("newPw2");

        String msg = "";
        if (userId == null || currentPw == null || newPw == null || newPw2 == null || !newPw.equals(newPw2)) {
            msg = "입력값을 확인하세요.";
        } else {
            UserService userService = new UserService();
            boolean success = userService.updatePassword(userId, currentPw, newPw);
            if (success) {
                msg = "비밀번호가 성공적으로 변경되었습니다.";
            } else {
                msg = "현재 비밀번호가 맞지 않거나 변경에 실패했습니다.";
            }
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println(
            "<script>alert('" + msg + "');window.location.href='" + request.getContextPath() + "/mypageEditForm.do';</script>"
        );
        return null;
    }
}
