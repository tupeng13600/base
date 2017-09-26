package com.tp.api.bean;

import com.tp.api.enums.SexType;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;

    private String nick;

    private String phone;

    private String password;

    private String salt;

    private String avatar;

    private Date birthday;

    private SexType sex;

    private String address;

    private Byte level;

    private Long empiricalValue;

    private Date createTime;

    private Date updateTime;

    private Boolean init;

    private Boolean deleted;

    public User() {
    }

    public User(String phone) {
        this.phone = phone;
        this.level = 1;
        this.empiricalValue = 0L;
        this.init = false;
    }
}