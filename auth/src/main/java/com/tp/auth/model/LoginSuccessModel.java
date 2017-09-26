package com.tp.auth.model;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/21.
 */
public class LoginSuccessModel {

    private Date loginTime;

    private String loginIp;

    public LoginSuccessModel(Date loginTime) {
        this.loginTime = loginTime;
    }

    public LoginSuccessModel(Date loginTime, String loginIp) {
        this.loginTime = loginTime;
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
