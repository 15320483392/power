package com.wt.auth.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 缓存激活码
 * @author WTar
 * @date 2020/3/24 15:27
 */
@Slf4j
@Component
public class RedisUtils {

    public static final String ACT_CODE = "user:act:code";

    public static final String ACT_KEY = "user:act:keys";

    @Autowired
    private RedisTemplate<String, String> redisTemplateStr;

    /**
     * 储存激活码
     * @author wangtao
     * @date 2020/3/24 15:32
     * @param  * @param act
     * @return void
     */
    public void saveActivation(String act){
        if (StringUtils.isEmpty(act)) {
            return;
        }
        redisTemplateStr.opsForValue().set(ACT_CODE, act,27, TimeUnit.HOURS);
        log.info("将激活码存入缓存");
    }

    /**
     * 取出激活码
     * @author wangtao
     * @date 2020/3/24 15:32
     * @param  * @param
     * @return java.lang.String
     */
    public String getActivation(){
        return  redisTemplateStr.opsForValue().get(ACT_CODE);
    }

    public void saveCompanyKey(String key){
        if (StringUtils.isEmpty(key)) {
            return;
        }
        redisTemplateStr.opsForValue().set(ACT_KEY, key,27, TimeUnit.HOURS);
    }

    /**
     * 查询密钥
     * @author wangtao
     * @date 2020/4/1 11:02
     * @param  * @param
     * @return java.lang.String
     */
    public String getCompanyKey () {
        return  redisTemplateStr.opsForValue().get(ACT_KEY);
    }
}
