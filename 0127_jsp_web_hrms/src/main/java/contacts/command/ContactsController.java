package contacts.command;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import contacts.model.ContactsDTO;
import contacts.service.ContactsService;
import mvc.command.CommandHandler;


public class ContactsController implements CommandHandler {

    // ContactsService 인스턴스 생성 / ContactsServiceインスタンス生成
    private ContactsService contactsService = new ContactsService();


    // 요청 처리 메서드 / リクエスト処理メソッド
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 검색 조건 가져오기 / 検索条件を取得
        String field = request.getParameter("field");   // 검색 필드 (all, name, email 등) / 検索フィールド (all, name, emailなど)
        String keyword = request.getParameter("keyword"); // 검색 키워드 / 検索キーワード

        // 부서별 연락처를 담을 Map / 部署別の連絡先を格納するMap
        Map<String, List<ContactsDTO>> groupedContacts;

        // 키워드가 존재하면 검색 수행 / キーワードが存在する場合は検索を実行
        if (keyword != null && !keyword.trim().isEmpty()) {
            // 입력한 필드와 키워드로 검색하고 부서별로 그룹핑 / 入力したフィールドとキーワードで検索し、部署別にグループ化
            groupedContacts = contactsService.searchGroupedByDepartment(field, keyword);
        } else {
            // 키워드가 없으면 전체 연락처를 부서별로 그룹핑 / キーワードがない場合は全ての連絡先を部署別にグループ化
            groupedContacts = contactsService.getGroupedByDepartment();
        }

        // JSP에 전달할 속성에 그룹화된 연락처 저장 / JSPに渡す属性にグループ化された連絡先を保存
        request.setAttribute("groupedContacts", groupedContacts);

        // JSP 경로 반환 / JSPパスを返す
        return "/WEB-INF/view/contacts.jsp";
    }

}
