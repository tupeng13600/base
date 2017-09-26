package com.tp.auth.util;

/**
 * Created by tupeng on 2017/7/22.
 */
public class CurrentThreadLocal {

    private static ThreadLocal<String> school = new ThreadLocal();

    public static void setSchoolId(String schoolId) {
        school.set(schoolId);
    }

    public static String getSchoolId() {
        return school.get();
    }

    public static void removeSchoolId() {
        school.remove();
    }

}
