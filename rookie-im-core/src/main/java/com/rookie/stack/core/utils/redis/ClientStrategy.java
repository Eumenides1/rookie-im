package com.rookie.stack.core.utils.redis;

import com.rookie.stack.core.config.BootStrapConfig;
import org.redisson.api.RedissonClient;

/**
 * @Classname ClientStrategy
 * @Description TODO
 * @Date 2025/1/26 13:41
 * @Created by liujiapeng
 */
public interface ClientStrategy {
    RedissonClient getRedissonClient(BootStrapConfig.RedisConfig config);
}
