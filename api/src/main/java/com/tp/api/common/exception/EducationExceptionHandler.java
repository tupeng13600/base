package com.tp.api.common.exception;

import com.tp.api.common.handler.RespModel;
import com.tp.api.common.utils.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by tupeng on 2017/7/18.
 */
@ControllerAdvice
public class EducationExceptionHandler {

    private Logger logger = LogManager.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespModel all(Exception e) {
        logger.error("Exception:", e);
        return new RespModel(false).setData("系统错误，请联系相关管理人员");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespModel requestTypeError(HttpRequestMethodNotSupportedException e) {
        logger.error("Exception:", e);
        return new RespModel(false).setData("请求方式错误");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespModel illegalArgument(IllegalArgumentException e) {
        logger.error("Exception:", e);
        return new RespModel(false).setData("参数错误");
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public RespModel unauthorized(UnauthorizedException e) {
        logger.error("Exception:", e);
        return new RespModel(false).setData("无权限");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public RespModel unauthenticated(UnauthenticatedException e) {
        logger.error("Exception:", e);
        return new RespModel(false).setData("未登录");
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespModel educationException(UnknownAccountException e) {
        return new RespModel(false).setData(e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespModel educationException(BaseException e) {
        return new RespModel(false).setData(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespModel methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder messageBuilder = new StringBuilder();
        if(CollectionUtils.isNotEmpty(errors)) {
            for(ObjectError error : errors) {
                String field = (String) ReflectUtil.getProperty(error, "field");
                String message = error.getDefaultMessage();
                messageBuilder.append(field).append(message).append("; ");
            }
        }
        return new RespModel(false).setData(messageBuilder.toString());
    }



}
