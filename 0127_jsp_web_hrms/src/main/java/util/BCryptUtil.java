package util;

import org.mindrot.jbcrypt.BCrypt;

// - 비밀번호를 안전하게 저장하고 검증하기 위한 유틸리티 클래스 // - パスワードを安全に保存し、検証するためのユーティリティクラス
// - BCrypt 라이브러리를 사용하여 비밀번호를 암호화(해싱)하고, 비교하는 기능 제공 // - BCryptライブラリを使用してパスワードを暗号化（ハッシュ化）し、比較する機能を提供

public class BCryptUtil {
	 
     // 비밀번호 해시(암호화) 메서드 // パスワードハッシュ（暗号化）メソッド
     // plainPassword : 사용자가 입력한 평문 비밀번호(암호화 전) // plainPassword : ユーザーが入力した平文パスワード（暗号化前）
     // return 해시(암호화)된 비밀번호 문자열 // 戻り値：ハッシュ（暗号化）されたパスワード文字列

     // 설명: // 説明:
     // - BCrypt.hashpw() : 비밀번호를 해시값으로 변환 // - BCrypt.hashpw() : パスワードをハッシュ値に変換
     // - BCrypt.gensalt() : 무작위 '솔트(salt)' 값을 생성하여 해시에 포함 // - BCrypt.gensalt() : ランダムな「ソルト」値を生成してハッシュに含める
     //   → 같은 비밀번호라도 솔트가 다르면 해시값도 달라져서 보안이 강화됨 //   → 同じパスワードでもソルトが異なればハッシュ値も変わり、セキュリティが強化される
	public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 비밀번호 비교(평문과 해시) // パスワード比較（平文とハッシュ）
	//
    // 비밀번호 검증 메서드 // パスワード検証メソッド
    // plainPassword  : 사용자가 입력한 평문 비밀번호 // plainPassword  : ユーザーが入力した平文パスワード
    // hashedPassword : DB에 저장된 해시 비밀번호 // hashedPassword : DBに保存されたハッシュパスワード
    // return 비밀번호가 일치하면 true, 다르면 false // 戻り値：パスワードが一致すれば true、一致しなければ false
    //
    // 설명: // 説明:
    // - BCrypt.checkpw() : 평문 비밀번호를 해시 알고리즘으로 비교 // - BCrypt.checkpw() : 平文パスワードをハッシュアルゴリズムで比較
    // - plainPassword나 hashedPassword가 null이면 false 반환 // - plainPassword または hashedPassword が null の場合は false を返す
    public static boolean check(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) return false;
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
