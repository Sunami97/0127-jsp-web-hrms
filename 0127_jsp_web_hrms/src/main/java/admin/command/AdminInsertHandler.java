package admin.command;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dto.AdminDto;
import admin.service.AdminInsertService;
import admin.service.AdminSelectService;
import mvc.command.CommandHandler;
import util.BCryptUtil;

public class AdminInsertHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private AdminInsertService userService = new AdminInsertService();
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

	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
			throws NumberFormatException, Exception {
		AdminInsertService userRequest = new AdminInsertService();

		String[] reqVal = new String[12];	
		String[] insertList = new String[12];	//넣을 항목과 값을 담을 배열 객체 入れる項目と数値を入れる配列オブジェクト
		int i; // 반복문에 사용할 변수 反復文に使用する変数

	//  입력한 값이 있다면 그 항목과 값을 배열에 넣음 그걸 반복문으로 반복 入力した数値があれば、その項目と数値を配列に入れて、それを繰り返します
		for (i = 0; i < 12; i++) {	
			if (req.getParameter("userId") != "" && req.getParameter("userId") != null
					&& !Arrays.toString(insertList).contains("user_id")) {
				reqVal[i] = req.getParameter("userId");
				insertList[i] = "user_id";
				continue;
			} else if (req.getParameter("password") != "" && req.getParameter("password") != null
					&& !Arrays.toString(insertList).contains("password")) {
				String psw = req.getParameter("password");
				String hashed = BCryptUtil.hash(psw);
				reqVal[i] = hashed;
				insertList[i] = "password";
				continue;
			} else if (req.getParameter("name") != "" && req.getParameter("name") != null
					&& !Arrays.toString(insertList).contains("name")) {
				reqVal[i] = req.getParameter("name");
				insertList[i] = "name";
				continue;
			} else if (req.getParameter("email") != "" && req.getParameter("email") != null
					&& !Arrays.toString(insertList).contains("email")) {
				reqVal[i] = req.getParameter("email");
				insertList[i] = "email";
				continue;
			} else if (req.getParameter("phone") != "" && req.getParameter("phone") != null
					&& !Arrays.toString(insertList).contains("phone")) {
				reqVal[i] = req.getParameter("phone");
				insertList[i] = "phone";
				continue;
			} else if (req.getParameter("birthDate") != "" && req.getParameter("birthDate") != null
					&& !Arrays.toString(insertList).contains("birth_date")) {
				reqVal[i] = req.getParameter("birthDate");
				insertList[i] = "birth_date";
				continue;
			} else if (req.getParameter("joinDate") != "" && req.getParameter("joinDate") != null
					&& !Arrays.toString(insertList).contains("join_date")) {
				reqVal[i] = req.getParameter("joinDate");
				insertList[i] = "join_date";
				continue;
			} else if (req.getParameter("position") != "" && req.getParameter("position") != null
					&& !Arrays.toString(insertList).contains("position")) {
				reqVal[i] = req.getParameter("position");
				insertList[i] = "position";
				continue;
			} else if (req.getParameter("departmentId") != "" && req.getParameter("departmentId") != null
					&& !Arrays.toString(insertList).contains("department_id")) {
				reqVal[i] = req.getParameter("departmentId");
				insertList[i] = "department_id";
				continue;
			} else if (req.getParameter("isAdmin") != "" && req.getParameter("isAdmin") != null
					&& !Arrays.toString(insertList).contains("is_admin")) {
				reqVal[i] = req.getParameter("isAdmin");
				insertList[i] = "is_admin";
				continue;
			} else if (req.getParameter("empStatus") != "" && req.getParameter("empStatus") != null
					&& !Arrays.toString(insertList).contains("emp_status")) {
				reqVal[i] = req.getParameter("empStatus");
				insertList[i] = "emp_status";
				continue;
			} else if (req.getParameter("workStatus") != "" && req.getParameter("workStatus") != null
					&& !Arrays.toString(insertList).contains("work_status")) {
				reqVal[i] = req.getParameter("workStatus");
				insertList[i] = "work_status";
				continue;
			}  else {
				break;
			}
		}
		String[] resizedArray = Arrays.copyOf(insertList, i);
		String[] reqArray = Arrays.copyOf(reqVal, i);	//반복한 횟수에 맞쳐 배열 수 조절 繰り返した回数に合わせて配列数を調節

		userRequest.serviceInsert(resizedArray, reqArray); //추가서비스의 메소드 호출 追加サービスのメソッド呼び出し
		List<AdminDto> user = userSelectService.AllSelect();
		int count = user.size();	//count변수에 검색된 인원 수를 담음 count変数に検索された人数を含める
		req.setAttribute("count", count);
		req.setAttribute("user", user);	//리퀘스트 객체에 count,user 저장 リクエストオブジェクトにcount、userを保存
	
		return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	}
}
