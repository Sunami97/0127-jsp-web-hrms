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
    // 有給休暇申請フォームのビュー
    private static final String FORM_VIEW = "/WEB-INF/view/paidLeaveForm.jsp";
    // 有給休暇申請登録サービス
    private WritePaidLeaveService writeService = new WritePaidLeaveService();
    // ユーザー情報取得サービス
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

    // フォーム送信時の処理
    private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Boolean> errors = new HashMap<String, Boolean>();
        req.setAttribute("errors", errors);

        try {
            // セッションからログインユーザー情報を取得
            UserDTO user = (UserDTO) req.getSession().getAttribute("loginUser");
            if (user == null) {
                // 未ログインの場合、ログインページへリダイレクト
                res.sendRedirect(req.getContextPath() + "/login.do");
                return null;
            }

            // リクエストパラメータから申請情報を生成
            PaidLeaveRequest writeReq = createWriteRequest(user, req);
            if (writeReq == null) {
                errors.put("parseFailed", true);
                return FORM_VIEW;
            }

            // 有給休暇申請を登録し、新規申請番号を取得
            int newPaidLeaveNo = writeService.write(writeReq);
            req.setAttribute("newPaidLeaveNo", newPaidLeaveNo);

            res.sendRedirect(req.getContextPath() + "/paidleave.do");
            return null;
        } catch (IllegalArgumentException e) {
            errors.put("dateError", true); // JSP에서 ${errors.dateError} 체크 가능
            return FORM_VIEW;
        } catch (RuntimeException e) {
            e.printStackTrace();
            errors.put("parseFailed", true);
            return FORM_VIEW;
        } catch (Exception e) {
            e.printStackTrace();
            errors.put("submitFailed", true);
            return FORM_VIEW;
        }
    }

    // リクエスト情報から PaidLeaveRequest オブジェクトを生成
    private PaidLeaveRequest createWriteRequest(UserDTO user, HttpServletRequest req) {
        try {
            // 引数で受け取ったユーザー情報からユーザーIDを取得
            String userId = user.getUser_id();
            // フォームで選択された承認者ID
            String approvedBy = req.getParameter("approvedBy");

            // HTMLフォームから送信された値を取得
            String startDateStr = req.getParameter("startDate"); // 開始日 (例: "2025-08-01")
            String endDateStr = req.getParameter("endDate");     // 終了日 (例: "2025-08-03")
            String daysStr = req.getParameter("days");           // 使用日数 (例: "3.0")
            String reason = req.getParameter("reason");          // 申請理由

            // 文字列の日付を Date 型に変換
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            if (endDate.before(startDate)) {
                throw new IllegalArgumentException("終了日は開始日以降でなければなりません。");
            }

            // JSで計算された日数を double に変換
            double days = Double.parseDouble(daysStr);

            // 申請情報をまとめてオブジェクト化
            return new PaidLeaveRequest(userId, startDate, endDate, days, reason, approvedBy);
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    // フォーム表示時の処理
    private String processForm(HttpServletRequest req, HttpServletResponse res) {
        // セッションからログインユーザー情報を取得
        UserDTO user = (UserDTO) req.getSession().getAttribute("loginUser");
        // 未ログインの場合
        if (user == null) {
            try {
                res.sendRedirect(req.getContextPath() + "/login.do");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 管理者一覧を取得
        List<String> adminNames = userService.getAdminUsernames();
        req.setAttribute("adminnames", adminNames);

        return FORM_VIEW;
    }
}
