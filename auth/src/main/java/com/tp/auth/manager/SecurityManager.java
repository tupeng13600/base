package com.tp.auth.manager;


import com.tp.auth.realm.Realm;
import com.tp.auth.service.AuthMessageService;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * Created by tupeng on 2017/7/16.
 */
public class SecurityManager extends DefaultWebSecurityManager {



    public SecurityManager(AuthMessageService authMessageService) {
        super(new Realm(authMessageService));
    }
}
