package com.rookie.stack.core.config;

import lombok.Data;

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
    }

}
