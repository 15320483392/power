package com.wt.common.supervise.exception;

import com.wt.common.http.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author WTar
 * @date 2020/9/4 15:47
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionProcessor {

    // 404
/*    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        String errorMsg = "无法找到资源:" + e.getRequestURL();
        return new RestResponse().setMessage(errorMsg).setStatusCode(HttpStatus.NOT_FOUND.value());
    }*/

    @ExceptionHandler(Exception.class)
    public RestResponse handleBusinessException(Exception e) {
        log.warn("服务器异常:{}", e.getMessage());
        return new RestResponse().setMessage(e.getMessage()).setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


}
