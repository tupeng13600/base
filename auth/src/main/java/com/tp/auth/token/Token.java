package com.tp.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by tupeng on 2017/7/16.
 */
public class Token implements AuthenticationToken {

    private String token;

    private String imei;

    private String phone;

    private String verifyCode;

    public Token() {
    }

    public Token(String token, String imei, String phone, String verifyCode) {
        this.token = token;
        this.imei = imei;
        this.phone = phone;
        this.verifyCode = verifyCode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return getPhone();
    }

    @Override
    public Object getCredentials() {
        return getVerifyCode();
    }
}
