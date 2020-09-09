package com.wt.common.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author WTar
 * @date 2020/9/4 16:33
 */
@Data
@Accessors(chain = true)
public class TokenInfo {

    private String accessToken;

    private String refreshToken;

    private String loginId;

    private String tokenType;

    private Integer expiresIn;

    private String scope;

    private String jti;

    private Date freshTime;
}
