package attendance.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import attendance.model.AttendanceRecord;
import attendance.service.AttendanceService;
import mvc.command.CommandHandler;
import user.model.UserDTO;

// 출퇴근 화면과 출근/퇴근 액션을 처리하는 핸들러 // 出退勤画面と出勤/退勤アクションを処理するハンドラ
// - GET: 페이지네이션된 출퇴근 내역 + 현재 상태를 조회하여 뷰에 전달 // - GET: ページネーションされた出退勤履歴＋現在状態を取得してビューへ渡す
// - POST: 출근/퇴근 요청을 처리 후 같은 페이지로 리다이렉트 // - POST: 出勤/退勤リクエストを処理後、同じページへリダイレクト

public class AttendanceHandler implements CommandHandler {

	private final AttendanceService service = new AttendanceService(); // 서비스 의존성 // サービス依存性

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 1) 로그인 사용자 획득(미로그인 시 index로) // 1) ログインユーザー取得（未ログインならindexへ）
		HttpSession session = req.getSession(false);
		UserDTO login = (session != null) ? (UserDTO) session.getAttribute("loginUser") : null;
		if (login == null) {
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		}

		// 2) 페이지 파라미터 처리 (기본 1) // 2) ページパラメータ処理（デフォルト1）
		String pageParam = req.getParameter("page");
		int page = 1;
		try {
			if (pageParam != null && !pageParam.isEmpty())
				page = Integer.parseInt(pageParam);
		} catch (Exception ignore) {
		}
		final int size = 6; // 페이지당 6건 // 1ページあたり6件

		// 3) POST: 출근/퇴근 액션 처리 // 3) POST: 出勤/退勤アクション処理
		if ("POST".equalsIgnoreCase(req.getMethod())) {
			String action = req.getParameter("action"); // "in" | "out"

			if ("in".equals(action)) { // 출근 // 出勤
				service.clockIn(login.getUser_id());
			} else if ("out".equals(action)) { // 퇴근 // 退勤
				String idStr = req.getParameter("statusId");
				if (idStr != null && !idStr.isEmpty()) {
					long statusId = Long.parseLong(idStr);
					service.clockOut(login.getUser_id(), statusId);
				}
			}

			// 중복 POST 방지 + 현재 페이지 유지 리다이렉트 // 二重POST防止＋現在ページ維持でリダイレクト
			res.sendRedirect(req.getContextPath() + "/attendance.do?page=" + page);
			return null;
		}

		// 4) GET: 페이지네이션된 내역/현재 상태 조회 // 4) GET: ページネーションされた履歴/現在状態の取得
		AttendanceService.PageResult<AttendanceRecord> pr = service.getHistoryPage(login.getUser_id(), page, size);

		req.setAttribute("history", pr.getItems()); // 표 데이터 // 表データ
		req.setAttribute("pager", pr); // 페이지 정보 // ページ情報
		req.setAttribute("currentPage", pr.getPage());
		req.setAttribute("totalPages", pr.getTotalPages());
		req.setAttribute("hasPrev", pr.getHasPrev());
		req.setAttribute("hasNext", pr.getHasNext());
		req.setAttribute("startPage", pr.getStartPage());
		req.setAttribute("endPage", pr.getEndPage());

		req.setAttribute("hasOpen", service.hasOpen(login.getUser_id())); // 현재 출근 중 여부 // 現在出勤中かどうか

		// 뷰로 포워딩 // ビューへフォワード
		return "/WEB-INF/view/attendance.jsp";
	}
}
