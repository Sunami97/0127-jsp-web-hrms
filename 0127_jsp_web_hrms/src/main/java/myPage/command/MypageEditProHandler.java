package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import myPage.model.UserDepartmentDTO;
import myPage.service.UserService;

public class MypageEditProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // 파라미터 받기
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        UserDepartmentDTO user = new UserDepartmentDTO();
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        UserService userService = new UserService();
        boolean result = userService.updateUser(user);

        if (result) {
            request.setAttribute("msg", "정보가 성공적으로 수정되었습니다.");
        } else {
            request.setAttribute("msg", "수정에 실패했습니다. 다시 시도해주세요.");
        }
        return "/WEB-INF/view/mypageEditResult.jsp";
    }
}
