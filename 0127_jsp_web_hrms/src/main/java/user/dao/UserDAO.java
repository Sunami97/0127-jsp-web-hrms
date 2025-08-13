package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.model.UserDTO;

import util.BCryptUtil; // 비밀번호 암호화/검증 유틸리티 클래스 // パスワードの暗号化／検証ユーティリティクラス


// UserDAO (Data Access Object)
// - user_tbl 테이블과 관련된 데이터베이스 작업을 수행하는 클래스 // user_tbl テーブルに関するデータベース操作を行うクラス
// - 로그인, 아이디 존재 여부 확인, 로그인 상태 변경 등을 처리 // ログイン、ID の存在確認、ログイン状態の更新などを処理



// UserDAO (Data Access Object)
// - user_tbl 테이블과 관련된 데이터베이스 작업을 수행하는 클래스
// - 로그인, 아이디 존재 여부 확인, 로그인 상태 변경 등을 처리


public class UserDAO {

    //

    // 로그인 처리 메소드 // ログイン処理メソッド
    // 1. 입력한 user_id로 DB에서 사용자 정보를 가져옴 // 1. 入力された user_id で DB からユーザー情報を取得
    // 2. DB에 저장된 암호화된 비밀번호와 사용자가 입력한 비밀번호를 비교 // 2. DB に保存されたハッシュ化パスワードと入力パスワードを比較
    // 3. 일치하면 UserDTO 객체에 사용자 정보를 담아 반환 // 3. 一致すれば UserDTO に詰めて返却
     
    public UserDTO login(Connection conn, String user_id, String plainPassword) throws Exception {
        String sql = "SELECT * FROM user_tbl WHERE user_id = ?"; // 특정 사용자 검색 쿼리 // 特定ユーザー検索クエリ
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id); // 첫 번째 ? 에 user_id 바인딩 // 1 番目の ? に user_id をバインド
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 해당 ID의 사용자가 존재하는 경우 // 該当 ID のユーザーが存在する場合
                    String dbHashedPassword = rs.getString("password"); // DB에 저장된 해시 비밀번호 // DB に保存されたハッシュ化パスワード
                    
                    // BCrypt를 이용해 평문 비밀번호와 해시값 비교 // BCrypt で平文パスワードとハッシュ値を比較
                    if (BCryptUtil.check(plainPassword, dbHashedPassword)) {
                        // 비밀번호 일치 -> UserDTO에 사용자 정보 저장 // パスワード一致 → UserDTO にユーザー情報を格納
                        UserDTO user = new UserDTO();
                        user.setUser_id(rs.getString("user_id"));
                        user.setPassword(dbHashedPassword); // 해시값 저장 // ハッシュ値を保存

                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPhone(rs.getString("phone"));
                        user.setBirth_date(rs.getDate("birth_date"));
                        user.setJoin_date(rs.getDate("join_date"));
                        user.setRetire_date(rs.getDate("retire_date"));
                        user.setPosition(rs.getString("position"));
                        user.setDepartment_id(rs.getInt("department_id"));
                        user.setIs_admin(rs.getString("is_admin"));
                        user.setEmp_status(rs.getString("emp_status"));
                        user.setWork_status(rs.getString("work_status"));
                        user.setLogin_status(rs.getString("login_status"));

                        return user; // 로그인 성공 시 UserDTO 반환 // ログイン成功時は UserDTO を返却
                    } else {
                        // 비밀번호 불일치 -> 로그인 실패 // パスワード不一致 → ログイン失敗

                        return null;
                    }
                }
            }
        }

        // 해당 ID의 사용자가 없으면 null 반환 // 該当 ID のユーザーがいなければ null を返却

        return null;
    }

    

    // 아이디 존재 여부 확인 메소드 // ID 存在確認メソッド
    // - 회원가입 또는 로그인 시, 해당 아이디가 이미 존재하는지 확인 // - 新規登録やログイン時に、その ID が既に存在するか確認
    public boolean isUserExist(Connection conn, String user_id) throws Exception {
        String sql = "SELECT COUNT(*) FROM user_tbl WHERE user_id=?"; // 아이디 개수 세기 // ID の件数を数える
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id); // SQL 쿼리의 첫 번째 ?에 user_id 값 넣기 // クエリの 1 番目の ? に user_id を設定
            try (ResultSet rs = pstmt.executeQuery()) { // 쿼리 실행 후 결과 받기 // クエリ実行後に結果を取得
                if (rs.next()) { // 결과 집합에서 첫 번째 행으로 이동 // 結果セットの最初の行へ移動
                    return rs.getInt(1) > 0; // COUNT(*) 값이 0보다 크면 해당 아이디가 존재 // COUNT(*) が 0 より大きければ存在
                }
            }
        }
        // 조건에 맞는 데이터가 없으면 false 반환 // 条件に合うデータがなければ false を返却

        return false;
    }


    // 로그인 상태 업데이트 메소드 // ログイン状態更新メソッド
    // - 로그인 시 "Y", 로그아웃 시 "N"으로 상태 변경 // - ログイン時は「Y」、ログアウト時は「N」に更新


    public void updateLoginStatus(Connection conn, String userId, String status) throws SQLException {
        String sql = "UPDATE user_tbl SET login_status = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status); // 첫 번째 ?에 status 값 넣기 // 1 番目の ? に status を設定
            pstmt.setString(2, userId); // 두 번째 ?에 userId 값 넣기 // 2 番目の ? に userId を設定
            pstmt.executeUpdate(); // UPDATE 실행 (DB 값 변경) // UPDATE 実行（DB の値を更新）

        }
    }
}
