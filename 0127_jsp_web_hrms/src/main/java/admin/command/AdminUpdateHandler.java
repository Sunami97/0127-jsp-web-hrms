package admin.command;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.service.AdminUpdateService;
import mvc.command.CommandHandler;

public class AdminUpdateHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
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
		String[] reqVal = new String[7];
		String[] updateList = new String[7];	//넣을 항목과 값을 담을 배열 객체 入れる項目と数値を入れる配列オブジェクト
		int i;// 반복문에 사용할 변수 反復文に使用する変数

		//  입력한 값이 있다면 그 항목과 값을 배열에 넣음 그걸 반복문으로 반복 入力した数値があれば、その項目と数値を配列に入れて、それを繰り返します
		for (i = 0; i < 7; i++) {
			if (req.getParameter("email") != "" && req.getParameter("email") != null
					&& !Arrays.toString(updateList).contains("email")) {
				reqVal[i] = req.getParameter("email");
				updateList[i] = "email";
				continue;
			} else if (req.getParameter("phone") != "" && req.getParameter("phone") != null
					&& !Arrays.toString(updateList).contains("phone")) {
				reqVal[i] = req.getParameter("phone");
				updateList[i] = "phone";
				continue;
			} else if (req.getParameter("position") != "" && req.getParameter("position") != null
					&& !Arrays.toString(updateList).contains("position")) {
				reqVal[i] = req.getParameter("position");
				updateList[i] = "position";
				continue;
			} else if (req.getParameter("departmentId") != "" && req.getParameter("departmentId") != null
					&& !Arrays.toString(updateList).contains("department_id")) {
				reqVal[i] = req.getParameter("departmentId");
				updateList[i] = "department_id";
				continue;
			} else if (req.getParameter("isAdmin") != "" && req.getParameter("isAdmin") != null
					&& !Arrays.toString(updateList).contains("is_admin")) {
				reqVal[i] = req.getParameter("isAdmin");
				updateList[i] = "is_admin";
				continue;
			} else if (req.getParameter("empStatus") != "" && req.getParameter("empStatus") != null
					&& !Arrays.toString(updateList).contains("emp_status")) {
				reqVal[i] = req.getParameter("empStatus");
				updateList[i] = "emp_status";
				continue;
			}else if (req.getParameter("name") != "" && req.getParameter("name") != null
					&& !Arrays.toString(updateList).contains("name")) {
				reqVal[i] = req.getParameter("name");
				updateList[i] = "name";
				continue;
			}else {
				break;
			}
		}
		String[] resizedArray = Arrays.copyOf(updateList, i);
		String[] reqArray = Arrays.copyOf(reqVal, i);	//반복한 횟수에 맞쳐 배열 수 조절 繰り返した回数に合わせて配列数を調節
		
		userService.userUpdate(userId, resizedArray, reqArray);	//수정서비스의 메소드 호출 修正サービスのメソッド呼び出し

		return "WEB-INF/view/adminForm.jsp"; //관리자 페이지로 리턴 管理者ページにリターン
	}

}
