package com.demo.eureka.provider.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * MyHealthIndicator
 * 自定义服务提供者注入健康监测
 *
 * @author 10905 2019/1/28
 * @version 1.0
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        System.out.println("自定义服务提供者注入健康监测");
        if (HealthcController.canVisitDB) {
            System.out.println("数据库连接成功返回------UP");
            return new Health.Builder(Status.UP).build();

        }else {
            System.out.println("数据库连接成功返回------DOWN");
            return new Health.Builder(Status.DOWN).build();
        }

    }
}
