package contacts.dao;

import contacts.model.ContactsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// 연락처 DAO 클래스 / 連絡先DAOクラス
public class ContactsDAO {

	// 데이터베이스 연결을 생성하는 메서드 / データベース接続を生成するメソッド
    private Connection getConnection() throws SQLException {
        // DB 연결 정보 설정 / DB接続情報の設定
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 JDBC 접속 URL / Oracle JDBC接続URL
        String user = "system"; // DB 사용자 이름 / DBユーザー名
        String password = "1234"; // DB 비밀번호 / DBパスワード
        return DriverManager.getConnection(url, user, password); // 연결 반환 / 接続を返す
    }
 
    // 모든 연락처 정보를 조회하는 메서드 / 全ての連絡先情報を取得するメソッド
    public List<ContactsDTO> findAll() {
        List<ContactsDTO> list = new ArrayList<>(); // 결과를 담을 리스트 / 結果を格納するリスト

        // SQL 쿼리 작성: 부서명, 이름, 이메일, 전화번호, 입사일, 직급, 로그인 상태, 근무 상태를 조회
        // SQLクエリ作成: 部署名、名前、メール、電話番号、入社日、役職、ログイン状態、勤務状態を取得
        String sql = "SELECT " +
                     "d.department_name, " +
                     "u.name, " +
                     "u.email, " +
                     "u.phone, " +
                     "u.join_date, " +
                     "u.position, " +
                     "u.login_status, " +
                     "u.work_status " +
                     "FROM user_tbl u " +
                     "JOIN department_tbl d ON u.department_id = d.department_id " +
                     "WHERE u.retire_date IS NULL " + // 퇴사자 제외 / 退職者を除外
                     "ORDER BY d.department_name, u.name";

        // try-with-resources를 사용하여 DB 연결, SQL 실행, 결과셋 자동 닫기
        // try-with-resourcesを使用して、DB接続、SQL実行、結果セットを自動的に閉じる
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과셋 처리 / 結果セットの処理
            while (rs.next()) {
                String workStatus = rs.getString("work_status") != null
                        ? rs.getString("work_status") : "정상근무"; // null이면 기본값 설정 / nullの場合はデフォルト値を設定

                list.add(new ContactsDTO(
                        rs.getString("department_name"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("join_date"),
                        rs.getString("position"),
                        workStatus
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 출력 / 例外を出力
        }
        return list;
    }


    // 특정 필드와 키워드로 연락처를 검색하는 메서드 / 特定のフィールドとキーワードで連絡先を検索するメソッド
    public List<ContactsDTO> search(String field, String keyword) {
        List<ContactsDTO> list = new ArrayList<>();

        // 기본 SQL 쿼리 / 基本SQLクエリ
        String baseSql = "SELECT d.department_name, u.name, u.email, u.phone, " +
                         "u.join_date, u.position, u.login_status, u.work_status " +
                         "FROM user_tbl u " +
                         "JOIN department_tbl d ON u.department_id = d.department_id " +
                         "WHERE u.retire_date IS NULL ";

        // 검색 조건 추가 / 検索条件を追加
        String where = "";
        switch (field) {
            case "name": where = "AND u.name LIKE ?"; break;
            case "email": where = "AND u.email LIKE ?"; break;
            case "phone": where = "AND u.phone LIKE ?"; break;
            case "join_date": where = "AND TO_CHAR(u.join_date, 'YYYY-MM-DD') LIKE ?"; break;
            case "position": where = "AND u.position LIKE ?"; break;
            case "work_status": where = "AND u.work_status LIKE ?"; break;
            default:
                throw new IllegalArgumentException("지원하지 않는 검색 필드: " + field);
                // 日本語: サポートされていない検索フィールド
        }

        // 최종 SQL / 最終SQL
        String sql = baseSql + where + " ORDER BY d.department_name, u.name";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%"); // 키워드 바인딩 / キーワードをバインド

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String workStatus = rs.getString("work_status") != null
                            ? rs.getString("work_status") : "정상근무";

                    list.add(new ContactsDTO(
                            rs.getString("department_name"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getDate("join_date"),
                            rs.getString("position"),
                            workStatus
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 출력 / 例外を出力
        }
        return list;
    }
}
