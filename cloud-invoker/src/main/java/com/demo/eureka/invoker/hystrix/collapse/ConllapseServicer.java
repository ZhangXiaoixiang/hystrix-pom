package com.demo.eureka.invoker.hystrix.collapse;

import com.demo.eureka.invoker.model.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * ConllapseServicer
 * 注解实现请求合并
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
@Component
public class ConllapseServicer {
    @HystrixCollapser(batchMethod = "getPersons", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")})
    public Future<Person> getSinglePerson(Integer id) {
        System.out.println("执行单个方法---");
        return null;
    }

    @HystrixCommand
    public List<Person> getPersons(List<Integer> ids) {
        System.out.println("合并执行,参数长度为:" + ids.size());
        List<Person> ps = new ArrayList<>();
        for (Integer id : ids) {
            Person p = new Person();
            p.setId(id);
            p.setName("批处理的名字");
            p.setUrl("url");
            p.setAge(12);
            ps.add(p);
        }
        return ps;

    }

}
