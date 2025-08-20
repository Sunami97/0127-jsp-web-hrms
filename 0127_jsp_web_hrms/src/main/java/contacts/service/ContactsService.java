package contacts.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import contacts.dao.ContactsDAO;
import contacts.model.ContactsDTO;

/**
 * 연락망 비즈니스 로직을 처리하는 서비스 클래스
 * 連絡網のビジネスロジックを処理するサービスクラス
 */
public class ContactsService {

    private ContactsDAO contactsDAO;

    public ContactsService() {
        contactsDAO = new ContactsDAO();
        // DAO 객체 초기화
        // DAOオブジェクトを初期化
    }

    /**
     * 부서별로 연락처를 그룹핑하여 반환
     * 部署ごとに連絡先をグループ化して返す
     *
     * @return Map<String, List<ContactsDTO>> - 부서명 / 연락처 리스트
     *                                          部署名 / 連絡先リスト
     */
    public Map<String, List<ContactsDTO>> getGroupedByDepartment() {
        // 1. 전체 연락처 목록 조회
        // 1. 全ての連絡先一覧を取得
        List<ContactsDTO> allContacts = contactsDAO.findAll();

        // 2. 날짜 포맷터 준비 (yyyy-MM-dd)
        // 2. 日付フォーマッターを準備 (yyyy-MM-dd)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 3. 순서를 유지하는 LinkedHashMap 생성
        // 3. 順序を保持するLinkedHashMapを作成
        Map<String, List<ContactsDTO>> grouped = new LinkedHashMap<>();

        // 4. 각 연락처 처리
        // 4. 各連絡先を処理
        for (ContactsDTO contact : allContacts) {

            // 입사일(Date → yyyy-MM-dd 문자열 변환)
            // 入社日(Date → yyyy-MM-dd文字列に変換)
            if (contact.getJoinDate() != null) {
                String formattedDate = sdf.format(contact.getJoinDate());
                contact.setJoinDateStr(formattedDate);
            } else {
                contact.setJoinDateStr("");
            }

            // 부서별 그룹에 추가
            // 部署ごとのグループに追加
            grouped
                .computeIfAbsent(contact.getDepartmentName(), k -> new ArrayList<>())
                .add(contact);
        }

        // 5. 그룹핑된 결과 반환
        // 5. グループ化された結果を返す
        return grouped;
    }
}
