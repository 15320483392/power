package com.wt.power.wtserver.api.github;

import com.alibaba.fastjson.JSONObject;
import com.wt.common.http.HttpBaseResponse;
import com.wt.common.http.HttpClients;
import com.wt.common.jwt.LoginInfoRest;
import com.wt.power.wtserver.api.IThirdPartyLogin;
import com.wt.power.wtserver.api.context.GitHubConText;
import com.wt.power.wtserver.api.entity.GitHubTokenInfo;
import com.wt.power.wtserver.api.enums.LoginVoucherEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WTar
 * @date 2020/9/5 15:18
 */
@Slf4j
public class GitHubLogin implements IThirdPartyLogin {

    @Override
    public LoginInfoRest login(String code) {
        LoginInfoRest rest = new LoginInfoRest();
        Map<String,Object> map = new HashMap<>();
        map.put("client_id", LoginVoucherEnum.GIT_HUB.getClientId());
        map.put("client_secret",LoginVoucherEnum.GIT_HUB.getClientSecret());
        map.put("code", code);
        HttpHeaders headers = new HttpHeaders();
        HttpBaseResponse<JSONObject> baseResponse = null;
        try {
            baseResponse = new HttpClients<JSONObject>().sendPostAuthToken(GitHubConText.GET_TOKEN_URL,map,headers);
            if (baseResponse.getBody() != null) {
                GitHubTokenInfo gitHubTokenInfo = new GitHubTokenInfo();
                gitHubTokenInfo.toJSONObject(baseResponse.getBody());
                getGitHubUser(gitHubTokenInfo.getAccessToken(),rest);
                rest.setScope(gitHubTokenInfo.getScope());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSONObject.toJSONString(baseResponse));
        return rest;
    }


    /**
     * 获取用户信息
     * @author wangtao
     * @date 2020/9/5 15:44
     * @param  * @param token
     * @param rest
     * @return void
     */
    public void getGitHubUser(String token,LoginInfoRest rest){
        if (StringUtils.isEmpty(token)) {
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","token " + token);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpBaseResponse<JSONObject> baseResponse = new HttpClients<JSONObject>().sendGetRequest(GitHubConText.GET_USER_URL
                ,params
                ,headers,JSONObject.class);
        JSONObject restObj = baseResponse.getBody();
        log.info(JSONObject.toJSONString(baseResponse));
        rest.setName(restObj.get("name").toString());
        rest.setUserId(restObj.get("id").toString());
        rest.setLoginId(restObj.get("login").toString());
    }
}
