package com.tp.api.common.handler;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private T value;

    public BaseResponse(T value) {
        this.value = value;
    }
}
