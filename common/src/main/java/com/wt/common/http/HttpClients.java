package com.wt.common.http;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wangtao
 * @date 2019/10/20 19:05
 */
@Slf4j
public class HttpClients<T> {

    /**
     * 发送get请求
     * @author wangtao
     * @date 2020/1/1 14:47
     * @param  * @param url
     * @param headers
     * @return com.somorn.ulp.common.http.HttpBaseResponse
     */
    public HttpBaseResponse<T> sendGetRequest(String url, MultiValueMap<String, String> params, HttpHeaders headers,Class<T> objClass){
        RestTemplate client = new RestTemplate();
        HttpMethod method = HttpMethod.GET;
        HttpBaseResponse<T> backResponse = new HttpBaseResponse<>();
        try {
            // 以表单的方式提交
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //将请求头部和参数合成一个请求
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            //执行HTTP请求，将返回的结构使用String 类格式化
            ResponseEntity<T> response = client.exchange(url, HttpMethod.GET, requestEntity, objClass);
            backResponse.setBody(response.getBody()).setRel(true).setMsg("请求成功");
        } catch (Exception e) {
            log.error("调用异常URL: "+url + " 异常信息: " + e.toString());
            //e.printStackTrace();
            backResponse.setRel(false);
            backResponse.setMsg("请求：" + url + " 失败! " + e.toString()).setStatus(500);
        }

        return backResponse.setTimes(new Date());
    }

    /**
     * 向权鉴中心发起
     * @author wangtao
     * @date 2020/1/1 14:47
     * @param  * @param url
     * @param params
     * @param headers
     * @return com.somorn.ulp.common.http.HttpBaseResponse
     */
    public HttpBaseResponse<JSONObject> sendPostAuthToken(String url, Map<String,Object> params, HttpHeaders headers){
        StringBuffer par = new StringBuffer("?");
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();  //获取key
            String value = params.get(key).toString(); //获取value
            par.append(key+"="+value + "&");
        }
        String uri = url + par.toString().substring(0,par.toString().length() - 1);
        return sendPost(uri,new HashMap<String,Object>(),headers);
    }

    public void sendDelete(String url){
        RestTemplate httpClient = new RestTemplate();
        httpClient.delete(url);
    }

    /**
     * 发送post请求
     * @author wangtao
     * @date 2020/1/1 14:48
     * @param  * @param url
     * @param params
     * @param headers
     * @return com.somorn.ulp.common.http.HttpBaseResponse
     */
    public HttpBaseResponse<JSONObject> sendPost (String url, Map<String,Object> params, HttpHeaders headers) {
        RestTemplate httpClient = new RestTemplate();
        //新建Http头，add方法可以添加参数
        //设置请求发送方式
        HttpBaseResponse<JSONObject> backResponse = new HttpBaseResponse<>();
        try {
            //设置请求媒体数据类型
            headers.setContentType(MediaType.APPLICATION_JSON);
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //将请求头部和参数合成一个请求
            //HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(query), headers);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            //执行HTTP请求
            ResponseEntity<JSONObject> response = httpClient.postForEntity(url, requestEntity, JSONObject.class);
            backResponse.setBody(response.getBody()).setRel(true).setMsg("请求成功");
        } catch (Exception e) {
            //e.printStackTrace();
            backResponse.setRel(false);
            backResponse.setMsg("请求：" + url + " 失败! " + e.toString()).setStatus(500);
            log.error(e.toString());
        }
        return backResponse;
    }
}
