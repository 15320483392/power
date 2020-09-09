package com.wt.common.jwt;

import com.wt.common.utils.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;

/**
 * @author wangtao
 * @date 2019/12/26 15:24
 */
public class TokenUtils {

    public static JWTInfo analysisToken(String token, String key){
        // 获取JWTUtils的属性 解析token
        Claims claims = Jwts.parser()
                .setSigningKey(key.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        JWTInfo jwtInfo = new JWTInfo();
        // 赋值
        jwtInfo.setUserId(StringHelper.getObjectValue(claims.get("user_id")))
                .setRoleId(StringHelper.getIntegerValue(claims.get("role_id")))
                .setUserName(StringHelper.getObjectValue(claims.get("user_name")))
                .setName(StringHelper.getObjectValue(claims.get("name")))
                .setType(StringHelper.getIntegerValue(claims.get("type")))
                .setLoginId(StringHelper.getObjectValue(claims.get("login_id")))
                .setClient(StringHelper.getObjectValue(claims.get("client")))
                .setCompanyId(StringHelper.getObjectValue(claims.get("company_id")));
        return jwtInfo;
    }
}
