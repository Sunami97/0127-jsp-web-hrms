package admin.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dto.AdminDto;
import admin.service.AdminSelectService;
import mvc.command.CommandHandler;

public class AdminInfoFormHandler implements CommandHandler {

private AdminSelectService userService = new AdminSelectService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String userId = req.getParameter("userId");
		//가져온 사원아이디로 사원 정보 조회 もって来た社員IDで社員情報を照会
		AdminDto user = userService.userInfo(userId);
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
			return "WEB-INF/view/userInfoForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	
		
	}
}
