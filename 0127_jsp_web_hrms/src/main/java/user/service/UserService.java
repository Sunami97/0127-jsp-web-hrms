package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import user.dao.UserDAO;
import user.model.UserDTO;

// UserService 클래스 // UserService クラス
// - 사용자 관련 비즈니스 로직을 처리하는 서비스 계층 클래스 // - ユーザー関連のビジネスロジックを処理するサービス層クラス
// - DAO(UserDAO)를 호출하여 데이터베이스 작업을 수행하고,  // - DAO（UserDAO）を呼び出してデータベース操作を行い、
//   그 결과를 컨트롤러(핸들러)에 전달 //   その結果をコントローラ（ハンドラ）へ渡す
// - 로그인, 아이디 존재 여부 확인, 로그인 상태 변경 기능을 제공 // - ログイン、ID存在確認、ログイン状態更新機能を提供

public class UserService {
	private UserDAO userDAO = new UserDAO(); // UserDAO 객체 생성 // UserDAO オブジェクトを生成

    // 로그인 메소드 // ログインメソッド
    // - user_id와 비밀번호를 받아 DAO의 login 메소드를 호출  // - user_id とパスワードを受け取り、DAO の login メソッドを呼び出す
    // - 로그인 성공 시 UserDTO 객체 반환, 실패 시 null 반환  // - ログイン成功時は UserDTO を返却、失敗時は null を返却
    public UserDTO login(Connection conn, String user_id, String password) throws Exception {
        return userDAO.login(conn, user_id, password);
    }
    
    // 아이디 존재 여부 확인 메소드  // ID存在確認メソッド
    // - 주어진 user_id가 DB에 존재하는지 확인   // - 指定された user_id がDBに存在するか確認
    // - 존재하면 true, 존재하지 않으면 false 반환  // - 存在すれば true、存在しなければ false を返却

    public boolean isUserExist(Connection conn, String user_id) throws Exception {
        return userDAO.isUserExist(conn, user_id);
    }

    // 로그인 상태 변경 메소드  // ログイン状態変更メソッド
    // - 로그인 시 status 값을 "Y", 로그아웃 시 "N"으로 설정  // - ログイン時は status を「Y」、ログアウト時は「N」に設定
    // - DAO의 updateLoginStatus 메소드를 호출하여 DB에 반영  // - DAO の updateLoginStatus メソッドを呼び出してDBに反映

    public void updateLoginStatus(Connection conn, String userId, String status) throws SQLException {
        userDAO.updateLoginStatus(conn, userId, status);
    }
}
