package com.demo.eureka.invoker.ribbon;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

/**
 * MyPing
 * 自定义ping
 * @author 10905 2019/1/29
 * @version 1.0
 */
public class MyPing implements IPing {
    @Override
    public boolean isAlive(Server server) {

        System.out.println("ribbon  自定义Ping类,服务器信息: "+server.getPort());

        return true;
    }
}
