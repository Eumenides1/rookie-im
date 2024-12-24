package com.rookie.stack.im.common.aspect;

import com.rookie.stack.im.common.annotations.RateLimiter;
import com.rookie.stack.im.common.annotations.RateLimiters;
import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.CommonErrorEnum;
import com.rookie.stack.im.common.utils.IpUtil;
import com.rookie.stack.im.common.utils.RedisUtil;
import com.rookie.stack.im.domain.dto.req.platform.GetVerificationCodeReq;
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

            // 动态生成限流 key
            String key = generateKey(type, joinPoint);
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

    private String generateKey(String type, ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs(); // 获取方法参数
        for (Object arg : args) {
            if ("EMAIL".equals(type) && arg instanceof GetVerificationCodeReq) {
                // 如果是目标 DTO，直接获取 email 字段
                return "EMAIL:" + ((GetVerificationCodeReq) arg).getEmail();
            }
        }

        if ("IP".equals(type)) {
            return "IP:" + IpUtil.getClientIp(request);
        }

        return null;
    }
}
