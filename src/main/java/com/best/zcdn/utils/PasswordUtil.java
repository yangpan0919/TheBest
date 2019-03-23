package com.best.zcdn.utils;


import org.mindrot.jbcrypt.BCrypt;

/**
 * 加密解密工具
 *
 * @author jiangbin
 */
public class PasswordUtil {
    /**
     * 加密
     *
     * @param source 明文
     * @return
     */
    public static String encode(String source) {
        if (null != source) {
            String hashed = BCrypt.hashpw(source, BCrypt.gensalt(10));
            return hashed;
        }
        return null;
    }

    /**
     * 加密校验
     *
     * @param candidate 明文
     * @param hashed    密文
     * @return
     */
    public static boolean checkPassword(String candidate, String hashed) {
        if (BCrypt.checkpw(candidate, hashed)) {
            return true;
        }
        return false;
    }


    public static void main(String args[]){
        encode("123456");
    }

}
