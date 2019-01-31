package com.demo.eureka.provider;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

//依赖service扫描(扫描多了就失效了,注意)
@SpringBootApplication(scanBasePackages="com"/*,exclude = DataSourceAutoConfiguration.class*/)
@EnableEurekaClient
public class EurekaProviderApplication {
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



        System.out.println("提供者发布服务: http://localhost:服务端口号(2次一致)");
        System.out.println("请输入提供者端口号:8080还是8081");
        Scanner scanner=new Scanner(System.in);
       new SpringApplicationBuilder(EurekaProviderApplication.class).properties("server.port="+scanner.next()).run(args);
        System.out.println("查看监测,如:http://localhost:8080/actuator\n或者: http://localhost:8080/actuator/health");
        System.out.println("修改模拟值(默认为false): http://localhost:8080/db/false");

        System.out.println("Hystrix服务: http://localhost:8080/personHystrix/125");
        System.out.println("Hystrix服务: http://localhost:8080/hello");

    }

}

