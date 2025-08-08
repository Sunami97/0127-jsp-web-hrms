package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import myPage.service.UserService;

public class PwUpdateProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 현재 요청(request)에서 userId, currentPw, newPw, newPw2 값을 꺼내 각각 변수에 담는다
        String userId = request.getParameter("userId");         // 사용자 아이디
        String currentPw = request.getParameter("currentPw");   // 현재 비밀번호
        String newPw = request.getParameter("newPw");           // 새 비밀번호
        String newPw2 = request.getParameter("newPw2");         // 새 비밀번호 확인

        // 2. 결과 메시지를 저장할 msg라는 변수를 만든다
        String msg = "";

        // 3. 만약 userId, currentPw, newPw, newPw2 중 하나라도 비어 있거나
        //    새 비밀번호(newPw)와 새 비밀번호 확인(newPw2)이 다르면
        //    msg에 "입력값을 확인하세요."라는 메시지를 담는다
        if (userId == null || currentPw == null || newPw == null || newPw2 == null || !newPw.equals(newPw2)) {
            msg = "입력값을 확인하세요.";
        } else {
            // 4. UserService라는 서비스 객체를 새로 만들어서 userService 변수에 담는다
            UserService userService = new UserService();

            // 5. userService에 비밀번호 변경을 요청하고, 그 결과(true/false)를 result 변수에 담는다
            boolean result = userService.updatePassword(userId, currentPw, newPw);

            // 6. 만약 비밀번호 변경이 성공(true)이면
            //    msg에 "비밀번호가 성공적으로 변경되었습니다."라는 메시지를 담고,
            //    아니면 "현재 비밀번호가 변경에 실패했습니다."라는 메시지를 담는다
            if (result) {
                msg = "비밀번호가 성공적으로 변경되었습니다.";
            } else {
                msg = "현재 비밀번호가 변경에 실패했습니다.";
            }
        }

        // 7. msg에 담긴 메시지를 request에 "msg"라는 이름으로 저장한다 (JSP에서 사용 가능)
        request.setAttribute("msg", msg);

        // 8. 결과 메시지를 보여주는 JSP(pwUpdateResult.jsp)로 이동한다
        return "/WEB-INF/view/pwUpdateResult.jsp";
    }
}
