package user.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;

public class UserUpdateFormHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "WEB-INF/view/userUpdateForm.jsp";	//유저수정페이지로 이동 ユーザー修正ページに移動

}
}
