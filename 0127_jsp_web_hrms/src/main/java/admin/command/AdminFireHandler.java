package admin.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dto.AdminDto;
import admin.service.AdminFireService;
import admin.service.AdminSelectService;
import mvc.command.CommandHandler;

public class AdminFireHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private AdminFireService userService = new AdminFireService();
	private AdminSelectService userSelectService = new AdminSelectService();

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
		List<AdminDto> user = userSelectService.AllSelect();
		int count = user.size();	//count변수에 검색된 인원 수를 담음 count変数に検索された人数を含める
		req.setAttribute("count", count);
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
		
		return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	}
}
