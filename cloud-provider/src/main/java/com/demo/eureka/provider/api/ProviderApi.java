package com.demo.eureka.provider.api;


import com.demo.eureka.provider.model.Person;
import com.demo.eureka.provider.model.PlayerInfo;
import com.demo.eureka.provider.service.PlayerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProviderApi
 *
 * @author 10905 2019/1/26
 * @version 1.0
 */
@RestController
public class ProviderApi {
    @Autowired
    private PlayerInfoService playerInfoService;


    /**
     * 返回字符串
     *大写的Person
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String Person(@PathVariable("id") Integer id, HttpServletRequest request) {
        return "你好,你输入的id是:  " + id + "   请求的URL:" + request.getRequestURL().toString();
    }

    @RequestMapping(value = "/getPlayerInfoList/{startPage}/{pageSize}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerInfo> getPlayerInfoList(@PathVariable("startPage") Integer startPage, @PathVariable("pageSize") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("startPage", startPage);
        map.put("pageSize", pageSize);
        System.out.println("进入了提供者服务调用层===>" + playerInfoService.getPlayerInfoByCondition(map));

        return playerInfoService.getPlayerInfoByCondition(map);
    }


    //    ----------------------------------hystrix使用的以下服务--------------------------------------------------------


    /**
     * 小写的person
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/personHystrix/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person personHystrix(@PathVariable("id") Integer id, HttpServletRequest request) {
        Person person = new Person(id, "我是Hystrix的person", request.getRequestURL().toString(), 23);
        return person;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object hello() {
        System.out.println("最简单的服务被调用---------返回字符串");
        return "凑数的服务2   hello ribbon and feign and hystrix";
    }
}
