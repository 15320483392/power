package com.wt.power.wtserver.controller;

import com.wt.common.http.RestResponse;
import com.wt.common.jwt.LoginInfoRest;
import com.wt.power.wtserver.api.context.GitHubConText;
import com.wt.power.wtserver.api.enums.LoginVoucherEnum;
import com.wt.power.wtserver.entity.LoginInfo;
import com.wt.power.wtserver.entity.LoginList;
import com.wt.power.wtserver.server.ILoginService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WTar
 * @date 2020/9/3 9:15
 */
@Slf4j
@RestController
@RequestMapping("/sign")
@Api(value = "LoginController", tags = {"登录"})
public class LoginController {

    @Autowired
    private ILoginService iLoginService;

    /**
     * 使用本系统登录
     * @author wangtao
     * @date 2020/9/4 17:06
     * @param  * @param info
     * @return com.wt.common.http.RestResponse<com.wt.common.jwt.LoginInfoRest>
     */
    @PostMapping("/in")
    public RestResponse<LoginInfoRest> login(@RequestBody LoginInfo info) {
        info.vsParam();
        return new RestResponse<LoginInfoRest>().SUCCESS(iLoginService.login(info));
    }

    /**
     * 授权码模式回调
     * @author wangtao
     * @date 2020/9/5 17:36
     * @param  * @param code
     * @return com.wt.common.http.RestResponse<com.wt.common.jwt.LoginInfoRest>
     */
    @GetMapping("/in/redirect")
    public RestResponse<LoginInfoRest> getLoginRedirect(@RequestParam("code") String code){
        return new RestResponse<LoginInfoRest>().SUCCESS(iLoginService.getLoginRedirect(code));
    }

    @PostMapping("/out")
    public String loginOut(){

        return "login out";
    }

    /**
     * 第三方登录接口列表
     * @author wangtao
     * @date 2020/9/5 15:40
     * @param  * @param
     * @return org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/page")
    public ModelAndView getThirdPartyPage(){
        ModelAndView mav = new ModelAndView("login");
        List<LoginList> lists = new ArrayList<>();

        StringBuffer url = new StringBuffer(GitHubConText.GET_TOKEN_CODE_URL);
        url.append("?client_id=");
        url.append(LoginVoucherEnum.GIT_HUB.getClientId());
        url.append("&redirect_uri=");
        url.append("http://localhost:8080/sign/gitHub/redirect");
        lists.add(new LoginList("Login with GitHub",url.toString()));

        lists.add(new LoginList("sign in","http://localhost:8080/sign/in"));

        mav.getModel().put("list", lists);
        return mav;
    }

    /**
     * 使用github账号登录
     * @author wangtao
     * @date 2020/9/5 15:37
     * @param  * @param code
     * @return com.wt.common.http.RestResponse<com.wt.common.jwt.LoginInfoRest>
     */
    @RequestMapping("/gitHub/redirect")
    public RestResponse<LoginInfoRest> getHubSigIn(@RequestParam("code") String code){
        RestResponse<LoginInfoRest> restRestResponse = new RestResponse<>();
        restRestResponse.SUCCESS(iLoginService.getHubSigIn(code));
        return restRestResponse;
    }
}
