package com.wt.auth.server.utils;

import com.nimbusds.jose.JOSEException;
import com.wt.auth.server.entity.OauthClientExt;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.*;

/**
 * @author WTar
 * @date 2020/3/19 16:19
 */
@Slf4j
public class Analysis {

    public static List<OauthClientExt> analysisActivationCode(String code, String key){
        // log.info("------------>激活码: " + code);
        List<OauthClientExt> list = new ArrayList<>();
        if (code == null || "".equals(code) || key == null || "".equals(key)) {
            return list;
        }
        // 拿到激活码进行解密
        Map<String,Object> acti = new HashMap<>();
        try {
            acti = ActivationUtils.getActivation(code,key);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        if (acti == null) {
            return list;
        }
        for (Map.Entry<String, Object> entry : acti.entrySet()) {
            Map<String,Object> val = (Map<String, Object>) entry.getValue();
            if (val.get("secret") == null || "".equals(val.get("secret").toString())) {
                continue;
            }else if (val.get("time") == null || Long.valueOf(val.get("time").toString()) <= System.currentTimeMillis()) {
                continue;
            }
            OauthClientExt oauthClient = new OauthClientExt();
            oauthClient.setClientId(entry.getKey());
            oauthClient.setClientSecret(val.get("secret").toString());
            // 授权类型
            Set<String> authorizedGrantTypes = new TreeSet<String>();
            List<String> types = (List<String>) val.get("grantTypes");
            for (String t : types) {
                authorizedGrantTypes.add(t);
            }
            oauthClient.setAuthorizedGrantTypes(authorizedGrantTypes);
            Set<String> scope = new TreeSet<String>();
            scope.add(val.get("scope").toString());
            oauthClient.setScope(scope);
            oauthClient.setAutoApproveScopes( new TreeSet<String>(){{
                add("true");
            }});

            // 添加权限
            list.add(oauthClient);
        }
        return list;
    }
}
