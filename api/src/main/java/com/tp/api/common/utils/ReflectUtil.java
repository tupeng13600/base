package com.tp.api.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by tupeng on 2017/7/22.
 */
public class ReflectUtil {

    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static <T> Object getProperty(T t, String propertyName) {
        Class clss = t.getClass();
        try{
            Field field = clss.getDeclaredField(propertyName);
            field.setAccessible(true);
            return field.get(t);
        } catch (Exception e) {
            logger.warn("对象：{}， 无法获取属性：{}", t, propertyName);
        }
        return null;
    }

    public static <T> void setProperty(T t, String propertyName, Object value) {
        Class clss = t.getClass();
        try{
            Field field = clss.getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(t, value);
        } catch (Exception e) {
            logger.warn("对象：{}， 无法获取属性：{}", t, propertyName);
        }
    }

}
