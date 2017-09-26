package com.tp.api.enums;

public enum VerifyType {
    REGISTRATION("注册");

    private String remark;

    VerifyType(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
