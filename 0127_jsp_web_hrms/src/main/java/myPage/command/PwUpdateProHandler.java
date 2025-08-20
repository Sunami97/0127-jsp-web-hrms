package myPage.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import myPage.service.UserService;

public class PwUpdateProHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 요청 파라미터(userId, currentPw, newPw, newPw2)를 가져와 각각 변수에 담는다
        // 1. リクエストパラメータ(userId, currentPw, newPw, newPw2)を取得し、各変数に代入する
        String userId    = request.getParameter("userId");       // 사용자 아이디 / ユーザーID
        String currentPw = request.getParameter("currentPw");    // 현재 비밀번호 / 現在のパスワード
        String newPw     = request.getParameter("newPw");        // 새 비밀번호 / 新しいパスワード
        String newPw2    = request.getParameter("newPw2");       // 새 비밀번호 확인 / 新しいパスワード（確認）

        // 2. 결과 메시지를 저장할 msg 변수를 준비한다
        // 2. 結果メッセージを保存するための msg 変数を用意する
        String msg = "";

        // 3. 입력값이 비어 있거나, 새 비밀번호와 확인값이 일치하지 않으면
        //    msg에 "입력값을 확인하세요."라는 메시지를 담는다
        // 3. 入力値が空であるか、新しいパスワードと確認用パスワードが一致しない場合、
        //    msg に「入力内容を確認してください。」というメッセージを代入する
        if (userId == null || currentPw == null || newPw == null || newPw2 == null || !newPw.equals(newPw2)) {
            msg = "入力内容を確認してください。";
        } else {
            // 4. UserService 인스턴스를 생성한다
            // 4. UserService のインスタンスを生成する
            UserService userService = new UserService();

            // 5. 비밀번호 변경을 요청하고 결과(true/false)를 result에 받는다
            // 5. パスワード変更を依頼し、結果(true/false)を result に受け取る
            boolean result = userService.updatePassword(userId, currentPw, newPw);

            // 6. 결과에 따라 msg에 성공/실패 메시지를 담는다
            // 6. 結果に応じて msg に成功/失敗メッセージを代入する
            if (result) {
                msg = "パスワードが正常に変更されました。"; // 비밀번호가 정상적으로 변경되었습니다
            } else {
                msg = "現在のパスワードの変更に失敗しました。"; // 현재 비밀번호 변경에 실패했습니다
            }
        }

        // 7. msg를 request에 "msg"라는 이름으로 저장한다 (JSP에서 사용 가능)
        // 7. msg を request に "msg" という名前で保存する（JSP で使用可能）
        request.setAttribute("msg", msg);

        // 8. 결과 메시지를 표시하는 JSP로 이동한다
        // 8. 結果メッセージを表示する JSP に移動する
        return "/WEB-INF/view/pwUpdateResult.jsp";
    }
}
