package com.demo.eureka.invoker.hystrix;

import com.demo.eureka.invoker.model.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * PerosnService
 * IOC讲hystrix放到springcloud里面
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
@Component
public class PersonService {



    @Autowired
    private RestTemplate restTemplate;
    public RestTemplate getRestTempalte() {
        return new RestTemplate();
    }

    public RestTemplate getRestTempalte2() {
        return new RestTemplate();
    }

    @HystrixCommand(fallbackMethod = "getPersonFallback")
    public Person getPerson(Integer id) {
//        使用resttemplate调用Eureka服务
        System.out.println("容错框架被调用后==>然后调用Eureka服务---personHystrix");
        Person person = restTemplate.getForObject("http://provider/personHystrix/{id}", Person.class, id);
        person.setId(id);
        System.out.println("----------------------正常响应服务-----数据:"+person);
        return person;
    }


    /**
     * 回退方法getPersonFallback
     */
    public Person getPersonFallback(Integer id) {
        System.out.println("----------------------进入容错预备方案------------------------比如返回个虚拟数据");
        Person p = new Person();
        p.setId(0);
        p.setName("我是Hystrix");
        p.setAge(-1);
        p.setUrl("request error");
        return p;
    }

}
