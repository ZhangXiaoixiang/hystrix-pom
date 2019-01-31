package com.demo.eureka.invoker.ribbon;


import org.aopalliance.intercept.Interceptor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * MyInterceptor
 * 自定义拦截器,使其具有负载均衡功能
 *
 * @author 10905 2019/1/29
 * @version 1.0
 */
public class MyInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        System.out.println("------------------------------自定义拦截器,使其具有负载均衡功能-----------------------------------------");
        System.out.println("原来的URI: " + request.getURI());
        MyHttpRequest newHttpRequest = new MyHttpRequest(request);

        System.out.println("更换后的URI: "+request.getURI());

        return execution.execute(newHttpRequest,body);
    }
}
