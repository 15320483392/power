package com.wt.common.context;

/**
 * @author WTar
 * @date 2020/1/21 14:01
 */
public class LoginContext {


    //手机验证
    public static final String PHONE_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9])|(19[013678]))\\d{8}$";

    public static final String EMAIL_REGEX = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    //忘记密码
    public static final Integer FORGETPWD = 4;

    //登录
    public static final Integer LOGIN_CODE = 5;

    //登录方式 密码登录
    public static final String LOGIN_PWD = "pwd";

    // 登录方式 验证码登录
    public static final String LOGIN_VCODE = "vcode";

    //pc端登录
    public static final String LOGIN_CLIENT_PC = "pc";

    //异动端登录
    public static final String LOGIN_CLIENT_APP = "app";


    /*-----------------------token相关-------------------------------*/
    // 验证码
    public static final String CODE = "sw:code:";

    // pc token存储
    public static final String TOKEN_PC = "token:sw:pc:";

    // app token存储
    public static final String TOKEN_APP = "token:sw:app:";

    // pc loginid存储
    public static final String TOKEN_PC_LOGIN_ID = "token:sw:pc:id:";

    // app loginid存储
    public static final String TOKEN_APP_LOGIN_ID = "token:sw:app:id:";

    public static final String USERSIG = "userSig:";

    public static final String RATE = "code:rate:";
}
