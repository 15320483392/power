package com.wt.common.supervise.auth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wt.common.context.AuthContext;
import com.wt.common.http.HttpBaseResponse;
import com.wt.common.http.HttpClients;
import com.wt.common.jwt.TokenInfo;
import com.wt.common.utils.DecodeTool;
import com.wt.common.utils.JWTUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WTar
 * @date 2020/9/3 14:16
 */
@Slf4j
@Data
@Component
public class TokenAuth<T> {

    /**
     * 授权客户端Id
     * @date 2020/9/4 16:54
     */
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    /**
     * 授权客户端秘钥
     * @date 2020/9/4 16:54
     */
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    /**
     * 获取token路由
     * @date 2020/9/4 16:54
     */
    @Value("${security.oauth2.client.access-token-uri}")
    private String url;

    @Value("${security.oauth2.client.user-authorization-uri}")
    private String authorizationUri;

    /**
     * 账号
     * @date 2020/9/4 16:54
     */
    private String userName;

    /**
     * 角色权限列表
     * @date 2020/9/4 16:54
     */
    private List<String> roles;

    /**
     * token中附加信息
     * @date 2020/9/4 16:54
     */
    private T info;

    public TokenAuth() {
        this.roles = new ArrayList<>();
    }

    public TokenInfo getToken() throws Exception {
        return getToken(this.userName,this.roles,this.info);
    }

    private HttpHeaders getThisHeader(){
        HttpHeaders headers = new HttpHeaders();
        StringBuffer str = new StringBuffer("Basic ");
        str.append(DecodeTool.base64Encode(this.clientId+":"+this.clientSecret));
        headers.set("Authorization",str.toString());
        return headers;
    }

    /**
     * 去权鉴中心获取token
     * @author wangtao
     * @date 2020/9/4 16:53
     * @param  * @param userName
     * @param roles
     * @param info
     * @return com.wt.common.jwt.TokenInfo
     */
    public TokenInfo getToken(String userName, List<String> roles,T info) {
        setUserName(userName);
        setRoles(roles);
        setInfo(info);
        vsParam();
        return sendRequest();
    }

    /**
     * 去权鉴中心获取token
     * @author wangtao
     * @date 2020/9/4 16:54
     * @param  * @param
     * @return com.wt.common.jwt.TokenInfo
     */
    public TokenInfo sendRequest() {
        TokenInfo rest = null;
        Map<String,Object> map = new HashMap<>();
        map.put("grant_type","password");
        map.put("username", this.userName);
        map.put("password", AuthContext.INIT_PASS);
        map.put("scope","server");
        HttpHeaders headers = getThisHeader();
        HttpBaseResponse<JSONObject> baseResponse = null;
        try {
            headers.set("roles", URLEncoder.encode(JSONArray.toJSONString(roles),"UTF-8"));
            headers.set("jwtInfo",URLEncoder.encode(JSONArray.toJSONString(info),"UTF-8"));
            baseResponse = new HttpClients<JSONObject>().sendPostAuthToken(url,map,headers);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (baseResponse == null || baseResponse.getStatus() != 200) {
            return rest;
        }
        rest = new JWTUtils().tokenToLogInfo(baseResponse.getBody());
        return rest;
    }


    /**
     * 获取授权码
     * @author wangtao
     * @date 2020/9/5 16:36
     * @param  * @param redirectUri
     * @return java.lang.String
     */
    public String getTokenCode(String redirectUri){
        Map<String,Object> map = new HashMap<>();
        map.put("client_id",clientId);
        map.put("response_type", "code");
        map.put("redirect_uri", redirectUri);
        HttpHeaders headers = getThisHeader();
        // 返回登录信息
        HttpBaseResponse<JSONObject> baseResponse = new HttpClients<JSONObject>().sendPostAuthToken(authorizationUri
                ,map
                ,headers);
        log.info("--------------------->");
        log.info(JSONObject.toJSONString(baseResponse));
        return null;
    }

    /**
     * 基本参数验证
     * @author wangtao
     * @date 2020/9/4 16:54
     * @param  * @param
     * @return void
     */
    public void vsParam(){
        Assert.hasText(url,"请设置权鉴中心地址");
        Assert.hasText(clientId,"请设置clientId");
        Assert.hasText(clientSecret,"请设置clientSecret");
        Assert.hasText(userName,"请设置账号");
    }
}
