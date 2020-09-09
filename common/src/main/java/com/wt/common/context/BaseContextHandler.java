package com.wt.common.context;


import com.wt.common.jwt.JWTInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangtao
 * @date 2019/12/26 17:21
 */
public class BaseContextHandler {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static JWTInfo getJWTInfo(){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
            return new JWTInfo();
        }
        return (JWTInfo) map.get("jwt");
    }

    public static void setJWTInfo(JWTInfo jwtInfo){
        if (jwtInfo == null) {
            return;
        }
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put("jwt",jwtInfo);
    }

    public static void remove(){
        threadLocal.remove();
    }
}

