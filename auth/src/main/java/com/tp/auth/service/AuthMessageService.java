package com.tp.auth.service;


import java.util.Date;

/**
 * Created by tupeng on 2017/7/17.
 */
public interface AuthMessageService {

    void saveUserMessage(String token, String imei, String phone, Date loginTime);

    Boolean verifyIsTrue(String phone, String verifyCode, String imei);
}
