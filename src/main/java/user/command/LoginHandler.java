package user.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import user.model.UserDTO;
import user.service.UserService;

//로그인 기능을 처리하는 핸들러 클래스
//- 사용자가 로그인 폼에서 아이디/비밀번호를 입력하면,
//  이 클래스가 DB를 조회하여 로그인 성공/실패를 판별함
//- CommandHandler 인터페이스를 구현하여 요청을 처리

public class LoginHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(request.getMethod().equalsIgnoreCase("GET")) {  // 1. GET 요청일 경우 (로그인 페이지로 이동)
            return "/index.jsp"; // 사용자가 로그인 페이지(index.jsp)를 요청한 경우
        } else if(request.getMethod().equalsIgnoreCase("POST")) { // 2. POST 요청일 경우 (로그인 시도)
        	// 로그인 폼에서 전송된 파라미터(아이디, 비밀번호) 가져오기
            String user_id = request.getParameter("user_id"); 
            String password = request.getParameter("password");
            
            try (Connection conn = ConnectionProvider.getConnection()) {
            	// 비즈니스 로직을 담당하는 UserService 객체 생성
                UserService userService = new UserService();

                // (1) 아이디 존재여부 확인
                boolean userExist = userService.isUserExist(conn, user_id);

                if (!userExist) { // 아이디가 존재하지 않으면 에러 메시지 설정 후 로그인 페이지로 이동
                    request.setAttribute("msg", "존재하지 않는 아이디입니다.");
                    return "/index.jsp";
                } else {
                	// (2) 아이디가 존재하면 비밀번호 일치 여부 확인
                    UserDTO user = userService.login(conn, user_id, password);
                    if (user != null) { // 비밀번호가 일치하는 경우
                    	// (3) DB에 로그인 상태(Y)로 변경
                    	userService.updateLoginStatus(conn, user_id, "Y");
                    	// (4) 세션에 로그인한 사용자 정보 저장
                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser", user);
                        
                        return "/WEB-INF/view/main.jsp"; // 로그인 성공시 메인화면
                        
                    } else {
                    	 // 비밀번호가 틀린 경우
                        request.setAttribute("msg", "비밀번호가 틀렸습니다.");
                        return "/index.jsp";
                    }
                }
            }
        }
     // 요청 방식이 GET/POST가 아닌 경우 기본적으로 index.jsp로 이동
        return "/index.jsp";
    }
}
