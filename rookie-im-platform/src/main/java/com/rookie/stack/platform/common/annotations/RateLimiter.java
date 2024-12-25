package com.rookie.stack.platform.common.annotations;



import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Name：RateLimiter
 * Author：eumenides
 * Created on: 2024/12/22
 * Description: 单个限流规则
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {
    String type(); // 限流类型
    int limit(); // 最大请求次数
    int duration(); // 时间窗口（秒）
}
