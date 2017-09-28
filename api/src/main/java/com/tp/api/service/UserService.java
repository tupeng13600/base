package com.tp.api.service;

import com.tp.api.bean.User;
import com.tp.api.common.exception.BaseException;
import com.tp.api.controller.user.req.InitReq;
import com.tp.api.controller.user.res.UserInfoRes;
import com.tp.api.enums.VerifyType;
import com.tp.api.mapper.UserMapper;
import com.tp.auth.cache.TokenThreadLocal;
import com.tp.auth.token.Token;
import com.tp.auth.util.CurrentUser;
import com.tp.auth.util.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean isUsed(String phone) {
        return null == userMapper.getByPhone(phone) ? false : true;
    }

    @Transactional
    public String register(String phone, String verifyCode, String imei) {
        if (isUsed(phone)) {
            throw new BaseException("手机号码已经被使用");
        }
        User user = new User(phone);
        userMapper.insert(user);
        SecurityUtils.getSubject().login(new Token(SecurityUtil.randomString(), imei, phone, verifyCode)); //执行登录操作并且校验验证码是否正确
        return TokenThreadLocal.get();
    }

    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    @Transactional
    public UserInfoRes init(InitReq req) {
        String phone = CurrentUser.phone();
        User user = userMapper.getByPhone(phone);
        if (null == user) {
            throw new BaseException("用户信息不存在");
        }
        if(user.getInit()) {
            throw new BaseException("用户已经进行过初始化");
        }
        BeanUtils.copyProperties(req, user);
        user.setInit(true);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
        return new UserInfoRes(user);
    }

    public void getVerifyCode(VerifyType verifyType, String phone) {
        // TODO: 2017/9/26 获取验证码逻辑待实现
    }
}
