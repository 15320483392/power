package com.wt.power.wtserver.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

/**
 * 登录参数
 * @author WTar
 * @date 2020/9/4 16:51
 */
@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value="LoginInfo", description="登录参数")
public class LoginInfo {

    @ApiModelProperty(value = "账号")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String passWord;

    public void vsParam(){
        Assert.hasText(userName,"请输入账号");
        Assert.hasText(passWord,"请输入密码");
    }
}
