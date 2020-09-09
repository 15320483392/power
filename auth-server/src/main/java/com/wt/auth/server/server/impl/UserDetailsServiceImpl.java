package com.wt.auth.server.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wt.common.context.AuthContext;
import com.wt.common.jwt.JWTInfo;
import com.wt.common.jwt.UserInfo;
import com.wt.common.utils.ContextHolderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义登录实现类
 * @author WTar
 * @date 2020/3/9 9:25
 */
@Slf4j
@Component(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 重写登录业务实现方法
     * @author wangtao
     * @date 2020/9/3 14:00
     * @param  * @param login
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        List<String> roles = new ArrayList<>();
        JSONObject jwtInfo = null;
        try {
            String jsonRole = URLDecoder.decode(request.getHeader("roles"),"UTF-8");
            if (!StringUtils.isEmpty(jsonRole)) {
                roles = JSONArray.parseArray(jsonRole,String.class);
            }
            String jsonObj = URLDecoder.decode(request.getHeader("jwtInfo"),"UTF-8");
            if (!StringUtils.isEmpty(jsonObj)) {
                jwtInfo = JSONObject.parseObject(jsonObj);
            }else {
                jwtInfo.put("user_name",login);
            }
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
        /*if (StringUtils.isEmpty(id) || StringUtils.isEmpty(style) || StringUtils.isEmpty(client)) {
            throw new UsernameNotFoundException("登录失败");
        }*/
/*        if (loginExt == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }*/
        UserInfo user = null;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            //添加角色权限
            authorities.add(new SimpleGrantedAuthority(role));
        }

        user = new UserInfo(login, passwordEncoder.encode(AuthContext.INIT_PASS),authorities);
        user.setJwtInfo(jwtInfo);
        return user;
    }
}
