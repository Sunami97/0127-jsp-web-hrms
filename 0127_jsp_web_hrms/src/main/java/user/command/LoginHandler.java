package user.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import user.model.UserDTO;
import user.service.UserService;


// 로그인 기능을 처리하는 핸들러 클래스 // ログイン機能を処理するハンドラクラス
// - 사용자가 로그인 폼에서 아이디/비밀번호를 입력하면, // - ユーザーがログインフォームでID/パスワードを入力すると、
 //  이 클래스가 DB를 조회하여 로그인 성공/실패를 판별함 //  このクラスがDBを照会してログインの成否を判定する
// - CommandHandler 인터페이스를 구현하여 요청을 처리 // - CommandHandlerインターフェースを実装してリクエストを処理


public class LoginHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if(request.getMethod().equalsIgnoreCase("GET")) {  // 1. GET 요청일 경우 (로그인 페이지로 이동) // 1. GETリクエストの場合（ログインページへ遷移）
            return "/index.jsp"; 
        } else if(request.getMethod().equalsIgnoreCase("POST")) { // 2. POST 요청일 경우 (로그인 시도) // 2. POSTリクエストの場合（ログイン試行）
        	// 로그인 폼에서 전송된 파라미터(아이디, 비밀번호) 가져오기 // ログインフォームから送信されたパラメータ（ID・パスワード）を取得

            String user_id = request.getParameter("user_id"); 
            String password = request.getParameter("password");

            try (Connection conn = ConnectionProvider.getConnection()) {

            	// 비즈니스 로직을 담당하는 UserService 객체 생성 // ビジネスロジックを担当する UserService オブジェクトを生成
                UserService userService = new UserService();

                // (1) 아이디 존재여부 확인 // (1) IDの存在有無を確認
                boolean userExist = userService.isUserExist(conn, user_id);

                if (!userExist) { // 아이디가 존재하지 않으면 에러 메시지 설정 후 로그인 페이지로 이동 // IDが存在しない場合はエラーメッセージ設定後ログインページへ遷移
                    request.setAttribute("msg", "存在しないIDです.");
                    return "/index.jsp";
                } else {
                	// (2) 아이디가 존재하면 비밀번호 일치 여부 확인 // (2) IDが存在する場合はパスワード一致を確認
                    UserDTO user = userService.login(conn, user_id, password);
                    if (user != null) { // 비밀번호가 일치하는 경우 // パスワードが一致する場合
                    	// (3) DB에 로그인 상태(Y)로 변경 // (3) DBのログイン状態を「Y」に更新
                    	userService.updateLoginStatus(conn, user_id, "Y");
                    	// (4) 세션에 로그인한 사용자 정보 저장 // (4) セッションにログインユーザー情報を保存
                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser", user);
                        
                        return "/WEB-INF/view/main.jsp"; // 로그인 성공시 메인화면 // ログイン成功時はメイン画面
                        
                    } else {
                    	 // 비밀번호가 틀린 경우 // パスワードが間違っている場合

                        request.setAttribute("msg", "暗証番号が間違っています.");
                        return "/index.jsp";
                    }
                }
            }
        }

        // 요청 방식이 GET/POST가 아닌 경우 기본적으로 index.jsp로 이동 // リクエスト方式がGET/POSTでない場合は基本的に index.jsp へ遷移

        return "/index.jsp";
    }
}
