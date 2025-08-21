package session;

import java.sql.Connection;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import jdbc.connection.ConnectionProvider;
import user.model.UserDTO;
import user.service.UserService;


/**
 * SessionListener

 * - 사용자의 세션이 종료될 때(브라우저 닫힘, 시간 초과 등) 자동으로 동작하는 리스너 클래스 // ユーザーのセッションが終了したとき（ブラウザ閉鎖、タイムアウトなど）自動的に動作するリスナークラス
 * - 로그인 상태였던 사용자의 login_status 값을 'N'(로그아웃 상태)로 변경 // ログイン状態だったユーザーの login_status を「N」（ログアウト状態）に変更
 *
 * 동작 방식: // 動作方式:
 *  1. 톰캣/Tomcat 서버에서 세션이 종료되면 sessionDestroyed()가 자동 호출됨 // 1. Tomcat サーバでセッションが終了すると sessionDestroyed() が自動的に呼び出される
 *  2. 세션에 저장된 로그인 사용자 정보(loginUser)를 가져옴 // 2. セッションに保存されているログインユーザー情報（loginUser）を取得
 *  3. DB 연결 후 login_status를 'N'으로 업데이트하여 로그아웃 처리 // 3. DB 接続後、login_status を「N」に更新してログアウト処理


 */
public class SessionListener implements HttpSessionListener {

	
	/**

     * 세션이 종료될 때 호출되는 메서드 // セッション終了時に呼び出されるメソッド
     * @param se 세션 이벤트 객체(HttpSessionEvent) // セッションイベントオブジェクト（HttpSessionEvent）
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	// 1. 현재 종료되는 세션 객체 가져오기 // 現在終了するセッションオブジェクトを取得
        HttpSession session = se.getSession();
     // 2. 세션에 저장된 로그인 사용자 정보 꺼내기 // セッションに保存されたログインユーザー情報を取得
        UserDTO user = (UserDTO) session.getAttribute("loginUser");
        
     // 3. 로그인한 사용자가 있으면(DB에 로그아웃 상태 반영) // ログイン中のユーザーがいれば（DBにログアウト状態を反映）
        if (user != null) { 
            String userId = user.getUser_id(); // 로그인한 사용자의 ID // ログイン中のユーザーID
            try (Connection conn = ConnectionProvider.getConnection()) {
            	// 4. 서비스 객체를 통해 login_status를 'N'으로 변경 // サービスオブジェクトを通じて login_status を「N」に変更
                UserService userService = new UserService();
                userService.updateLoginStatus(conn, userId, "N"); // 세션 만료 -> 로그아웃 // セッション満了 → ログアウト
            } catch (Exception e) {
            	// 예외 발생 시 콘솔에 에러 출력 // 例外発生時にコンソールへエラー出力

                e.printStackTrace(); 
            }
        }
    }
}
