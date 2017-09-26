package com.tp.api.service;

import com.tp.api.bean.User;
import com.tp.api.common.exception.BaseException;
import com.tp.api.controller.user.req.InitReq;
import com.tp.api.controller.user.res.UserInfoRes;
import com.tp.api.enums.VerifyType;
import com.tp.api.mapper.UserMapper;
import com.tp.auth.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean isUsed(String phone) {
        return null == userMapper.getByPhone(phone) ? false : true;
    }

    public String register(String phone, String verifyCode) {
        if (isUsed(phone)) {
            throw new BaseException("手机号码已经被使用");
        }
        if (!verifyCodeIsOk(verifyCode)) {
            throw new BaseException("手机验证码不正确");
        }
        User user = new User(phone);
        userMapper.insert(user);
        return user.getId();
    }

    public Boolean verifyCodeIsOk(String verifyCode) {
        String serverCode = "666666";
        return serverCode.equals(verifyCode);
    }

    public UserInfoRes init(InitReq req) {
        User user = userMapper.selectByPrimaryKey(req.getId());
        if (null == user) {
            throw new BaseException("用户信息不存在");
        }
        String phone = UserUtil.getCurrentUserName();
        if (!phone.equals(user.getPhone())) {
            throw new BaseException("非法操作");
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
