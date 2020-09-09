package com.wt.common.supervise.config;


import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author WTar
 * @date 2019/10/11 9:14
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(1000); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(400); // 每个主机的并发
        return poolingConnectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
        clientHttpRequestFactory.setConnectTimeout(20000); // 连接超时，毫秒
        clientHttpRequestFactory.setReadTimeout(20000); // 读写超时，毫秒
        return clientHttpRequestFactory;
    }
}
