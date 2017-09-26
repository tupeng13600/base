package com.tp.auth.util;

import org.apache.shiro.SecurityUtils;

/**
 * Created by tupeng on 2017/7/21.
 */
public abstract class UserUtil {

    /**
     * 获取当前用户名称
     * @return
     */
    public static String getCurrentUserName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

}
