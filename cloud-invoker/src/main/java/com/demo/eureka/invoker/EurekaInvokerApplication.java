package com.demo.eureka.invoker;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@ServletComponentScan
public class EurekaInvokerApplication {
    /**
     * 监控台需要,url和以前版本不一样
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet(){

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);

        registrationBean.setLoadOnStartup(1);

        registrationBean.addUrlMappings("/actuator/hystrix.stream");

        registrationBean.setName("HystrixMetricsStreamServlet");


        return registrationBean;
    }


    public static void main(String[] args) {
        SpringApplication.run(EurekaInvokerApplication.class, args);
        System.out.println("-----------------------自定义负载均衡70%----------------------------@EnableDiscoveryClient");
        System.out.println("服务调用者: http://localhost:9000/router");
        System.out.println("使用spring封装LoadBalancerClient: http://localhost:9000/userLb");
        System.out.println("使用原生ribbon: http://localhost:9000/defaultValue");
        System.out.println("或者自定义拦截会重定向的: http://localhost:9000/lanJie");

        System.out.println("feign客户端的调用: http://localhost:9000/invokerHello");
        System.out.println("feign客户端的调用,看端口号是否满足负载均衡: http://localhost:9000/invokerPerson");
        System.out.println("Hystrix容错请求: http://localhost:9000/routerHystrix/661");
        System.out.println("Hystrix容错请求,缓存,使用注解实现: http://localhost:9000/cachePerson/661");
        System.out.println("Hystrix缓存和清理: http://localhost:9000/cacheMethod");
        System.out.println("Hystrix缓存和清理: http://localhost:9000/update");
        System.out.println("Hystrix请求合并,批处理方式: http://localhost:9000/collapse");
        System.out.println("Hystrix整合feign(在测试的main方法去运行吧): http://localhost:9000/feign/hello");
        System.out.println("Hystrix监控面板: http://localhost:9000/actuator/hystrix.stream");


    }

}

