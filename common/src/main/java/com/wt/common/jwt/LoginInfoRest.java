package com.wt.common.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author WTar
 * @date 2020/3/11 11:36
 */
@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value="LoginInfoRest", description="登录返回值")
public class LoginInfoRest {

    @ApiModelProperty(value = "签名")
    private String accessToken;

    @ApiModelProperty(value = "刷新签名")
    private String refreshToken;

    @ApiModelProperty(value = "作用域")
    private String scope;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "登录随机id")
    private String loginId;

    @ApiModelProperty(value = "权限id")
    private Integer roleId;

    @ApiModelProperty(value = "角色列表")
    private List<String> roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "刷新时间")
    private Date freshTime;

    public LoginInfoRest initLoginInfo(JWTInfo jwtInfo){
        this.userName = jwtInfo.getUserName();
        this.userId = jwtInfo.getUserId();
        this.roleId = jwtInfo.getRoleId();
        this.name = jwtInfo.getName();
        return this;
    }
}
