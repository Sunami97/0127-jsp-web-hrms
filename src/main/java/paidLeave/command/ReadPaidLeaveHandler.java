package paidLeave.command;

import mvc.command.CommandHandler;
import paidLeave.service.PaidLeaveData;
import paidLeave.service.PaidLeaveNotFoundException;
import paidLeave.service.ReadPaidLeave;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadPaidLeaveHandler implements CommandHandler {
    private ReadPaidLeave readPaidLeave = new ReadPaidLeave();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String noVal = req.getParameter("no");
        int leaveNum = Integer.parseInt(noVal);
        try {
            PaidLeaveData paidLeaveData = readPaidLeave.getPaidLeave(leaveNum);
            req.setAttribute("paidLeaveData", paidLeaveData);
            return "/WEB-INF/view/readPaidLeave.jsp";
        } catch (PaidLeaveNotFoundException e) {
            req.getServletContext().log("no paidleave",e);
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }
}
