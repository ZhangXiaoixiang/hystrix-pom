package com.demo.eureka.invoker.ribbon;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

/**
 * MyRule
 * ribbon自定义规则
 * @author 10905 2019/1/29
 * @version 1.0
 */
public class MyRule  implements IRule {
    /**
     * 需要一个负载均衡器
     */
    ILoadBalancer iLoadBalancer;

    public MyRule() {
    }

    /**
     * 自定义规则能够接收自定义均衡器
     *
     * @param ILoadBalancer
     */
    public MyRule(ILoadBalancer ILoadBalancer) {
        this.iLoadBalancer = ILoadBalancer;
    }

    /**
     * 写一个从服务列表获取端口号的方法
     *
     * @param servers 服务列表
     * @param port    端口号
     * @return
     */
    private Server getServerByPort(List<Server> servers, int port) {
        for (Server server : servers) {
            if (server.getPort() == port) {
                return server;
            }
        }
        return null;
    }

    /**
     * 自定义规则(Object key 可以传入端口之类的参数)
     *
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        /**
         * 服务列表iLoadBalancer对象获取所有服务放进list
         */
        List<Server> servers = iLoadBalancer.getAllServers();
        /**
         * 10以内的随机数
         */
        Random random = new Random();
        int i = random.nextInt(10);
        if (i > 7) {
            /**
             * 通过自己写的方法进行返回服务
             */
            return getServerByPort(servers, 8081);
        }
        return getServerByPort(servers, 8080);
    }

    /**
     * 对对象赋值
     *
     * @param lb
     */

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        /**
         * 注意
         */
        this.iLoadBalancer = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.iLoadBalancer;
    }
}
