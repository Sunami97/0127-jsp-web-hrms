package contacts.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import contacts.dao.ContactsDAO;
import contacts.model.ContactsDTO;

// 연락망 비즈니스 로직을 처리하는 서비스 클래스
// 連絡網のビジネスロジックを処理するサービスクラス
public class ContactsService {

    private ContactsDAO contactsDAO;

    public ContactsService() {
        contactsDAO = new ContactsDAO();
        // DAO 객체 초기화 / DAOオブジェクトを初期化
    }

    // 부서별로 연락처를 그룹핑하여 반환
    // 部署ごとに連絡先をグループ化して返す
    public Map<String, List<ContactsDTO>> getGroupedByDepartment() {
        // 전체 연락처 목록 조회 / 全ての連絡先一覧を取得
        List<ContactsDTO> allContacts = contactsDAO.findAll();

        // 날짜 포맷터 준비 (yyyy-MM-dd) / 日付フォーマッターを準備 (yyyy-MM-dd)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 순서를 유지하는 LinkedHashMap 생성 / 順序を保持するLinkedHashMapを作成
        Map<String, List<ContactsDTO>> grouped = new LinkedHashMap<>();

        // 각 연락처 처리 / 各連絡先を処理
        for (ContactsDTO contact : allContacts) {

            // 입사일(Date → yyyy-MM-dd 문자열 변환) / 入社日(Date → yyyy-MM-dd文字列に変換)
            if (contact.getJoinDate() != null) {
                String formattedDate = sdf.format(contact.getJoinDate());
                contact.setJoinDateStr(formattedDate);
            } else {
                contact.setJoinDateStr(""); // null일 경우 빈 문자열 / nullの場合は空文字
            }

            // 부서별 그룹에 추가 / 部署ごとのグループに追加
            grouped
                .computeIfAbsent(contact.getDepartmentName(), k -> new ArrayList<>())
                .add(contact);
        }

        // 그룹핑된 결과 반환 / グループ化された結果を返す
        return grouped;
    }

    // 검색 필드와 키워드로 부서별 그룹핑된 연락처 반환
    // 検索フィールドとキーワードで部署ごとにグループ化された連絡先を返す
    public Map<String, List<ContactsDTO>> searchGroupedByDepartment(String field, String keyword) {
        List<ContactsDTO> contacts;

        if ("all".equalsIgnoreCase(field)) {
            // 전체 조회는 DAO의 findAll() 사용 / 全件取得はDAOのfindAll()を使用
            List<ContactsDTO> all = contactsDAO.findAll();

            // 입사일 문자열 세팅 / 入社日文字列を設定
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ContactsDTO c : all) {
                if (c.getJoinDate() != null) {
                    c.setJoinDateStr(sdf.format(c.getJoinDate()));
                } else {
                    c.setJoinDateStr("");
                }
            }

            contacts = new ArrayList<>();
            keyword = keyword.toLowerCase(); // 소문자로 변환하여 대소문자 무시 / 小文字に変換して大文字小文字を無視

            // 각 연락처가 키워드를 포함하는지 검사 / 各連絡先がキーワードを含むかをチェック
            for (ContactsDTO c : all) {
                boolean match =
                    (c.getName() != null && c.getName().toLowerCase().contains(keyword)) ||
                    (c.getEmail() != null && c.getEmail().toLowerCase().contains(keyword)) ||
                    (c.getPhone() != null && c.getPhone().toLowerCase().contains(keyword)) ||
                    (c.getJoinDateStr() != null && c.getJoinDateStr().contains(keyword)) ||
                    (c.getPosition() != null && c.getPosition().toLowerCase().contains(keyword)) ||
                    (c.getWorkStatus() != null && c.getWorkStatus().toLowerCase().contains(keyword));

                if (match) {
                    contacts.add(c); // 조건에 맞으면 리스트에 추가 / 条件に一致した場合リストに追加
                }
            }
        } else {
            // 특정 필드 검색은 DAO의 search() 사용 / 特定フィールド検索はDAOのsearch()を使用
            contacts = contactsDAO.search(field, keyword);

            // 입사일 문자열 세팅 / 入社日文字列を設定
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ContactsDTO c : contacts) {
                if (c.getJoinDate() != null) {
                    c.setJoinDateStr(sdf.format(c.getJoinDate()));
                } else {
                    c.setJoinDateStr("");
                }
            }
        }

        // 부서별 그룹핑 / 部署ごとにグループ化
        Map<String, List<ContactsDTO>> grouped = new LinkedHashMap<>();
        for (ContactsDTO contact : contacts) {
            grouped.computeIfAbsent(contact.getDepartmentName(), k -> new ArrayList<>()).add(contact);
        }

        return grouped;
    }

}
