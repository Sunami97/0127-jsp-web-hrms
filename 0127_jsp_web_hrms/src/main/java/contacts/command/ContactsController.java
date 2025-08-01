package contacts.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import contacts.dao.ContactsDAO;
import contacts.model.ContactsDTO;
import mvc.command.CommandHandler;

public class ContactsController implements CommandHandler {

    private ContactsDAO contactsDAO = new ContactsDAO(); // 필요 시 생성자 주입 가능

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 연락처 목록 가져오기
        List<ContactsDTO> contactsList = contactsDAO.findAll();

        // JSP에 전달
        request.setAttribute("contactsList", contactsList);

        // 뷰 반환
        return "/WEB-INF/view/contacts.jsp"; // 실제 파일 경로에 맞게 조정
    }
}
