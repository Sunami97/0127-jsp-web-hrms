package myPage.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import myPage.dao.UserDepartmentDAO;
import myPage.model.UserDepartmentDTO;
import util.BCryptUtil;

/**
 * UserService 클래스  
 * - 마이페이지에서 사용하는 비즈니스 로직(실제 서비스 동작)을 처리하는 클래스다  
 * - 데이터베이스(DB)와 직접 연결하지 않고, DAO를 통해서만 DB 작업을 한다  
 * - 핸들러(컨트롤러)와 DAO의 중간 역할을 한다  
 *
 * UserService クラス  
 * - マイページで使用するビジネスロジック（実際のサービス動作）を処理するクラス  
 * - データベース(DB)と直接接続せず、DAOを通じてのみDB操作を行う  
 * - ハンドラー（コントローラー）とDAOの仲介役を果たす  
 */
public class UserService {

    // 회원정보 조회 (유저ID로 내 정보+부서명까지 가져오기)
    // 会員情報の取得（ユーザーIDで自分の情報＋部署名まで取得）
    public UserDepartmentDTO getUserById(String userId) throws SQLException {
        // 1. DB 연결(Connection 객체)을 얻어서 conn에 저장 (자동 close)
        // 1. DB接続(Connectionオブジェクト)を取得してconnに格納（自動クローズ）
        try (Connection conn = ConnectionProvider.getConnection()) {
            // 2. DAO 객체 생성
            // 2. DAOオブジェクトを生成
            UserDepartmentDAO dao = new UserDepartmentDAO();
            // 3. DAO 메소드 호출 → DB에서 조회한 결과를 반환
            // 3. DAOメソッドを呼び出し → DBから取得した結果を返す
            return dao.selectUserById(conn, userId);
        }
    }

    // 회원정보 수정 (이름/이메일/전화번호 등)
    // 会員情報の更新（名前/メール/電話番号など）
    public boolean updateUser(UserDepartmentDTO user) throws SQLException {
        // 1. DB 연결
        // 1. DB接続
        try (Connection conn = ConnectionProvider.getConnection()) {
            // 2. DAO 객체 생성
            // 2. DAOオブジェクトを生成
            UserDepartmentDAO dao = new UserDepartmentDAO();
            // 3. DAO 메소드 호출 → DB 수정 실행, 결과 행 수를 result에 저장
            // 3. DAOメソッドを呼び出し → DB更新を実行、結果行数をresultに格納
            int result = dao.updateUser(conn, user);
            // 4. 수정 성공 여부 반환 (1 이상이면 true)
            // 4. 更新成功かどうかを返す（1以上ならtrue）
            return result > 0;
        }
    }

    // 로그인 시 비밀번호 검증 + (레거시 비밀번호라면) 해시 업그레이드
    // ログイン時にパスワード検証＋（レガシーパスワードなら）ハッシュへアップグレード
    public boolean loginAndMaybeUpgrade(String userId, String inputPw) throws SQLException {
        try (Connection conn = ConnectionProvider.getConnection()) {
            // DAO 객체 생성
            // DAOオブジェクト生成
            UserDepartmentDAO dao = new UserDepartmentDAO();

            // DB에서 저장된 비밀번호(해시 또는 평문) 조회
            // DBから保存されたパスワード（ハッシュまたは平文）を取得
            String stored = dao.selectPasswordHashById(conn, userId);

            // 저장된 값이 없으면 로그인 실패
            // 保存された値がなければログイン失敗
            if (stored == null)
                return false;

            // BCryptUtil.check() → 입력 비밀번호와 저장된 값 비교
            // BCryptUtil.check() → 入力パスワードと保存値を比較
            boolean ok = BCryptUtil.check(inputPw, stored);

            if (!ok)
                return false;

            // 저장된 값이 BCrypt 해시인지 확인
            // 保存された値がBCryptハッシュかどうか確認
            boolean isBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");

            // 레거시 평문이면 해시로 업그레이드
            // レガシー平文ならハッシュにアップグレード
            if (!isBcrypt) {
                String newHash = BCryptUtil.hash(inputPw);
                dao.updatePasswordHash(conn, userId, newHash);
            }

            return true; // 로그인 성공
            // ログイン成功
        }
    }

    // 마이페이지에서 비밀번호 변경
    // マイページでのパスワード変更
    public boolean updatePassword(String userId, String currentPw, String newPw) throws SQLException {
        try (Connection conn = ConnectionProvider.getConnection()) {
            UserDepartmentDAO dao = new UserDepartmentDAO();

            // 현재 비밀번호(해시 또는 평문) 조회
            // 現在のパスワード（ハッシュまたは平文）を取得
            String stored = dao.selectPasswordHashById(conn, userId);

            if (stored == null)
                return false;

            // 현재 비밀번호 검증
            // 現在のパスワードを検証
            if (!BCryptUtil.check(currentPw, stored))
                return false;

            // 새 비밀번호를 BCrypt 해시로 변환
            // 新しいパスワードをBCryptハッシュに変換
            String newHash = BCryptUtil.hash(newPw);

            // DB에 업데이트 → 성공 시 true
            // DBに更新 → 成功ならtrue
            return dao.updatePasswordHash(conn, userId, newHash) > 0;
        }
    }
}
