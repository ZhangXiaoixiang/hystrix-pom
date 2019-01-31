package com.demo.eureka.invoker.ribbon;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * MyHttpRequest
 *
 * @author 10905 2019/1/29
 * @version 1.0
 */
public class MyHttpRequest implements HttpRequest {
    private HttpRequest sourceRequest;

    public MyHttpRequest(HttpRequest sourceRequest) {
        this.sourceRequest = sourceRequest;
    }

    @Override
    public String getMethodValue() {
        return sourceRequest.getMethod().toString();
    }

    /**
     * 这是自定义具体实现(简单修改,意思意思)
     * @return
     */
    @Override
    public URI getURI() {
        String oldURI = sourceRequest.getURI().toString();
        System.out.println(oldURI);
        try {
            /**
             * http://localhost:8080/byMe
             * 浏览器输入地址后重定向到lanJie
             */
            URI newUri = new URI("http://localhost:9000/lanJie");
            return newUri;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return sourceRequest.getURI();
    }

    @Override
    public HttpHeaders getHeaders() {
        return sourceRequest.getHeaders();
    }

}
