package com.demo.eureka.invoker.ribbon;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

/**
 * MyConfig
 * 配置;类
 *
 * @author 10905 2019/1/29
 * @version 1.0
 */
public class MyConfig {
    @Bean
    public IRule getRule() {
        return new MyRule();
    }

    @Bean
    public IPing getPing() {
        return new MyPing();
    }




}
