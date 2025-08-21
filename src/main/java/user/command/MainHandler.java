package user.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.command.CommandHandler;



//메인 화면으로 이동하는 핸들러 클래스
//- 사용자가 로그인에 성공하면 메인 페이지(main.jsp)로 이동
//- 특별한 비즈니스 로직은 없고, 단순히 JSP 경로를 반환

public class MainHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 // "/WEB-INF/view/main.jsp" 경로의 JSP 페이지로 이동
        return "/WEB-INF/view/main.jsp";
    }
}
