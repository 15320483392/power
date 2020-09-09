package com.wt.power.wtserver.interceptor;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.logout().logoutSuccessUrl("http://106.54.253.23:8762/logout");
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        //http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }
}
