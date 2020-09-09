package com.wt.power.wtserver.server;

import com.wt.common.jwt.LoginInfoRest;
import com.wt.power.wtserver.entity.LoginInfo;

/**
 * @author WTar
 * @date 2020/9/4 10:01
 */
public interface ILoginService {

    LoginInfoRest login(LoginInfo info);

    LoginInfoRest getLoginRedirect(String code);

    LoginInfoRest getHubSigIn(String code);
}
