package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;
import myPage.model.UserDepartmentDTO;
import myPage.service.UserService;
import user.model.UserDTO;

public class MypageEditFormHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. 현재 요청(request)에서 세션을 얻어서 ‘HttpSession 타입의 session 변수에 담는다’
        // 1. 現在のリクエスト(request)からセッションを取得し、HttpSession型の変数 session に格納する
        HttpSession session = request.getSession();

        // 2. 세션에서 "loginUser"라는 이름으로 저장된, 로그인한 유저 정보를 꺼내와서 UserDTO 타입의 loginUser 변수에 담는다
        // 2. セッションから "loginUser" という名前で保存されている、ログイン中のユーザー情報を取り出し、UserDTO型の変数 loginUser に格納する
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        // 3. UserService 객체를 새로 만들어서,
        // 3. UserService オブジェクトを新しく作成し、
        UserService userService = new UserService();

        // 4. 로그인한 사용자의 아이디로 내 정보(UserDepartmentDTO)를 조회해서 user 변수에 담는다
        // 4. ログイン中のユーザーのIDで自分の情報(UserDepartmentDTO)を検索し、変数 user に格納する
        UserDepartmentDTO user = userService.getUserById(loginUser.getUser_id());

        // 5. 조회한 user 정보를 request에 담아서 JSP에서 쓸 수 있게 한다
        // 5. 取得した user 情報を request に格納し、JSPで使えるようにする
        request.setAttribute("user", user);

        // 6. 마지막으로 내 정보 수정 화면(JSP)으로 이동한다
        // 6. 最後に、自分の情報を編集する画面(JSP)に移動する
        return "/WEB-INF/view/mypageEditForm.jsp";
    }
}
