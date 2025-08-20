package attendance.command;

import java.util.List;
import javax.servlet.http.*;

import attendance.model.AttendanceRecord;
import attendance.service.AttendanceService;
import mvc.command.CommandHandler;
import user.model.UserDTO;

public class AttendanceHandler implements CommandHandler {

    private final AttendanceService service = new AttendanceService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        UserDTO login = (session != null) ? (UserDTO) session.getAttribute("loginUser") : null;
        if (login == null) {
            res.sendRedirect(req.getContextPath() + "/index.jsp");
            return null;
        }
        
        String pageParam = req.getParameter("page");
        int page = 1;
        try { if (pageParam != null && !pageParam.isEmpty()) page = Integer.parseInt(pageParam); } catch (Exception ignore) {}
        int size = 6;

        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String action = req.getParameter("action"); // "in" | "out"
            if ("in".equals(action)) {
                service.clockIn(login.getUser_id());
            } else if ("out".equals(action)) {
                String idStr = req.getParameter("statusId");
                if (idStr != null && !idStr.isEmpty()) {
                    long statusId = Long.parseLong(idStr);
                    service.clockOut(login.getUser_id(), statusId);
                }
            }
         //  현재 페이지 유지한 채로 리다이렉트
            String qp = "?page=" + page;
            res.sendRedirect(req.getContextPath() + "/attendance.do" + qp);
            return null;
        }

     // GET: 페이지 데이터 + 상태
        AttendanceService.PageResult<AttendanceRecord> pr = service.getHistoryPage(login.getUser_id(), page, size);
        req.setAttribute("history", pr.getItems()); // 기존 JSP 호환 유지
        req.setAttribute("pager", pr);
        req.setAttribute("currentPage", pr.getPage());
        req.setAttribute("totalPages", pr.getTotalPages());
        req.setAttribute("hasPrev", pr.getHasPrev());
        req.setAttribute("hasNext", pr.getHasNext());
        req.setAttribute("startPage", pr.getStartPage());
        req.setAttribute("endPage", pr.getEndPage());

        req.setAttribute("hasOpen", service.hasOpen(login.getUser_id()));
        return "/WEB-INF/view/attendance.jsp";
    }
}
