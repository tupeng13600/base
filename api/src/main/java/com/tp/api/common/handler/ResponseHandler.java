package com.tp.api.common.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by tupeng on 2017/7/18.
 */
@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    private static final String[] swaggerPath = {"swagger-resources", "/v2/api-docs"};

    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (body instanceof RespModel) {
            return body;
        }
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return body;
        }
        if (methodParameter.getMethodAnnotation(NormalResponse.class) != null) {
            return body;
        }

        if (isSwagger(serverHttpRequest)) {
            return body;
        }

        RespModel respModel = new RespModel(true);
        respModel.setData(body);
        return respModel;
    }

    private Boolean isSwagger(ServerHttpRequest request) {
        for (String path : swaggerPath) {
            if (request.getURI().getPath().contains(path)) {
                return true;
            }
        }
        return false;
    }
}
