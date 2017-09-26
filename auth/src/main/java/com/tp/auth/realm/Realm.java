package com.tp.auth.realm;

import com.tp.auth.cache.AuthCache;
import com.tp.auth.cache.TokenThreadLocal;
import com.tp.auth.matcher.AuthPwdMatcher;
import com.tp.auth.model.LoginSuccessModel;
import com.tp.auth.model.User;
import com.tp.auth.service.AuthMessageService;
import com.tp.auth.token.Token;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

/**
 * Created by tupeng on 2017/7/16.
 */
public class Realm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(Realm.class);

    protected AuthMessageService authMessageService;

    public Realm(AuthMessageService authMessageService) {
        super.setCredentialsMatcher(new AuthPwdMatcher());
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
        String principal = (String) principals.getPrimaryPrincipal();
        LOGGER.warn("获取到的principal的值为：{}", principal);
        if (StringUtils.isEmpty(principal)) {
            return new SimpleAuthorizationInfo();
        }
        Set<String> roles = authMessageService.getRole(principal);
        return new SimpleAuthorizationInfo(roles);
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (null == token) {
            return null;
        }
        LOGGER.info("执行登录操作，用户名:{}，密码:{}", ((Token) token).getUsername(), ((Token) token).getPassword());
        User user = authMessageService.getUser(((Token) token).getUsername());
        if (null == user) {
            LOGGER.error("登录失败，用户不存在!!");
            throw new UnknownAccountException("用户不存在");
        }
        String accessToken = TokenThreadLocal.get();
        AuthCache.put(accessToken, (Token) token);
        authMessageService.saveUserMessage(new LoginSuccessModel(new Date(), ((Token) token).getHost()));
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), new SimpleByteSource(user.getSalt()), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Token;
    }

    public void setAuthMessageService(AuthMessageService authMessageService) {
        this.authMessageService = authMessageService;
    }


}
