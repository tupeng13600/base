package com.tp.api.common.auth;

import com.tp.api.bean.User;
import com.tp.api.common.exception.BaseException;
import com.tp.api.mapper.UserMapper;
import com.tp.auth.service.AuthMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/18.
 */
public class AuthService implements AuthMessageService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getVerifyCode(String phone) {
        User user = userMapper.getByPhone(phone);
        if(null == user) {
            throw new BaseException("该用户尚未注册");
        }
        return "666666";
    }

    @Override
    public void saveUserMessage(String token, String imei, String phone, Date loginTime) {

    }

}
