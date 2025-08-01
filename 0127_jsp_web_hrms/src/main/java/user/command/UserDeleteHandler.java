package user.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import user.dto.UserDto;
import user.service.UserDeleteService;

public class UserDeleteHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private UserDeleteService userService = new UserDeleteService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			return processForm(req, res);
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(req,res);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		String[] userId = req.getParameterValues("userId");
		 userService.Delete(userId);
		
		return "WEB-INF/view/adminForm.jsp";
	}

}
