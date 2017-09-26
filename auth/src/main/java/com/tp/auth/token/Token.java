package com.tp.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by tupeng on 2017/7/16.
 */
public class Token implements AuthenticationToken {

    private String host;

    private String username;

    private String password;

    public Token() {
    }

    public Token(String username, String password, String host) {
        this.username = username;
        this.password = password;
        this.host = host;
    }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
