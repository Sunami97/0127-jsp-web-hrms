package paidLeave.command;

import mvc.command.CommandHandler;
import paidLeave.service.PaidLeavePage;
import paidLeave.service.ReadPaidLeave;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PaidLeaveStatusHandler implements CommandHandler {
    ReadPaidLeave readPaidLeave = new ReadPaidLeave();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 요청 파라미터 받기
        String leaveIdParam = req.getParameter("leaveId");
        String status = req.getParameter("status");

        if(leaveIdParam == null || status == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        int leaveId = Integer.parseInt(leaveIdParam);

        // 서비스 객체를 통해 상태 업데이트
        readPaidLeave.updatePaidLeave(leaveId, status);
        res.sendRedirect(req.getContextPath() + "/paidleave.do");
        return null;
    }
}
