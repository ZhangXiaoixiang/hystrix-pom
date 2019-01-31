package com.demo.eureka.invoker.hystrix.cache;

import com.demo.eureka.invoker.model.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.stereotype.Component;

/**
 * CacheService
 * IOC
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
@Component
public class CacheService {
    @CacheResult
    @HystrixCommand
    public Person getPerson(Integer id) {
        System.out.println("注解实现的缓存-------getPerson()方法---------");
        Person person = new Person();
        person.setId(id);
        person.setName("我是缓存注解");
        person.setAge(18);
        person.setUrl("展示不考虑URL");
        System.out.println("缓存的person======>" + person);
        return person;
    }

    //    清除缓存

    @CacheResult()
    @HystrixCommand(commandKey = "removeKey")
    public String cacheMethod(String name) {
        return "内容是缓存的清除使用,你好: "+name;

    }
    @CacheRemove(commandKey = "removeKey")
    @HystrixCommand
    public  String updateMethod(){
        return "我是更新方法";
    }

}
