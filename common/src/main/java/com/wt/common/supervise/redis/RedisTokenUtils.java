package com.wt.common.supervise.redis;

import com.wt.common.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author WTar
 * @date 2020/2/26 13:14
 */
@Component
public class RedisTokenUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplates;

    /**
     * 查询loginid
     * @author wangtao
     * @date 2020/2/26 15:08
     * @param  * @param userId
     * @return java.lang.String
     */
    public String getPcLoginId(String userId){
        String loginId = redisTemplates.opsForValue().get(LoginContext.TOKEN_PC_LOGIN_ID + userId);
        return loginId;
    }

    /**
     * 查询loginid
     * @author wangtao
     * @date 2020/2/26 15:08
     * @param  * @param userId
     * @return java.lang.String
     */
    public String getAppLoginId(String userId){
        String loginId = redisTemplates.opsForValue().get(LoginContext.TOKEN_APP_LOGIN_ID + userId);
        return loginId;
    }
}
