package com.rookie.stack.im.common.aspect;

import com.rookie.stack.im.common.annotations.RateLimiter;
import com.rookie.stack.im.common.annotations.RateLimiters;
import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.CommonErrorEnum;
import com.rookie.stack.im.common.utils.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Name：RateLimiterAspect
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Aspect
@Component
public class RateLimiterAspect {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private HttpServletRequest request;

    @Around("@annotation(rateLimiters)")
    public Object handleRateLimiters(ProceedingJoinPoint joinPoint, RateLimiters rateLimiters) throws Throwable {
        for (RateLimiter rateLimiter : rateLimiters.value()) {
            String type = rateLimiter.type();
            int limit = rateLimiter.limit();
            int duration = rateLimiter.duration();

            // 根据限流类型构造限流 key
            String key = generateKey(type);
            if (key == null) {
                throw new BusinessException("Unsupported rate limiter type: " + type);
            }

            // 调用限流工具类
            if (!redisUtil.isAllowed(key, limit, duration)) {
                throw new BusinessException(CommonErrorEnum.LOCK_LIMIT);
            }
        }
        return joinPoint.proceed(); // 所有限流规则通过后执行方法
    }

    private String generateKey(String type) {
        return switch (type) {
            case "IP" -> "IP:" + request.getRemoteAddr();
            case "EMAIL" -> "EMAIL:" + request.getParameter("email");
            default -> null;
        };
    }
}
