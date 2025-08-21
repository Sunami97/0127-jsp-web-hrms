package contacts.dao;

import contacts.model.ContactsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsDAO {

    // 데이터베이스 연결을 생성하는 메서드
    // データベース接続を生成するメソッド
    private Connection getConnection() throws SQLException {
        // DB 연결 정보 설정
        // DB接続情報の設定
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 JDBC 접속 URL / Oracle JDBC接続URL
        String user = "system"; // DB 사용자 이름 / DBユーザー名
        String password = "1234"; // DB 비밀번호 / DBパスワード
        return DriverManager.getConnection(url, user, password); // 연결 반환 / 接続を返す
    }

    // 모든 연락처 정보를 조회하는 메서드
    // 全ての連絡先情報を取得するメソッド
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
                     "s.status_type " +
                     "FROM user_tbl u " +
                     "JOIN department_tbl d ON u.department_id = d.department_id " +
                     "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id AND s.is_current = 'Y' " +
                     "WHERE u.retire_date IS NULL " +
                     "ORDER BY d.department_name, u.name";

        // try-with-resources를 사용하여 DB 연결, SQL 실행, 결과셋 자동 닫기
        // try-with-resourcesを使用して、DB接続、SQL実行、結果セットを自動的に閉じる
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {


        	while (rs.next()) {
                String loginStatus = "Y".equals(rs.getString("login_status")) ? "login" : "logout";
                String statusType = rs.getString("status_type") != null
                        ? rs.getString("status_type") : "정상근무";

                list.add(new ContactsDTO(
                        rs.getString("department_name"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("join_date"),
                        rs.getString("position"),
                        loginStatus,
                        statusType
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
