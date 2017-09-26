package com.tp.auth.util;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 * Created by tupeng on 2017/7/17.
 */
public class SecurityUtil {

    /**
     * 盐值加密
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPwd(String password, ByteSource salt) {
        return new Sha256Hash(password, salt).toString();
    }

    /**
     * 生成随机字符串
     * @return
     */
    public static String randomString(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}
