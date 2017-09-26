package com.tp.auth.cache;

/**
 * Created by tupeng on 2017/8/8.
 */
public class TokenThreadLocal {

    private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal();

    public static void put(String token) {
        tokenThreadLocal.set(token);
    }

    public static String get() {
        return tokenThreadLocal.get();
    }

    public static void remove() {
        tokenThreadLocal.remove();
    }

}
