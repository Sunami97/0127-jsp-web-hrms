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
        // ページ番号の取得（デフォルトは1ページ目）
        String pageNoVal = req.getParameter("pageNo");
        int pageNo = 1;
        if (pageNoVal != null) {
            pageNo = Integer.parseInt(pageNoVal);
        }
        // 指定ページの有給休暇申請データを取得
        PaidLeavePage paidLeavePage = listPaidLeave.getPaidLeavePage(pageNo);
        // 取得したデータをリクエスト属性に保存
        req.setAttribute("paidLeavePage", paidLeavePage);
        return "/WEB-INF/view/paidLeaveList.jsp";
    }
}
