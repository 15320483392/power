package com.wt.power.wtserver.interceptor;

import com.wt.common.jwt.UserAuthRestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author wangtao
 * @date 2019/12/29 21:24
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserAuthRestInterceptor())
                .addPathPatterns(getIncludePathPatterns());
    }

    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    private ArrayList<String> getIncludePathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                "/role/**",
                "/account/**",
                "/company/**"
        };
        Collections.addAll(list, urls);
        return list;
    }
}
