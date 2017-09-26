package com.tp.api.enums;

public enum SexType {
    MALE("男"),
    FEMALE("女");
    private String remark;

    SexType(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
