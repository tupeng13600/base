package com.tp.api.common.auth;

import com.tp.api.bean.User;
import com.tp.api.common.exception.BaseException;
import com.tp.api.service.UserService;
import com.tp.auth.service.AuthMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/18.
 */
public class AuthService implements AuthMessageService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    private String getVerifyCode(String phone) {
        // TODO: 2017/9/28 待实现具体获取验证码的流程
        return "666666";
    }

    @Override
    public void saveUserMessage(String token, String imei, String phone, Date loginTime) {

    }

    @Override
    public Boolean verifyIsTrue(String phone, String verifyCode, String imei) {
        User user = userService.getByPhone(phone);
        if (!getVerifyCode(phone).equals(verifyCode)) {
            logger.error("登录失败，验证码不正确。手机号：{}，验证码：{} ", phone, verifyCode);
            throw new BaseException("验证码不正确");
        }
        if (null == user) {
            userService.register(phone, verifyCode, imei);
        }
        logger.info("登录成功。手机号：{}，验证码：{}", phone, verifyCode);
        return true;
    }


}
