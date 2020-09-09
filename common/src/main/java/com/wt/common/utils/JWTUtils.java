package com.wt.common.utils;

import com.wt.common.jwt.JWTInfo;
import com.wt.common.jwt.TokenInfo;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WTar
 * @date 2020/3/12 15:58
 */
public class JWTUtils {

    private final String USER_NAME = "user_name";
    private final String USER_ID = "user_id";
    private final String NAME = "name";
    private final String ROLE_ID = "role_id";
    private final String LOGIN_ID = "loginId";
    private final String CLIENT = "client";
    private final String TYPE = "type";
    private final String COMPANY_ID = "company_id";

    private final String TOKEN_TYPE = "token_type";
    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";

    public Map<String, Object> jwtToMap (JWTInfo jwtInf) {
        Map<String,Object> info = new HashMap<>();
        if (!StringUtils.isEmpty(jwtInf.getUserName())) {
            info.put(this.USER_NAME, jwtInf.getUserName());
        }
        if (!StringUtils.isEmpty(jwtInf.getUserId())) {
            info.put(this.USER_ID,jwtInf.getUserId());
        }
        if (!StringUtils.isEmpty(jwtInf.getName())) {
            info.put(this.NAME,jwtInf.getName());
        }
        info.put(this.ROLE_ID,jwtInf.getRoleId());
        info.put(this.LOGIN_ID,jwtInf.getLoginId());
        info.put(this.CLIENT,jwtInf.getClient());
        info.put(this.TYPE,jwtInf.getType());
        info.put(this.COMPANY_ID,jwtInf.getCompanyId());
        return info;
    }

    public JWTInfo mapToJwt(Map<String, Object> map){
        JWTInfo jwtInfo = new JWTInfo();
        return jwtInfo;
    }

    /**
     * 获取token对象
     * @author wangtao
     * @date 2020/3/26 9:48
     * @param  * @param body
     * @return com.somorn.hgsw.domain.entity.auth.vo.LoginInfoVO
     */
    public TokenInfo tokenToLogInfo(Map<String,Object> body){
        TokenInfo tokenInfo = new TokenInfo();
//        System.out.println("---------------------------");
//        System.out.println(JSONObject.toJSONString(body));

        tokenInfo.setAccessToken(body.get(this.TOKEN_TYPE).toString()+ " " +body.get(this.ACCESS_TOKEN).toString())
                .setScope(body.get("scope").toString())
                .setRefreshToken(body.get(this.REFRESH_TOKEN).toString())
                .setFreshTime(new Date());

        return tokenInfo;
    }
}
