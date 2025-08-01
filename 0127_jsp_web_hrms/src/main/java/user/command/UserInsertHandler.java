package user.command;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import user.dto.UserDto;
import user.service.UserInsertService;

public class UserInsertHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private UserInsertService userService = new UserInsertService();

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
		UserInsertService userRequest = new UserInsertService();

		String[] reqVal = new String[13];
		String[] insertList = new String[13];
		int i;

		
		/*
		 * String[] resizedArray = { "user_id", "password", "name","login_Status"};
		 * String[] reqArray = { "b", "b", "c","n" };
		 */
		 

		for (i = 0; i < 13; i++) {
			if (req.getParameter("userId") != "" && req.getParameter("userId") != null
					&& !Arrays.toString(insertList).contains("user_id")) {
				reqVal[i] = req.getParameter("userId");
				insertList[i] = "user_id";
				continue;
			} else if (req.getParameter("password") != "" && req.getParameter("password") != null
					&& !Arrays.toString(insertList).contains("password")) {
				reqVal[i] = req.getParameter("password");
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
			} else if (req.getParameter("loginStatus") != "" && req.getParameter("loginStatus") != null
					&& !Arrays.toString(insertList).contains("login_status")) {
				reqVal[i] = req.getParameter("loginStatus");
				insertList[i] = "login_status";
				continue;
			} else {
				break;
			}
		}
		String[] resizedArray = Arrays.copyOf(insertList, i);
		String[] reqArray = Arrays.copyOf(reqVal, i);

		/*
		 * UserDto userDto = new UserDto( req.getParameter("userId"),
		 * req.getParameter("password"), req.getParameter("name"),
		 * req.getParameter("email"), req.getParameter("phone"),
		 * req.getParameter("birthDate"), req.getParameter("joinDate"),
		 * req.getParameter("position"),
		 * Integer.parseInt(req.getParameter("departmentId")),
		 * req.getParameter("isAdmin"), req.getParameter("empStatus"),
		 * req.getParameter("workStatus"), req.getParameter("loginStatus") );
		 */

		userRequest.serviceInsert(resizedArray, reqArray);
		return "WEB-INF/view/adminForm.jsp";
	}
}
