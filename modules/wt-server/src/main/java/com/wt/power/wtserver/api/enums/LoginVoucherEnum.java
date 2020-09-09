package com.wt.power.wtserver.api.enums;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author WTar
 * @date 2020/9/5 11:31
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LoginVoucherEnum {

    GIT_HUB("gitHub","c85074593c0812f1a0af","1549c52ab109a4cbb917a7fd4f61235d7baa0c42");

    private String name;

    private String clientId;

    private String clientSecret;

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 转换
     * @author wangtao
     * @date 2020/9/5 11:34
     * @param  * @param clientId
     * @return java.lang.String
     */
    public static String parse(String clientId) {
        Optional<LoginVoucherEnum> found = Arrays.stream(LoginVoucherEnum.values()).filter(c -> c.clientId.equals(clientId)).findFirst();
        if (!found.isPresent()) {
            return null;
        }
        return found.get().clientSecret;
    }
}
