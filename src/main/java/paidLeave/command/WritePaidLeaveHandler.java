package paidLeave.command;

import mvc.command.CommandHandler;
import paidLeave.model.PaidLeave;
import paidLeave.service.PaidLeaveRequest;
import paidLeave.service.WritePaidLeaveService;
import user.model.UserDTO;
import user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static oracle.sql.DATE.toDate;
import static oracle.sql.DATE.toTimestamp;

public class WritePaidLeaveHandler implements CommandHandler {
    private static final String FORM_VIEW = "/WEB-INF/view/paidLeaveForm.jsp";
    private WritePaidLeaveService writeService = new WritePaidLeaveService();
    private UserService userService = new UserService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) {
        if (req.getMethod().equals("GET")) {
            return processForm(req, res);
        } else if (req.getMethod().equals("POST")) {
            return processSubmit(req,res);
        } else {
            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return null;
        }
    }

    private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Boolean> errors = new HashMap<String, Boolean>();
        req.setAttribute("errors", errors);

        try {
            // 유저 정보 세션에서 가져옴
            UserDTO user = (UserDTO) req.getSession().getAttribute("loginUser");
            if (user == null) {
                res.sendRedirect(req.getContextPath() + "/login.do");
                return null;
            }

            // 요청 데이터 파싱
            PaidLeaveRequest writeReq = createWriteRequest(user, req);

            int newPaidLeaveNo = writeService.write(writeReq);
            req.setAttribute("newPaidLeaveNo", newPaidLeaveNo);

            return "/WEB-INF/view/paidLeaveList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("submitFailed", true);
            return FORM_VIEW;
        }
    }

    private PaidLeaveRequest createWriteRequest(UserDTO user, HttpServletRequest req) {
        try {
            //세션에서 받은 userId
            String userId = user.getUser_id();
            // form에서 선택한 관리자 ID
            String approvedBy = req.getParameter("approvedBy");

            // HTML form에서 전달된 파라미터들 받아오기
            String startDateStr = req.getParameter("startDate"); // ex: "2025-08-01"
            String endDateStr = req.getParameter("endDate");     // ex: "2025-08-03"
            String daysStr = req.getParameter("days");           // ex: "3.0"
            String reason = req.getParameter("reason");          // ex: "휴가 사유"

            // 날짜 문자열을 Date 객체로 변환
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            // 일수 변환
            double days = Double.parseDouble(daysStr);

            // PaidLeaveRequest에 모든 값 포함
            return new PaidLeaveRequest(userId, startDate, endDate, days, reason, approvedBy);
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
            return null; // 또는 예외 던지기
        }
    }

    private String processForm(HttpServletRequest req, HttpServletResponse res) {
        // 유저 정보 세션에서 가져옴
        UserDTO user = (UserDTO) req.getSession().getAttribute("loginUser");
        if (user == null) {
            try {
                res.sendRedirect(req.getContextPath() + "/login.do");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 관리자 목록 가져오기
        List<String> adminNames = userService.getAdminUsernames();
        req.setAttribute("adminnames", adminNames);

        return FORM_VIEW;
    }
}
