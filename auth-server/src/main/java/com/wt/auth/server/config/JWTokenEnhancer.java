package com.wt.auth.server.config;

import com.alibaba.fastjson.JSONObject;
import com.wt.common.jwt.JWTInfo;
import com.wt.common.jwt.UserInfo;
import com.wt.common.utils.JWTUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 拦截token开始解析
 * @author wangtao
 * @date 2019/12/23 16:46
 */
public class JWTokenEnhancer implements TokenEnhancer {

    /**
     * 为token返回的bodey添加属性
     * @author wangtao
     * @date 2020/3/26 9:52
     * @param  * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        UserInfo user = (UserInfo) oAuth2Authentication.getPrincipal();
        JSONObject jwtInf = user.getJwtInfo();
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(jwtInf);
        return oAuth2AccessToken;
    }
}
