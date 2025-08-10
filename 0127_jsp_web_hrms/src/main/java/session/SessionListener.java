package session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import jdbc.connection.ConnectionProvider;
import user.model.UserDTO;
import user.service.UserService;


/**
 * SessionListener
 * - 사용자의 세션이 종료될 때(브라우저 닫힘, 시간 초과 등) 자동으로 동작하는 리스너 클래스
 * - 로그인 상태였던 사용자의 login_status 값을 'N'(로그아웃 상태)로 변경
 *
 * 동작 방식:
 *  1. 톰캣/Tomcat 서버에서 세션이 종료되면 sessionDestroyed()가 자동 호출됨
 *  2. 세션에 저장된 로그인 사용자 정보(loginUser)를 가져옴
 *  3. DB 연결 후 login_status를 'N'으로 업데이트하여 로그아웃 처리
 */
public class SessionListener implements HttpSessionListener {

	
	/**
     * 세션이 종료될 때 호출되는 메서드
     * @param se 세션 이벤트 객체(HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	// 1. 현재 종료되는 세션 객체 가져오기
        HttpSession session = se.getSession();
     // 2. 세션에 저장된 로그인 사용자 정보 꺼내기
        UserDTO user = (UserDTO) session.getAttribute("loginUser");
        
     // 3. 로그인한 사용자가 있으면(DB에 로그아웃 상태 반영)
        if (user != null) { 
            String userId = user.getUser_id(); // 로그인한 사용자의 ID
            try (Connection conn = ConnectionProvider.getConnection()) {
            	// 4. 서비스 객체를 통해 login_status를 'N'으로 변경
                UserService userService = new UserService();
                userService.updateLoginStatus(conn, userId, "N"); // 세션 만료 -> 로그아웃
            } catch (Exception e) {
            	// 예외 발생 시 콘솔에 에러 출력
                e.printStackTrace(); 
            }
        }
    }
}
