package admin.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;

public class AdminRegisterFormHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
			return "WEB-INF/view/userRegisterForm.jsp"; //유저등록페이지 이동 ユーザー登録ページへ移動
		}
		
	}


