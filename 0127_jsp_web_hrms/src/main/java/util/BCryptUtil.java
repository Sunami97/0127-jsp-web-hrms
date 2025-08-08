package util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

    public static String hash(String plainPassword) {
        if (plainPassword == null) return null;
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /** 저장값이 BCrypt가 아닐 때(평문/다른해시), checkpw가 터지지 않도록 안전 비교 */
    public static boolean check(String plainPassword, String stored) {
        if (plainPassword == null || stored == null) return false;
        stored = stored.trim(); // CHAR 패딩/개행 방지

        boolean looksBcrypt =
                stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");

        if (looksBcrypt) {
            return BCrypt.checkpw(plainPassword, stored);
        } else {
            // 레거시(평문 저장) 대비: 평문 비교 허용
            return plainPassword.equals(stored);
        }
    }
}
