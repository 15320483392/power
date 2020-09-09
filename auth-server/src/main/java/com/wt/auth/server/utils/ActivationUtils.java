package com.wt.auth.server.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WTar
 * @date 2020/3/24 14:01
 */
public class ActivationUtils {

    // 根据不同的公司使用不同的密文
    private static final String KEY = "genjubutongdegongsshiyongbutongdejiamiwen";

    /**
     * 生成激活码
     * @author wangtao
     * @date 2020/3/24 14:15
     * @param  * @param pram
     * @return java.lang.String
     */
    public static String getActivationStr(Map<String,Object> pram) throws JOSEException{
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(new JSONObject(pram));
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(KEY);
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     * 解析激活码
     * @author wangtao
     * @date 2020/3/24 14:14
     * @param  * @param activation
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String,Object> getActivation(String activation,String key) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(activation);
        Payload payload=jwsObject.getPayload();
        JWSVerifier jwsVerifier = new MACVerifier(key);
        Map<String, Object> resultMap = new HashMap<>();
        if (jwsObject.verify(jwsVerifier)) {
            resultMap = payload.toJSONObject();
        }
        return resultMap;
    }
}
