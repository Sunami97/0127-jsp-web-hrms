package user.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import user.dto.UserDto;
import user.service.UserSelectService;

public class UserSelectHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/adminForm.jsp";
	private UserSelectService userService = new UserSelectService();
	
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
		String keyWord = req.getParameter("keyWord");
		String keyField = req.getParameter("keyField");
		List<UserDto> user = userService.Select(keyWord,keyField);
		int count = user.size();
		
		req.setAttribute("count", count);
		req.setAttribute("user", user);
		return "WEB-INF/view/adminForm.jsp";
	}

}
