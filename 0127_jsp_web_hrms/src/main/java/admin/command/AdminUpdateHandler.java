package admin.command;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dto.AdminDto;
import admin.service.AdminSelectService;
import admin.service.AdminUpdateService;
import mvc.command.CommandHandler;

public class AdminUpdateHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private AdminSelectService userSelectService = new AdminSelectService();
	private AdminUpdateService userService = new AdminUpdateService();

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
		String userId = req.getParameter("userId");
		//넣을 항목과 값을 담을 배열 객체 入れる項目と数値を入れる配列オブジェクト
		String[] reqVal = new String[7];
		String[] updateList = new String[7];	
		
				//수정할 수치를 배열에 저장 修正する数値を配列に保存
				reqVal[0] = req.getParameter("email");
				updateList[0] = "email";
			
				reqVal[1] = req.getParameter("phone");
				updateList[1] = "phone";
			
				reqVal[2] = req.getParameter("position");
				updateList[2] = "position";
			
				reqVal[3] = req.getParameter("departmentId");
				updateList[3] = "department_id";
				
				reqVal[4] = req.getParameter("isAdmin");
				updateList[4] = "is_admin";
			
				reqVal[5] = req.getParameter("empStatus");
				updateList[5] = "emp_status";
			
				reqVal[6] = req.getParameter("name");
				updateList[6] = "name";
		userService.userUpdate(userId, updateList, reqVal);	//수정서비스의 메소드 호출 修正サービスのメソッド呼び出し
		//셀럭트서비스의 메소드 호출후 유저리스트에 담음 セレクトサービスのメソッド呼び出し後、ユーザーリストに入れる
		List<AdminDto> user = userSelectService.AllSelect();
		int count = user.size();	//count변수에 검색된 인원 수를 담음 count変数に検索された人数を含める
		req.setAttribute("count", count);
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
		

		return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	}

}
