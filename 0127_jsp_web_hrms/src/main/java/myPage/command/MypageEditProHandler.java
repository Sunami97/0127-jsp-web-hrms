package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import myPage.model.UserDepartmentDTO;
import myPage.service.UserService;

public class MypageEditProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 한글이 깨지지 않도록, 현재 요청(request)의 문자 인코딩을 UTF-8로 설정한다
        request.setCharacterEncoding("utf-8");

        // 2. 폼(form)에서 넘어온 파라미터(입력값)들을 각각 변수에 담는다
        String userId = request.getParameter("userId");   // 아이디
        String name = request.getParameter("name");       // 이름
        String email = request.getParameter("email");     // 이메일
        String phone = request.getParameter("phone");     // 전화번호

        // 3. UserDepartmentDTO라는 객체를 만들어서, 그 안에 위에서 받은 값들을 저장한다
        UserDepartmentDTO user = new UserDepartmentDTO();
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        // 4. UserService라는 서비스를 새로 만들어서,
        UserService userService = new UserService();

        // 5. DB에 정보수정 요청을 보내고, 성공 여부를 result 변수에 담는다
        boolean result = userService.updateUser(user);

        // 6. 수정 결과에 따라, 결과 메시지를 request에 담는다 (JSP에서 사용 가능)
        if (result) {
            request.setAttribute("msg", "情報が正常に更新されました。");
        } else {
            request.setAttribute("msg", "更新に失敗しました。もう一度お試しください。");
        }


        // 7. 마지막으로 결과 메시지를 보여줄 JSP 화면으로 이동한다
        return "/WEB-INF/view/mypageEditResult.jsp";
    }
}
