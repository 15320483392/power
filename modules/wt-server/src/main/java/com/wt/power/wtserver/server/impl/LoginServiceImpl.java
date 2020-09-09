package com.wt.power.wtserver.server.impl;

import com.wt.common.jwt.JWTInfo;
import com.wt.common.jwt.LoginInfoRest;
import com.wt.common.jwt.TokenInfo;
import com.wt.common.supervise.auth.TokenAuth;
import com.wt.power.wtserver.api.IThirdPartyLogin;
import com.wt.power.wtserver.api.github.GitHubLogin;
import com.wt.power.wtserver.entity.LoginInfo;
import com.wt.power.wtserver.server.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author WTar
 * @date 2020/9/4 10:01
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private TokenAuth<JWTInfo> tokenAuth;

    /**
     * 用户登录
     * @author wangtao
     * @date 2020/9/4 17:05
     * @param  * @param info
     * @return com.wt.common.jwt.LoginInfoRest
     */
    @Override
    public LoginInfoRest login(LoginInfo info) {
        LoginInfoRest loginInfoRest = new LoginInfoRest();
        // 登录业务

        //用户信息
        JWTInfo jwtInfo = new JWTInfo();
        jwtInfo.setName("管理员")
                .setRoleId(1)
                .setLoginId("AAA");
        loginInfoRest.setName("管理员")
                .setRoleId(1)
                .setLoginId("AAA");

        // 查询角色权限
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        loginInfoRest.setRoles(roles);

        // 使用授权码模式
        tokenAuth.getTokenCode("http://localhost:8080/sign/in/redirect");


        // 使用密码模式获取token
        TokenInfo tokenInfo = tokenAuth.getToken(info.getUserName(), roles,jwtInfo);
        if (StringUtils.isEmpty(tokenInfo.getAccessToken())) {
            Assert.isTrue(false,"登录失败,授权异常");
        }
        loginInfoRest.setAccessToken(tokenInfo.getAccessToken()).setRefreshToken(tokenInfo.getRefreshToken());
        loginInfoRest.setUserName(info.getUserName()).setScope(tokenInfo.getScope()).setFreshTime(new Date());
        // 缓存登录信息

        return loginInfoRest;
    }

    /**
     * 根据tokenCode获取token
     * @author wangtao
     * @date 2020/9/5 16:48
     * @param  * @param code
     * @return com.wt.common.jwt.LoginInfoRest
     */
    @Override
    public LoginInfoRest getLoginRedirect(String code) {
        // 授权码模式获取token
        // 授权模式 token生成策略需要重写User和getAuthorities()权限方法
        log.info("-------------------------code----------------------------------------");
        LoginInfoRest rest = new LoginInfoRest();
        log.info(code);

        return rest;
    }

    /**
     * 使用gitHub账号登录
     * @author wangtao
     * @date 2020/9/5 15:41
     * @param  * @param code
     * @return com.wt.common.jwt.LoginInfoRest
     */
    @Override
    public LoginInfoRest getHubSigIn(String code) {
        IThirdPartyLogin gitHub = new GitHubLogin();
        LoginInfoRest rest = gitHub.login(code);

        // 查询系统用户

        // 获取token

        return rest;
    }
}
