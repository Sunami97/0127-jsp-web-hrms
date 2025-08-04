package user.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import user.service.UserFireService;

public class UserFireHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private UserFireService userService = new UserFireService();

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
		String date = req.getParameter("date");
		String userId = req.getParameter("userId");
		userService.fire(userId, date);
		
		return "WEB-INF/view/adminForm.jsp";
	}
}
