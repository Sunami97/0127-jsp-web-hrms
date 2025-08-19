package admin.command;

import java.util.List;

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
		AdminDto user = userService.userInfo(userId);
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
			return "WEB-INF/view/userInfoForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
		//셀럭트서비스의 메소드 호출후 유저리스트에 담음 セレクトサービスのメソッド呼び出し後、ユーザーリストに入れる
	
		
	}
}
