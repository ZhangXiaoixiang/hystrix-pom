package com.demo.eureka.invoker.api;

import com.demo.eureka.invoker.feignandhystrix.HelloClient;
import com.demo.eureka.invoker.feignapi.PersonClient;
import com.demo.eureka.invoker.hystrix.PersonService;
import com.demo.eureka.invoker.hystrix.cache.CacheService;
import com.demo.eureka.invoker.hystrix.collapse.ConllapseServicer;
import com.demo.eureka.invoker.model.Person;
import com.demo.eureka.invoker.ribbon.MyLoadBalanced;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * InvokerApi
 *
 * @author 10905 2019/1/26
 * @version 1.0
 */
@RestController
@Configuration
public class InvokerApi {
    @Bean
    @LoadBalanced
    @Primary//----------------------------------------hystrix自动注入是由于不知道需要主人那个,这里明确一下
    public RestTemplate getRestTempalte() {
        return new RestTemplate();
    }

    /**
     * spring 使用ribbon的API
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 服务查询
     *
     * @return
     */
    @Autowired
    private DiscoveryClient discoveryClient;
    /**
     * 使用springcloud默认的实现(原生ribbon)
     */
    @Autowired
    private SpringClientFactory factory;

    /**
     * 使用自定义的注解拦截器实现
     *
     * @return
     */
    @Bean
    @MyLoadBalanced
    public RestTemplate getMyRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 使用feign客户端
     *
     * @return
     */
    @Autowired
    private PersonClient personClient;

    @RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String router() {
        RestTemplate restTemplate = getRestTempalte();
//        服务名称调用
        System.out.println("使用了ribbon的配置文件");
        String json = restTemplate.getForObject("http://provider/person/10086", String.class);

        return json;

    }

    @RequestMapping(value = "/userLb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceInstance userLb() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
        System.out.println("ServiceInstance==============>" + serviceInstance);
        return serviceInstance;
    }

    @RequestMapping(value = "/defaultValue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String defaultValue() {
        System.out.println("==== 输出默认配置：");
        // 获取默认的配置
        ZoneAwareLoadBalancer alb = (ZoneAwareLoadBalancer) factory
                .getLoadBalancer("default");
        System.out.println("    IClientConfig: "
                + factory.getLoadBalancer("default").getClass().getName());
        System.out.println("    IRule: " + alb.getRule().getClass().getName());
        System.out.println("    IPing: " + alb.getPing().getClass().getName());
        System.out.println("    ServerList: "
                + alb.getServerListImpl().getClass().getName());
        System.out.println("    ServerListFilter: "
                + alb.getFilter().getClass().getName());
        System.out.println("    ILoadBalancer: " + alb.getClass().getName());
        System.out.println("    PingInterval: " + alb.getPingInterval());
        System.out.println("==== 输出 provider 配置：");
        // 获取 provider 的配置
        ZoneAwareLoadBalancer alb2 = (ZoneAwareLoadBalancer) factory
                .getLoadBalancer("provider");
        System.out.println("    IClientConfig: "
                + factory.getLoadBalancer("provider").getClass()
                .getName());
        System.out.println("    IRule: " + alb2.getRule().getClass().getName());
        System.out.println("    IPing: " + alb2.getPing().getClass().getName());
        System.out.println("    ServerList: "
                + alb2.getServerListImpl().getClass().getName());
        System.out.println("    ServerListFilter: "
                + alb2.getFilter().getClass().getName());
        System.out.println("    ILoadBalancer: " + alb2.getClass().getName());
        System.out.println("    PingInterval: " + alb2.getPingInterval());
        return "看idea控制台哈";
    }

    /**
     * 浏览器访问的请求,自定义拦截器
     */
    @RequestMapping(value = "/byMe", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String byMe() {
        RestTemplate restTpl = getMyRestTemplate();
        // 根据名称来调用服务，这个URI会被拦截器所置换,test任意写(反正会重定向)
        String json = restTpl.getForObject("http://test/lanJie", String.class);
        return json;
    }

    /**
     * 最终的请求都会转到这个服务
     */
    @RequestMapping(value = "/lanJie", method = RequestMethod.GET)
    @ResponseBody
    public String lanJie() {
        System.out.println("-----------调用了我,说明拦截生效了--------------哈哈");
        return "你好,我是自定义拦截器的一个简单手写---";
    }
//    -----------------------------------------------feign使用------------------------------------------------------

    @RequestMapping(value = "/invokerHello", method = RequestMethod.GET)
    public String invokerHello() {
        System.out.println("进入了调用feign的API,实际调用的是feign客户端的接口,在浏览器看结果");
        return personClient.hello();
    }

    @RequestMapping(value = "/invokerPerson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String invokerPerson() {
        System.out.println("进入了调用feign的API,实际调用的是feign客户端的接口,在浏览器看结果,json格式,id写死的");
        return personClient.personFeign(678).toString();
    }
//    -----------------------------------------------hystrix-使用-------------------------------------------

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/routerHystrix/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person routerHystrix(@PathVariable("id") Integer id) {
//        服务名称调用
        System.out.println("调用的是hystrix的容错服务类");
        Person person = personService.getPerson(id);

        return person;

    }


    /**
     * 测试注解缓存的实现情况
     */
    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/cachePerson/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person cachePerson(@PathVariable("id") Integer id) {
//        服务名称调用
        System.out.println("调用cachePerson去执行getPerson()------注解方式");
        Person person = null;
        for (int i = 0; i < 3; i++) {
            System.out.println("控制器调用服务----" + i);
            person = cacheService.getPerson(id);
        }
        return person;

    }

    /**
     * 测试缓存和清理缓存
     */
    @RequestMapping(value = "/cacheMethod", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String cacheMethod() {

        String zxx = cacheService.cacheMethod("张晓祥");
        System.out.println(zxx);
        return zxx;

    }

    /**
     * 测试缓存和清理缓存 update方法
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String update() {

        String zxx = cacheService.updateMethod();
        System.out.println(zxx);
        return zxx;
    }

    @Autowired
    private ConllapseServicer servicer;

    /**
     * 请求合并
     */
    @RequestMapping(value = "/collapse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String collapse() throws ExecutionException, InterruptedException {
//连续执行3次请求
        Future<Person> f1 = servicer.getSinglePerson(1);
        Future<Person> f2 = servicer.getSinglePerson(2);
        Future<Person> f3 = servicer.getSinglePerson(3);
        Person person = f1.get();
        Person person2 = f2.get();
        Person person3 = f3.get();
        System.out.println(person.toString());
        System.out.println(person2.toString());
        System.out.println(person3.toString());
        return "查看idea控制台";

    }
//    ----------------整合feign-----------------------

    /**
     * 编译器报错这里暂时不管
     */
    @Autowired
    HelloClient helloClient;

    @RequestMapping(value = "/feign/hello", method = RequestMethod.GET)
    public String feignHello() {
        System.out.println("API执行了-------------");
//        hello方法会超时
        String hello = helloClient.hello();
//        获取断路器
        HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory.getInstance(HystrixCommandKey.Factory.asKey("HelloClient#hello()"));
        System.out.println("断路器的状态:" + breaker.isOpen());
        return hello;
    }


}
