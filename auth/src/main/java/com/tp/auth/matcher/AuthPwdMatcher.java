package com.tp.auth.matcher;

import com.tp.auth.util.SecurityUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * Created by tupeng on 2017/7/17.
 */
public class AuthPwdMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo) info;
        String desPwd = SecurityUtil.encryptPwd((String) token.getCredentials(), simpleInfo.getCredentialsSalt());
        if (desPwd.equals(info.getCredentials())) {
            return true;
        }
        return false;
    }
}
