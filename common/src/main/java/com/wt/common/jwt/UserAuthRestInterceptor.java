package com.wt.common.jwt;

import com.alibaba.fastjson.JSONObject;
import com.wt.common.context.BaseContextHandler;
import com.wt.common.context.LoginContext;
import com.wt.common.context.SWContext;
import com.wt.common.http.BaseException;
import com.wt.common.http.RestResponse;
import com.wt.common.supervise.redis.RedisTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证登录用户
 * @author wangtao
 * @date 2019/12/23 19:49
 * @param  * @param null
 * @return
 */
@Slf4j
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTokenUtils redisTokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader(SWContext.TOKENHEADER);
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(SWContext.TOKENHEADER)) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        // 只有网关后的url
        // log.info(request.getRequestURI());
        // 200 成功; 400 参数异; 401 认证签名过期; 402 登录已注销; 403 无权限; 404 未知地址; 405 请求方法错误; 406 该账号在其他地方登录; 500 后台异常
        try {
            token = token.replaceAll("bearer ","");
            JWTInfo jwtInfo = TokenUtils.analysisToken(token,SWContext.TOKENKEY);
            RestResponse restul = new RestResponse();
            if (null == jwtInfo) {
                response.setStatus(401);
                restul.setStatusCode(401);
                restul.setMessage("身份认证已过期");
                response.getWriter().append(JSONObject.toJSONString(restul));
                return false;
            }
            // 判断是否被注销
            String loginId = null;
            if (LoginContext.LOGIN_CLIENT_PC.equals(jwtInfo.getClient())) {
                loginId = redisTokenUtils.getPcLoginId(jwtInfo.getUserId());
            }else if (LoginContext.LOGIN_CLIENT_APP.equals(jwtInfo.getClient())) {
                loginId = redisTokenUtils.getAppLoginId(jwtInfo.getUserId());
            }else {
                response.setStatus(402);
                restul.setStatusCode(402);
                restul.setMessage("登录已注销");
                response.getWriter().append(JSONObject.toJSONString(restul));
                return false;
            }
            if (loginId == null) {
                response.setStatus(402);
                restul.setStatusCode(402);
                restul.setMessage("登录已注销");
                response.getWriter().append(JSONObject.toJSONString(restul));
                return false;
            }
            // 是否已在其他地方登录
            if (!jwtInfo.getLoginId().equals(loginId)) {
                response.setStatus(406);
                restul.setStatusCode(406);
                restul.setMessage("该账号在其他地方登录");
                response.getWriter().append(JSONObject.toJSONString(restul));
                return false;
            }
            BaseContextHandler.setJWTInfo(jwtInfo);
            BaseContextHandler.set("token",token);
        }catch (ExpiredJwtException ex){
            response.setStatus(401);
            throw new BaseException("身份认证已过期!",401);
        }catch (SignatureException ex){
            throw new BaseException("用户令牌签名错误!",401);
        }catch (IllegalArgumentException ex){
            throw new BaseException("用户令牌为空!",401);
        }catch (NullPointerException ex){
            throw new BaseException("用户令牌为空!",401);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
