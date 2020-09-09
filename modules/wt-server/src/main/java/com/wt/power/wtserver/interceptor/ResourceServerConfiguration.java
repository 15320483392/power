package com.wt.power.wtserver.interceptor;

import com.wt.common.auth.CustomAccessDeniedHandler;
import com.wt.common.auth.ResourceAuthExceptionEntryPoint;
import com.wt.common.jwt.UserAuthRestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author wangtao
 * @date 2019/12/29 16:24
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new ResourceAuthExceptionEntryPoint());
        resources.accessDeniedHandler(new CustomAccessDeniedHandler());
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/role/**","/company/**","/account/**").hasRole("ADMIN")
            .antMatchers("/logout/**").access("hasRole('ADMIN') or hasRole('USER')")
            .anyRequest().permitAll();
    }

    @Bean
    ResourceAuthExceptionEntryPoint getResourceAuthExceptionEntryPoint() {
        return new ResourceAuthExceptionEntryPoint();
    }

    @Bean
    UserAuthRestInterceptor getPermissionInterceptor() {
        return new UserAuthRestInterceptor();
    }
}
