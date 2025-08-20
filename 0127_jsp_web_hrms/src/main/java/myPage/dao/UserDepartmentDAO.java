package myPage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import myPage.model.UserDepartmentDTO;

public class UserDepartmentDAO {

    // [1] 유저ID로 사용자(부서명 포함) 정보를 조회하는 메소드
    // [1] ユーザーIDで利用者（部署名含む）情報を取得するメソッド
    public UserDepartmentDTO selectUserById(Connection conn, String userId) throws SQLException {
        // 1. 사용자와 부서 정보를 조인하여 한 번에 가져오는 SQL을 작성한다
        // 1. 利用者と部署情報を結合して一度に取得するSQLを作成する
        String sql =
            "SELECT u.user_id, u.name, u.email, u.phone, u.birth_date, u.join_date, u.retire_date, " +
            "u.position, u.department_id, u.is_admin, u.emp_status, u.work_status, u.login_status, d.department_name " +
            "FROM user_tbl u LEFT JOIN department_tbl d ON u.department_id = d.department_id " +
            "WHERE u.user_id = ?";

        // 2. PreparedStatement를 생성한다
        // 2. PreparedStatement を生成する
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // 3. 플레이스홀더(?)에 userId를 바인딩한다
            // 3. プレースホルダ(?)に userId をバインドする
            ps.setString(1, userId);

            // 4. SQL을 실행하고 결과를 ResultSet으로 받는다
            // 4. SQL を実行し、結果を ResultSet で取得する
            try (ResultSet rs = ps.executeQuery()) {
                // 5. 결과가 존재하면 한 건을 읽는다
                // 5. 結果が存在すれば1件を読み込む
                if (rs.next()) {
                    // 6. DTO를 생성하고 각 컬럼 값을 설정한다
                    // 6. DTO を生成し、各カラム値を設定する
                    UserDepartmentDTO user = new UserDepartmentDTO();
                    user.setUserId(rs.getString("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setBirthDate(rs.getDate("birth_date"));
                    user.setJoinDate(rs.getDate("join_date"));
                    user.setRetireDate(rs.getDate("retire_date"));
                    user.setPosition(rs.getString("position"));
                    user.setDepartmentId(rs.getInt("department_id"));
                    user.setIsAdmin(rs.getString("is_admin"));
                    user.setEmpStatus(rs.getString("emp_status"));
                    user.setWorkStatus(rs.getString("work_status"));
                    user.setLoginStatus(rs.getString("login_status"));
                    user.setDepartmentName(rs.getString("department_name"));
                    // 7. 완성된 DTO를 반환한다
                    // 7. 完成した DTO を返却する
                    return user;
                }
            }
        }
        // 8. 결과가 없으면 null을 반환한다
        // 8. 結果が無ければ null を返却する
        return null;
    }

    // [2] 이름/이메일/전화번호 수정 (user_id 기준)
    // [2] 名前／メール／電話番号の更新（user_id 基準）
    public int updateUser(Connection conn, UserDepartmentDTO user) throws SQLException {
        // 1. 사용자 기본 정보를 업데이트하는 SQL을 작성한다
        // 1. 利用者の基本情報を更新するSQLを作成する
        String sql = "UPDATE user_tbl SET name=?, email=?, phone=? WHERE user_id=?";

        // 2. PreparedStatement를 생성하고 파라미터를 바인딩한다
        // 2. PreparedStatement を生成し、パラメータをバインドする
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getUserId());
            // 3. 실행 후 영향받은 행 수를 반환한다
            // 3. 実行後の影響行数を返却する
            return ps.executeUpdate();
        }
    }

    // [3] 확장용: 이메일/전화 등 정보 수정(현재는 제한적으로 사용)
    // [3] 拡張用：メール／電話など情報の更新（現状は限定的に使用）
    public int updateUserInfo(Connection conn, String userId, String name, String email, String phone,
                              String birthDate, String joinDate, String retireDate) throws Exception {
        // 1. 이메일/전화만 업데이트하는 SQL을 작성한다
        // 1. メール／電話のみを更新するSQLを作成する
        String sql = "UPDATE user_tbl SET email=?, phone=? WHERE user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // 2. 파라미터 바인딩 후 실행한다
            // 2. パラメータをバインドして実行する
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, userId);
            // 3. 영향행 수를 반환한다
            // 3. 影響行数を返却する
            return ps.executeUpdate();
        } catch (SQLException e) {
            // 4. 에러 로그 출력 후 도메인 예외로 감싸서 던진다
            // 4. エラーログを出力し、ドメイン例外でラップしてスローする
            System.out.println("[ERROR] updateUserInfo 실패: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("사용자 정보 업데이트 중 오류 발생", e);
        }
    }

    // [4] 비밀번호 변경 (현재 비밀번호 일치 시 갱신)
    // [4] パスワード変更（現在のパスワード一致時に更新）
    public int updatePassword(Connection conn, String userId, String currentPw, String newPw) throws SQLException {
        // 1. user_id와 기존 비밀번호가 일치할 때 새 비밀번호로 업데이트하는 SQL
        // 1. user_id と既存パスワード一致時に新パスワードへ更新するSQL
        String sql = "UPDATE user_tbl SET password=? WHERE user_id=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // 2. 파라미터를 바인딩하고 실행한다
            // 2. パラメータをバインドして実行する
            ps.setString(1, newPw);
            ps.setString(2, userId);
            ps.setString(3, currentPw);
            // 3. 영향행 수를 반환한다
            // 3. 影響行数を返却する
            return ps.executeUpdate();
        }
    }

    // [5] 저장된 비밀번호(해시 또는 평문)를 조회
    // [5] 保存済みパスワード（ハッシュまたは平文）を取得
    public String selectPasswordHashById(Connection conn, String userId) throws SQLException {
        // 1. TRIM으로 공백 패딩을 제거하여 password를 읽어오는 SQL
        // 1. TRIMで空白パディングを除去して password を読み込むSQL
        final String sql = "SELECT TRIM(password) FROM user_tbl WHERE user_id = ?";

        // 2. 파라미터 바인딩 후 조회를 수행한다
        // 2. パラメータをバインドして問い合わせを実行する
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                // 3. 결과가 있으면 첫 컬럼을 반환, 없으면 null
                // 3. 結果があれば第1カラムを返却、なければ null
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }

    // [6] 새 비밀번호 해시를 DB에 업데이트
    // [6] 新しいパスワードハッシュをDBに更新
    public int updatePasswordHash(Connection conn, String userId, String newHash) throws SQLException {
        // 1. 해당 user_id의 password를 새 해시로 갱신하는 SQL
        // 1. 当該 user_id の password を新しいハッシュに更新するSQL
        final String sql = "UPDATE user_tbl SET password = ? WHERE user_id = ?";

        // 2. 파라미터 바인딩 후 실행한다
        // 2. パラメータをバインドして実行する
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setString(2, userId);
            // 3. 영향행 수를 반환한다
            // 3. 影響行数を返却する
            return ps.executeUpdate();
        }
    }
}
