package com.wt.common.auth;

import com.alibaba.fastjson.JSONObject;
import com.wt.common.http.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 异常处理
 * @author wangtao
 * @date 2020/3/10 17:59
 * @param  * @param null
 * @return
 */
@Slf4j
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

	private List<String> whiteList = new ArrayList<>();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		RestResponse restResponse = new RestResponse();
		restResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		log.info("----------------------");
		log.info(request.getRequestURI());
		//查看白名单 放行
		/*log.info(whiteList.toString());
		String thisUrl = request.getRequestURI();
		Boolean vs = true;
		for (String uri : whiteList) {
			String[] url = uri.split("/");
			String[] thisurl = thisUrl.split("/");
			for (int i = 0 ; i < url.length ; i++) {
				if (!StringUtils.isEmpty(url[i]) && url[i].equals(thisurl[i])) {
					vs = false;
				}
			}
		}*/
		//验证token
		if (authException != null) {
			if(authException.getCause() instanceof InvalidTokenException){
				restResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				restResponse.setMessage("无效Token");
				restResponse.setData(authException.getMessage());
			}else {
				restResponse.setMessage(authException.getMessage());
				restResponse.setData(authException.getMessage());
			}
		}

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		PrintWriter printWriter = response.getWriter();
		printWriter.append(JSONObject.toJSONString(restResponse));
	}

	public ResourceAuthExceptionEntryPoint setWhiteList(List<String> whiteList) {
		this.whiteList = whiteList;
		return this;
	}
}
