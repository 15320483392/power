package com.wt.common.http;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 请求返回结果类
 * @author wangtao
 * @date 2019/10/2 12:29
 * @return
 */
@Data
@Accessors(chain = true)
public class HttpBaseResponse<T> {

    private Integer status;

    private String msg;

    private Boolean rel;

    private T body;

    private Date times;

    public HttpBaseResponse() {
        this.status = 200;
    }

    public HttpBaseResponse(Integer status, String msg, Boolean rel, T body) {
        this.status = status;
        this.msg = msg;
        this.rel = rel;
        this.body = body;
    }
}
