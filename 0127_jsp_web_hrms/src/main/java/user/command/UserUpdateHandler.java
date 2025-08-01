package user.command;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import oracle.sql.ARRAY;
import user.dto.UserDto;
import user.service.UserUpdateService;

public class UserUpdateHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private UserUpdateService userService = new UserUpdateService();

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
		String[] reqVal = new String[6];
		String[] updateList = new String[6];
		for (int i = 0; i < 6; i++) {
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
			} else {
				break;
			}
		}

		userService.userUpdate(userId, updateList, reqVal);

		return "WEB-INF/view/adminForm.jsp";
	}

}
