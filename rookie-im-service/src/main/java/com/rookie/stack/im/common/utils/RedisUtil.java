package com.rookie.stack.im.common.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Name：RedisUtil
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Component
public class RedisUtil {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private static final String SLIDING_WINDOW_LUA = "local key = KEYS[1]\n" +
            "local limit = tonumber(ARGV[1])\n" +
            "local now = tonumber(ARGV[2])\n" +
            "local window = tonumber(ARGV[3])\n" +
            "redis.call('ZREMRANGEBYSCORE', key, '-inf', now - window)\n" +
            "local count = redis.call('ZCARD', key)\n" +
            "if count < limit then\n" +
            "    redis.call('ZADD', key, now, now)\n" +
            "    redis.call('EXPIRE', key, window)\n" +
            "    return 1\n" +
            "else\n" +
            "    return 0\n" +
            "end";

    /**
     * 滑动窗口限流
     *
     * @param key      限流的维度
     * @param limit    最大允许次数
     * @param duration 时间窗口（秒）
     * @return 是否允许请求
     */
    public boolean isAllowed(String key, int limit, int duration) {
        long now = System.currentTimeMillis() / 1000; // 当前时间戳（秒）
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(SLIDING_WINDOW_LUA, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key),
                String.valueOf(limit), String.valueOf(now), String.valueOf(duration));
        return result == 1;
    }

    /**
     * 设置字符串键值对
     *
     * @param key   键
     * @param value 值
     * @param timeout 过期时间（秒），如果为 null 则不过期
     */
    public void set(String key, String value, Long timeout) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value);
        if (timeout != null) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取字符串值
     *
     * @param key 键
     * @return 值，如果键不存在则返回 null
     */
    public String get(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 删除键
     *
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 检查键是否存在
     *
     * @param key 键
     * @return true 存在，false 不存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 设置键的过期时间
     *
     * @param key 键
     * @param timeout 过期时间（秒）
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 增加键的值（计数器）
     *
     * @param key 键
     * @param delta 增加的值，可以为负数
     * @return 增加后的值
     */
    public Long increment(String key, long delta) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.increment(key, delta);
    }

    /**
     * 获取键的剩余过期时间
     *
     * @param key 键
     * @return 剩余时间（秒），如果为 -1 表示没有设置过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 设置哈希值
     *
     * @param key 键
     * @param hashKey 哈希键
     * @param value 值
     */
    public void hSet(String key, String hashKey, String value) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.put(key, hashKey, value);
    }

    /**
     * 获取哈希值
     *
     * @param key 键
     * @param hashKey 哈希键
     * @return 值，如果不存在返回 null
     */
    public String hGet(String key, String hashKey) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        Object value = ops.get(key, hashKey);
        return value != null ? value.toString() : null;
    }

    /**
     * 删除哈希键
     *
     * @param key 键
     * @param hashKey 哈希键
     */
    public void hDelete(String key, String hashKey) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.delete(key, hashKey);
    }

    /**
     * 原子操作 - 设置值并返回旧值
     *
     * @param key 键
     * @param value 新值
     * @return 旧值
     */
    public String getAndSet(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.getAndSet(key, value);
    }

    /**
     * 限流功能：检查某个操作是否超过限制
     *
     * @param key 键
     * @param limit 时间范围内允许的最大次数
     * @param seconds 时间范围（秒）
     * @return true 未超过限制，false 超过限制
     */
    public boolean rateLimit(String key, int limit, int seconds) {
        Long currentCount = increment(key, 1);
        if (currentCount == 1) {
            // 第一次操作，设置过期时间
            expire(key, seconds);
        }
        return currentCount <= limit;
    }
}
