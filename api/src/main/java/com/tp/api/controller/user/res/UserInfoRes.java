package com.tp.api.controller.user.res;

import com.tp.api.bean.User;
import com.tp.api.common.exception.BaseException;
import com.tp.api.enums.SexType;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoRes {

    private String id;

    private String nick;

    private String phone;

    private String avatar;

    private Date birthday;

    private SexType sex;

    private String address;

    private Byte level;

    private Long empiricalValue;

    private Boolean init;

    public UserInfoRes() {
    }

    public UserInfoRes(User user) {
        if(null == user) {
            throw new BaseException("用户信息不可为空");
        }
        this.id = user.getId();
        this.nick = user.getNick();
        this.phone = user.getPhone();
        this.avatar = user.getAvatar();
        this.birthday = user.getBirthday();
        this.sex = user.getSex();
        this.address = user.getAddress();
        this.level = user.getLevel();
        this.empiricalValue = user.getEmpiricalValue();
        this.init = user.getInit();
    }
}
