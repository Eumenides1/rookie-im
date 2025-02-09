package com.rookie.stack.core.config;

import com.rookie.stack.core.utils.redis.RedisMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname BootStrapConfig
 * @Description 核心配置类
 * @Date 2025/1/24 14:25
 * @Created by liujiapeng
 */
@Data
public class BootStrapConfig {

    private TcpConfig rookie;

    @Data
    public static class TcpConfig {
        private Integer tcpPort;
        private Integer wsPort;
        private Integer workThreadSize;
        private Integer bossThreadSize;
        private Long heartBeatTime; // 心跳超时时间

        private RedisConfig redis;

        private RabbitMq rabbitmq;

        private ZkConfig zkConfig;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisConfig {
        /**
         * 单机模式：single 哨兵模式：sentinel 集群模式：cluster
         */
        private RedisMode mode;
        /**
         * 数据库
         */
        private Integer database;

        /**
         * 密码
         */
        private String password;
        /**
         * 超时时间
         */
        private Integer timeout;
        /**
         * 最小空闲数
         */
        private Integer poolMinIdle;
        /**
         * 连接超时时间(毫秒)
         */
        private Integer poolConnTimeout;
        /**
         * 连接池大小
         */
        private Integer poolSize;

        /**
         * redis单机配置
         */
        private RedisSingle single;
        // 集群配置
        private ClusterConfig cluster;
    }
    /**
     * redis单机配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisSingle {
        /**
         * 地址
         */
        private String address;
    }

    @Data
    public static class ClusterConfig {
        private String nodes; // 格式: "host1:port1,host2:port2,..."
    }

    /**
     * RabbitMQ 配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RabbitMq {
         private String host;
         private Integer port;
         private String virtualHost;
         private String username;
         private String password;
    }

    @Data
    public static class ZkConfig {
        private String zkAddr;
        private Integer zkConnectTimeout;
    }
}
