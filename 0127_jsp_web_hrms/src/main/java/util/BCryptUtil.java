package util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
	   public static String hash(String plainPassword) {
	        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	    }
	    // 비밀번호 비교(평문과 해시)
	    public static boolean check(String plainPassword, String hashedPassword) {
	        if (plainPassword == null || hashedPassword == null) return false;
	        return BCrypt.checkpw(plainPassword, hashedPassword);
	    }

}
