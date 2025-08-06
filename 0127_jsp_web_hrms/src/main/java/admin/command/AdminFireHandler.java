package admin.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.service.AdminFireService;
import mvc.command.CommandHandler;

public class AdminFireHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private AdminFireService userService = new AdminFireService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		String date = req.getParameter("date");
		String userId = req.getParameter("userId");	//날짜와 유저아이디를 받음 日付とユーザーIDを受け取る
		userService.fire(userId, date);	//퇴사 서비스의 퇴사 메서드 호출 退社サービスの退社メソッド呼び出し
		
		return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	}
}
