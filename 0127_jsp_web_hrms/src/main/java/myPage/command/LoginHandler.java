package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myPage.service.UserService;

public class LoginHandler {

	private final UserService userService = new UserService();

	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    if ("GET".equalsIgnoreCase(request.getMethod())) {
	        return "/WEB-INF/view/login.jsp";
	    }
	    String userId = request.getParameter("userId");
	    String password = request.getParameter("password");

	    boolean ok = userService.loginAndMaybeUpgrade(userId, password);
	    if (!ok) {
	        request.setAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
	        return "/WEB-INF/view/login.jsp";
	    }

	    // 세션 적재 (프로젝트에 맞춰)
	    HttpSession session = request.getSession();
	    user.model.UserDTO loginUser = new user.model.UserDTO();
	    loginUser.setUser_id(userId);
	    session.setAttribute("loginUser", loginUser);

	    response.sendRedirect(request.getContextPath() + "/main.do");
	    return null;
	}

}
