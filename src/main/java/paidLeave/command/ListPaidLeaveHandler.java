package paidLeave.command;

import mvc.command.CommandHandler;
import paidLeave.service.ListPaidLeave;
import paidLeave.service.PaidLeavePage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListPaidLeaveHandler implements CommandHandler {
    private ListPaidLeave listPaidLeave = new ListPaidLeave();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageNoVal = req.getParameter("pageNo");
        int pageNo = 1;
        if (pageNoVal != null) {
            pageNo = Integer.parseInt(pageNoVal);
        }
        PaidLeavePage paidLeavePage = listPaidLeave.getPaidLeavePage(pageNo);
        req.setAttribute("paidLeavePage", paidLeavePage);
        return "/WEB-INF/view/paidLeaveList.jsp";
    }
}
