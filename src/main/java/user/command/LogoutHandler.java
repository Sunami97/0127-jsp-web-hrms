package user.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import user.model.UserDTO;
import user.service.UserService;



// 로그아웃 기능을 처리하는 핸들러 클래스
//- 로그인 상태에서 로그아웃 버튼을 클릭하면
//  세션을 삭제하고 DB의 로그인 상태를 "N"(로그아웃 상태)로 변경
public class LogoutHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 기존 세션이 있으면 가져오고, 없으면 null 반환
        HttpSession session = request.getSession(false);
        if (session != null) { // 세션이 존재하는 경우
        	
        	// 세션에 저장된 로그인한 사용자 정보 가져오기
        	UserDTO user = (UserDTO) session.getAttribute("loginUser");
        	
            if (user != null) {// 로그인한 사용자가 있는 경우
            	
                try (Connection conn = ConnectionProvider.getConnection()) {
                	// UserService 객체 생성 (비즈니스 로직 처리)
                    UserService userService = new UserService();
                 // DB의 login_status 값을 "N"으로 변경 (로그아웃 상태)
                    userService.updateLoginStatus(conn, user.getUser_id(), "N");
                }
            }
            session.invalidate(); // 세션 무효화 (로그아웃)
        }
        // 로그인 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return null; // 리다이렉트 했으므로 null 반환
    }
}
