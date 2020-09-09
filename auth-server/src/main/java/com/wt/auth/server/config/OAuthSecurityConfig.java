package com.wt.auth.server.config;

import com.wt.auth.server.server.impl.InMemoryClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author wangtao
 * @date 2019/12/23 16:24
 */
@Slf4j
@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService userDetailsServiceImpl;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Resource
    private DataSource dataSource;

    @Autowired
    private InMemoryClientDetailsService inMemoryClientDetailsService;

    /**
     * 配置构建token参数
     * @author wangtao
     * @date 2020/9/3 13:57
     * @param  * @param endpoints
     * @return void
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer,jwtAccessTokenConverter));
        endpoints.tokenStore(tokenStore)
                .userDetailsService(userDetailsServiceImpl)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager);

        // 配置tokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        endpoints.authorizationCodeServices(authorizationCodeServices()); //authorizationCodeService用来配置授权码的存储 这里我们是存在在内存中
        endpoints.tokenServices(tokenServices);
    }

    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 切换oauth2客户端配置 *
     * @author wangtao
     * @date 2020/9/3 13:56
     * @param  * @param clients
     * @return void
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 手动添加客户端
        clients.inMemory()
                .withClient("admin")
                .secret(passwordEncoder.encode("admin-auto-20200904"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(28800)
                .scopes("server");
        // 使用注册码中客户端
        //clients.withClientDetails(inMemoryClientDetailsService);
        //数据库中客户端
        clients.withClientDetails(clientDetails());
    }


    /**
     * 自动读取数据库配置的客户端
     * @author wangtao
     * @date 2020/9/3 13:56
     * @param  * @param
     * @return org.springframework.security.oauth2.provider.ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        // 使用数据库
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients();
        // 检查令牌访问
        security.checkTokenAccess("isAuthenticated()");
        // 令牌密钥访问
        security.tokenKeyAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();//支持把secret和clientid写在url上，否则需要在头上
    }
}
