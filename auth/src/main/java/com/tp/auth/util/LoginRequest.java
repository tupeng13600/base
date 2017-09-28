package com.tp.auth.util;

/**
 * Created by 22670 on 2017/9/20.
 */

public class LoginRequest {

    public LoginRequest(String phone, String verifyCode) {
        this.phone = phone;
        this.verifyCode = verifyCode;
    }

    private String phone;

    private String verifyCode;

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
}
