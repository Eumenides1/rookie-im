package com.rookie.stack.im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Name：Application
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.rookie.stack.im.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
