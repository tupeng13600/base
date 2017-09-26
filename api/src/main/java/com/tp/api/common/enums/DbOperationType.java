package com.tp.api.common.enums;

/**
 * Created by tupeng on 2017/7/22.
 */
public enum DbOperationType {
    INSERT("新增"),
    SELECT("查询"),
    DELETE("删除"),
    UPDATE("更新");

    private String name;

    DbOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
