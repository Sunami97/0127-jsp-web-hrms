package admin.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dto.AdminDto;
import admin.service.AdminSelectService;
import mvc.command.CommandHandler;

public class AdminFormHandler implements CommandHandler{

	private AdminSelectService userService = new AdminSelectService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		//셀럭트서비스의 메소드 호출후 유저리스트에 담음 セレクトサービスのメソッド呼び出し後、ユーザーリストに入れる
		List<AdminDto> user = userService.AllSelect();
		int count = user.size();	//count변수에 검색된 인원 수를 담음 count変数に検索された人数を含める
		req.setAttribute("count", count);
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
			return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
		
	
		
	}

}


