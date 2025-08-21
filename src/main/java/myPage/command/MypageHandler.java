package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;
import myPage.model.UserDepartmentDTO;
import myPage.service.UserService;
import user.model.UserDTO;

public class MypageHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 현재 요청(request)에서 세션을 얻어서 ‘HttpSession 타입의 session 변수에 담는다’
        HttpSession session = request.getSession(); 
        
        // 2. 세션에서 “loginUser”라는 이름으로 저장된 로그인한 사용자 정보를 꺼내와서, UserDTO 타입의 loginUser 변수에 담는다
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        // 3. 만약 로그인된 사용자가 없으면(=loginUser가 null이면), 로그인 페이지(index.jsp)로 이동시킨다
        if (loginUser == null) {
            response.sendRedirect("index.jsp");
            return null; // 더 이상 아래 코드는 실행하지 않고 종료
        }

        // 4. UserService 객체를 새로 만들어서,
        UserService userService = new UserService();
        
        // 5. 로그인한 사용자의 아이디로 내 정보(UserDepartmentDTO)를 조회해서 user 변수에 담는다
        UserDepartmentDTO user = userService.getUserById(loginUser.getUser_id());
        
        // 6. 조회한 user 정보를 request에 담아서, JSP에서 쓸 수 있게 한다
        request.setAttribute("user", user);

        // 7. 마지막으로 마이페이지 JSP로(내 정보 화면으로) 포워딩한다
        return "/WEB-INF/view/mypage.jsp";
    }
}
