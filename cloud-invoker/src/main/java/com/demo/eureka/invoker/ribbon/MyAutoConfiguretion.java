package com.demo.eureka.invoker.ribbon;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MyAutoConfiguretion
 * 使用自定义拦截器
 *
 * @author 10905 2019/1/29
 * @version 1.0
 */
@Configuration
public class MyAutoConfiguretion {
    @Autowired(required = false)
    @MyLoadBalanced
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Bean
    public SmartInitializingSingleton smartInitializingSingleton() {
        System.out.println("这个bean在容器初始化----------");
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (RestTemplate restTemplate : restTemplates) {
                    // 创建一个自定义的拦截器实例
                    MyInterceptor myInterceptor = new MyInterceptor();
                    // 获取RestTemplate原来的拦截器
                    List list = new ArrayList(restTemplate.getInterceptors());
                    // 添加到拦截器集合
                    list.add(myInterceptor);
                    // 将新的拦截器集合设置到RestTemplate实例
                    restTemplate.setInterceptors(list);
                }
            }
        };
    }
}
