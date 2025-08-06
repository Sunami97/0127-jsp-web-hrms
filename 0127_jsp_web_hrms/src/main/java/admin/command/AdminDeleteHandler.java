package admin.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.service.AdminDeleteService;
import mvc.command.CommandHandler;

public class AdminDeleteHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private AdminDeleteService userService = new AdminDeleteService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			return processForm(req, res);
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(req,res);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		String[] userId = req.getParameterValues("userId"); //유저아이디를 담아둘 배열객체 ユーザーIDを入れる配列オブジェクト     
		 userService.Delete(userId);	//딜리트 서비스의 딜리트 메서드 호출 デリートサービスのデリートメソッド呼び出し
		
		return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	}

}
