package myPage.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import myPage.dao.UserDAO;
import myPage.dto.UserDepartmentDTO;

public class MypageHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 고정값 예시 (user02)
        String userId = "user02";
        
        try (Connection conn = ConnectionProvider.getConnection()) {   
        UserDAO dao = new UserDAO();
        UserDepartmentDTO user = dao.selectUserById(conn, userId);

        request.setAttribute("user", user);
        }
        return "/WEB-INF/view/mypage.jsp";
    }
}
