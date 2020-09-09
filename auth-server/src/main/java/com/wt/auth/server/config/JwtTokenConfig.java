package com.wt.auth.server.config;

import com.wt.common.context.SWContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author wangtao
 * @date 2019/12/23 16:40
 */
@Configuration
public class JwtTokenConfig {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(SWContext.TOKENKEY);
        return accessTokenConverter;
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer(){
        return new JWTokenEnhancer();
    }
}
