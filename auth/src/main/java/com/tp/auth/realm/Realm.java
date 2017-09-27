package com.tp.auth.realm;

import com.tp.auth.cache.AuthCache;
import com.tp.auth.cache.TokenThreadLocal;
import com.tp.auth.service.AuthMessageService;
import com.tp.auth.token.Token;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/16.
 */
public class Realm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(Realm.class);

    protected AuthMessageService authMessageService;

    public Realm(AuthMessageService authMessageService) {
        super.setCachingEnabled(false); //关闭缓存
        super.setAuthenticationCachingEnabled(false); //关闭认证换缓存
        if (null == authMessageService) {
            LOGGER.warn("the authMessageService is null.");
        }
        this.authMessageService = authMessageService;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (null == authenticationToken) {
            return null;
        }
        Token token = (Token) authenticationToken;
        LOGGER.info("执行登录操作，用户名:{}，密码:{}", token.getPhone(), token.getVerifyCode());
        String verifyCode = authMessageService.getVerifyCode(token.getPhone());
        if (!token.getVerifyCode().equals(verifyCode)) {
            LOGGER.error("登录失败，验证码不正确。手机号：{}，验证码：{} ", token.getPhone(), token.getVerifyCode());
            throw new UnknownAccountException("验证码不正确");
        }
        AuthCache.put(token.getToken(), token);
        TokenThreadLocal.put(token.getToken());
        authMessageService.saveUserMessage(token.getToken(), token.getImei(), token.getPhone(), new Date());
        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getVerifyCode(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Token;
    }

    public void setAuthMessageService(AuthMessageService authMessageService) {
        this.authMessageService = authMessageService;
    }

}
