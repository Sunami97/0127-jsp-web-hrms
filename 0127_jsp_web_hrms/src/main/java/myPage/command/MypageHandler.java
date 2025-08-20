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
        // 1. 현재 요청에서 세션을 얻는다
        // 1. 現在のリクエストからセッションを取得する
        HttpSession session = request.getSession(); 
        
        // 2. 세션의 "loginUser" 속성에서 로그인 사용자 정보를 가져와 UserDTO 변수에 넣는다
        // 2. セッションの "loginUser" 属性からログインユーザー情報を取得し、UserDTO 型の変数に代入する
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        // 3. 만약 로그인된 사용자가 없으면 로그인 페이지(index.jsp)로 리다이렉트한다
        // 3. ログイン中のユーザーが存在しない場合は、ログインページ(index.jsp)へリダイレクトする
        if (loginUser == null) {
            response.sendRedirect("index.jsp");
            return null; // 더 이상 아래 코드는 실행하지 않고 종료
                         // これ以上下のコードは実行せず、処理を終了する
        }

        // 4. UserService 인스턴스를 생성한다
        // 4. UserService のインスタンスを生成する
        UserService userService = new UserService();
        
        // 5. 로그인한 사용자의 ID로 내 정보(UserDepartmentDTO)를 조회해 변수 user에 넣는다
        // 5. ログイン中ユーザーのIDで自分の情報(UserDepartmentDTO)を検索し、変数 user に代入する
        UserDepartmentDTO user = userService.getUserById(loginUser.getUser_id());
        
        // 6. 조회한 user 객체를 request에 설정하여 JSP에서 사용할 수 있게 한다
        // 6. 取得した user オブジェクトを request に設定し、JSP で使用できるようにする
        request.setAttribute("user", user);

        // 7. 마지막으로 마이페이지 JSP로 포워드한다
        // 7. 最後にマイページのJSPへフォワードする
        return "/WEB-INF/view/mypage.jsp";
    }
}
