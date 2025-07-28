package myPage.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import myPage.dao.UserDAO;

public class MypageEditProHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		// 파라미터 받기
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String birthDate = request.getParameter("birthDate");
		String joinDate = request.getParameter("joinDate");
		String retireDate = request.getParameter("retireDate");
		// 부서, 직책은 readonly라 변경 불가

		// (이미지 등 파일 업로드는 추후 추가)

		try (Connection conn = ConnectionProvider.getConnection()) {
			UserDAO dao = new UserDAO();
			int result = dao.updateUserInfo(conn, userId, name, email, phone, birthDate, joinDate, retireDate);

			System.out.println("result" + result);

			// 처리 결과에 따라
			if (result > 0) {
				request.setAttribute("msg", "정보가 성공적으로 수정되었습니다.");
			} else {
				request.setAttribute("msg", "수정에 실패했습니다. 다시 시도해주세요.");
			}
		}
		// 수정 후 다시 mypage로 이동
		return "/WEB-INF/view/mypageEditResult.jsp";
	}
}
