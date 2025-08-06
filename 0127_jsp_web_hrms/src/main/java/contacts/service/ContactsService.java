package contacts.service;

import contacts.model.ContactsDTO;
import contacts.dao.ContactsDAO;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class ContactsService {

    private ContactsDAO contactsDAO;

    public ContactsService() {
        contactsDAO = new ContactsDAO(); // DAO 초기화
        // DAOを初期化
    }

    // 전체 연락처 목록 그대로 반환 (기존 방식 유지)
    // 全ての連絡先リストをそのまま返す（既存の方法を維持）
    public List<ContactsDTO> getAllContacts() {
        return contactsDAO.findAll();
    }

    // 부서별로 연락처 그룹핑된 맵 반환
    // 部署ごとに連絡先をグループ化したマップを返す
    public Map<String, List<ContactsDTO>> getGroupedByDepartment() {
        List<ContactsDTO> allContacts = contactsDAO.findAll(); // 전체 목록 불러오기
        // 全ての連絡先リストを取得

        Map<String, List<ContactsDTO>> grouped = new LinkedHashMap<>(); // 순서 유지 맵
        // 順序を保持するLinkedHashMapを作成

        for (ContactsDTO contact : allContacts) {
            String deptName = contact.getDepartmentName();

            // 부서별 그룹 생성 및 추가
            // 部署ごとのグループを作成し、追加
            grouped
                .computeIfAbsent(deptName, k -> new ArrayList<>())
                .add(contact);
        }

        return grouped;
    }
}
