package com.rookie.stack.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname PlatformApplication
 * @Description TODO
 * @Date 2024/12/25 16:20
 * @Created by liujiapeng
 */
@SpringBootApplication
@MapperScan(basePackages = "com.rookie.stack.platform.mapper")
public class PlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}