package com.wt.power.wtserver.api;

import com.wt.common.jwt.LoginInfoRest;

/**
 * @author WTar
 * @date 2020/9/5 15:18
 */
public interface IThirdPartyLogin {

    LoginInfoRest login (String code);
}
