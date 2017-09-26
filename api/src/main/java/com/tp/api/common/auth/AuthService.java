package com.tp.api.common.auth;

import com.tp.auth.model.LoginSuccessModel;
import com.tp.auth.model.User;
import com.tp.auth.service.AuthMessageService;
import com.tp.auth.util.SecurityUtil;
import org.apache.shiro.util.SimpleByteSource;

import java.util.Set;

/**
 * Created by tupeng on 2017/7/18.
 */
public class AuthService implements AuthMessageService {

    @Override
    public User getUser(String username) {
        String password = SecurityUtil.encryptPwd("123", new SimpleByteSource("88888888"));
        return new User("tupeng", password, "88888888");
    }

    @Override
    public Set<String> getRole(String username) {
        return null;
    }

    @Override
    public void saveUserMessage(LoginSuccessModel model) {

    }
}
