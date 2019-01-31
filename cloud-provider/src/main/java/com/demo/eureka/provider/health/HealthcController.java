package com.demo.eureka.provider.health;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthApi
 * 提供一个健康服务监测功能的接口(模拟)
 *
 * @author 10905 2019/1/28
 * @version 1.0
 */
@RestController
public class HealthcController {
    /**
     * 模拟数据库连接状态
     */
//   static   Boolean canVisitDB = false;

   static   Boolean canVisitDB = true;//不是测试
    @RequestMapping(value = "/db/{canVisitDB}",method = RequestMethod.GET)
    public String setConnectionState(@PathVariable("canVisitDB") Boolean canVisitDB){
        HealthcController.canVisitDB=canVisitDB;
        System.out.println("修改模拟状态服务接口: localhost:8080/db/true布尔值");
        return "当前数据库连接状态: "+ HealthcController.canVisitDB;
    }

}
