package com.demo.eureka.invoker.feignandhystrix;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HelloFeignAndHystrux
 * 整合feign
 * @author 10905 2019/1/31
 * @version 1.0
 */
@FeignClient(name = "provider", fallback = HelloClient.HelloClientFallback.class)
public interface HelloClient {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
     String hello();


    @Component
     class HelloClientFallback implements HelloClient {

        @Override
        public String hello() {
            System.out.println("hello的回退方法------和feign整合---注意yml配置---说明进入容错了");
            return "error hello";
        }
    }
}
