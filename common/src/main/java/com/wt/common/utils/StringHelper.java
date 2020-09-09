package com.wt.common.utils;


public class StringHelper {

    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }

    public static Integer getIntegerValue(Object obj){
        return obj == null ? null : Integer.valueOf(obj.toString());
    }
}
