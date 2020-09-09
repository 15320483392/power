package com.wt.common.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * 请求结果返回类
 * @author wangtao
 * @date 2019/10/2 12:30
 * @return
 */
@Data
@Accessors(chain = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="RestResponse",description="接口返回对象")
public class RestResponse<T> {

    @ApiModelProperty(name="status",value="请求状态码")
    private Integer statusCode;

    @ApiModelProperty(name="message",value="描述")
    private  String message;

    @ApiModelProperty(name="data",value="返回数据")
    private T data;

    public RestResponse() {
        this.statusCode = 200;
    }

    public RestResponse<T> SUCCESS(){
        this.message = "SUCCESS";
        this.statusCode = HttpStatus.OK.value();
        return this;
    }

    public RestResponse<T> SUCCESS(String msg){
        this.message = msg;
        this.statusCode = HttpStatus.OK.value();
        return this;
    }

    public RestResponse<T> SUCCESS(T data){
        this.message = "SUCCESS";
        this.statusCode = HttpStatus.OK.value();
        this.data = data;
        return this;
    }

    public RestResponse<T> SUCCESS(String msg,T data){
        this.message = msg;
        this.statusCode = HttpStatus.OK.value();
        this.data = data;
        return this;
    }

    public RestResponse<T> Failure(){
        this.message = "ERROR";
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return this;
    }

    public RestResponse<T> Failure(String msg){
        this.message = msg;
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return this;
    }

    public RestResponse<T> Failure(Integer statusCode,String msg){
        this.message = msg;
        this.statusCode = statusCode;
        return this;
    }
}
