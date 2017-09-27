package com.tp.auth.util;

import com.tp.auth.cache.AuthCache;
import com.tp.auth.cache.TokenThreadLocal;

/**
 * Created by tupeng on 2017/7/21.
 */
public abstract class CurrentUser {

    /**
     * 获取当前用户手机号码
     *
     * @return
     */
    public static String phone() {
        String accessToken = TokenThreadLocal.get();
        return AuthCache.get(accessToken).getPhone();
    }

    /**
     * 登出
     */
    public static void logout() {
        AuthCache.remove(TokenThreadLocal.get());
    }

}
