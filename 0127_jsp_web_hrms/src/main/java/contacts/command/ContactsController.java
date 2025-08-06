package contacts.command;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import contacts.model.ContactsDTO;
import contacts.service.ContactsService;
import mvc.command.CommandHandler;

public class ContactsController implements CommandHandler {

    private ContactsService contactsService = new ContactsService();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 부서별 그룹핑된 연락처 맵 가져오기
        // 部署ごとにグループ化された連絡先のマップを取得
        Map<String, List<ContactsDTO>> groupedContacts = contactsService.getGroupedByDepartment();

        // JSP에 전달
        // JSPに渡す
        request.setAttribute("groupedContacts", groupedContacts);

        // 뷰 반환
        // ビューのパスを返す
        return "/WEB-INF/view/contacts.jsp";
    }
}
