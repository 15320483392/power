package com.wt.power.wtserver.api.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WTar
 * @date 2020/9/5 14:20
 */
@Data
@Accessors(chain = true)
public class GitHubTokenInfo {

    private String accessToken;

    private String scope;

    private String tokenType;

    public Map<String,Object> toStringMap (String body){
        Map<String,Object> obj = new HashMap<>();
        String[] s = body.split("&");
        for (String a : s) {
            String[] p = a.split("=");
            if (p.length > 1) {
                obj.put(p[0],p[1]);
            }else {
                obj.put(p[0],null);
            }

        }
        return obj;
    }

    public GitHubTokenInfo toRestString (String body){
        String[] s = body.split("&");
        for (String a : s) {
            String[] p = a.split("=");
            if (p.length <= 1) {
                continue;
            }
            if ("access_token".equals(p[0])) {
                this.accessToken = p[1];
            }else if ("scope".equals(p[0])) {
                this.scope = p[1];
            }else if ("token_type".equals(p[0])) {
                this.tokenType = p[1];
            }
        }
        return this;
    }

    public GitHubTokenInfo toJSONObject(JSONObject object){
        if (object == null) {
            return null;
        }
        this.accessToken = object.get("access_token") != null ? object.get("access_token").toString() : null;
        this.scope = object.get("scope") != null ? object.get("scope").toString() : null;
        this.tokenType = object.get("token_type") != null ? object.get("token_type").toString() : null;
        return this;
    }
}
