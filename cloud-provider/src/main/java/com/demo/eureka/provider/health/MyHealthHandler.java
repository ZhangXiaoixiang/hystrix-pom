package com.demo.eureka.provider.health;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * MyHealthHandler
 *
 * @author 10905 2019/1/28
 * @version 1.0
 */
@Component
public class MyHealthHandler implements HealthCheckHandler {
    @Autowired
    private MyHealthIndicator myHealthIndicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
        Status status = myHealthIndicator.health().getStatus();
        if (status.equals(Status.UP)){
            System.out.println("连接数据库处理器最终判断为连接成功状态,并且发给服务器知晓");
            return InstanceInfo.InstanceStatus.UP;
        }else {
            System.out.println("连接数据库处理器最终判断为连接失败  XXX  状态,并且发给服务器知晓");
            return InstanceInfo.InstanceStatus.DOWN;
        }

    }
}
