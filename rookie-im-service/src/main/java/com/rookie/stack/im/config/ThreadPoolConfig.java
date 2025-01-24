package com.rookie.stack.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Name：ThreadPoolConfig
 * Author：eumenides
 * Created on: 2025/1/2
 * Description:
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService globalExecutorService() {
        return new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000) // 队列长度
        );
    }
}
