package admin.command;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dto.AdminDto;
import admin.service.AdminSelectService;
import mvc.command.CommandHandler;

public class AdminUpdateFormHandler implements CommandHandler{


private AdminSelectService userService = new AdminSelectService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userId = req.getParameter("userId");
		AdminDto user = userService.userInfo(userId);
		 // 職位 리스트
        List<String> positions = Arrays.asList("社員", "主任", "係長", "課長", "次長", "部長", "常務", "専務", "取締役", "代表取締役", "会長", "社長");

        // 部署 맵
        Map<Integer, String> departments = new LinkedHashMap<>();
        departments.put(1, "総務部");
        departments.put(2, "人事課");
        departments.put(3, "経理部");
        departments.put(4, "財務部");
        departments.put(5, "営業部");
        departments.put(6, "販売部");
        departments.put(7, "マーケティング部");
        departments.put(8, "開発部");
        departments.put(9, "技術部");
        departments.put(10, "情報システム部");
        departments.put(11, "生産部");
        departments.put(12, "品質管理部");
        departments.put(13, "法務部");
        departments.put(14, "企画部");

        // 状態 리스트
        List<String> empStatuss = Arrays.asList("在職", "休職", "退職");
        
        
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
		req.setAttribute("positions", positions);
		req.setAttribute("departments", departments);
		req.setAttribute("empStatuss", empStatuss);
			
		return "WEB-INF/view/userUpdateForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
		//셀럭트서비스의 메소드 호출후 유저리스트에 담음 セレクトサービスのメソッド呼び出し後、ユーザーリストに入れる
	
		
	}
}
