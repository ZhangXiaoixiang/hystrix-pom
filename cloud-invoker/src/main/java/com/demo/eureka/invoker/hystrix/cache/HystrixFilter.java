package com.demo.eureka.invoker.hystrix.cache;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * HystrixFilter
 * 用过滤器实现原生框架初始上下文,并且使用注解实现缓存
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "hystrixFilter")
public class HystrixFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("-------------HystrixFilter 初始化,记得在启动类加@ServletComponentScan注解--------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--------------------HystrixFilter 过滤--------------------------");
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            context.shutdown();
        }
    }

    @Override
    public void destroy() {
        System.out.println("----------HystrixFilter 销毁-----------------");
    }
}
