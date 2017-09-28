package com.tp.auth.util;

/**
 * Created by 22670 on 2017/9/20.
 */

public class Response<T> {

    private boolean status;

    private String description;

    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
