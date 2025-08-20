package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import myPage.model.UserDepartmentDTO;
import myPage.service.UserService;

public class MypageEditProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 요청 본문의 한글이 깨지지 않도록 UTF-8로 인코딩을 설정한다
        // 1. リクエスト本文の文字化けを防ぐため、エンコーディングを UTF-8 に設定する
        request.setCharacterEncoding("utf-8");

        // 2. 폼에서 전달된 파라미터를 가져와 각각 변수에 담는다
        // 2. フォームから渡されたパラメータを取得し、各変数に代入する
        String userId = request.getParameter("userId");   // 아이디 / ユーザーID
        String name   = request.getParameter("name");     // 이름 / 名前
        String email  = request.getParameter("email");    // 이메일 / メールアドレス
        String phone  = request.getParameter("phone");    // 전화번호 / 電話番号

        // 3. DTO를 생성하고 입력값을 설정한다
        // 3. DTO を生成し、入力値を設定する
        UserDepartmentDTO user = new UserDepartmentDTO();
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        // 4. 서비스 인스턴스를 준비한다
        // 4. サービスのインスタンスを用意する
        UserService userService = new UserService();

        // 5. DB에 업데이트를 요청하고, 성공 여부를 result에 받는다
        // 5. DB に更新処理を依頼し、成功可否を result に受け取る
        boolean result = userService.updateUser(user);

        // 6. 결과에 따라 메시지를 request에 설정한다 (JSP에서 사용)
        // 6. 結果に応じてメッセージを request に設定する（JSP で使用）
        if (result) {
            request.setAttribute("msg", "情報が正常に更新されました。"); // 정보가 정상적으로 수정되었습니다.
        } else {
            request.setAttribute("msg", "更新に失敗しました。もう一度お試しください。"); // 수정에 실패했습니다. 다시 시도해주세요.
        }

        // 7. 결과 메시지를 표시할 JSP 경로를 반환한다
        // 7. 結果メッセージを表示する JSP のパスを返す
        return "/WEB-INF/view/mypageEditResult.jsp";
    }
}
