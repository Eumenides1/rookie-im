package com.rookie.stack.core.utils.redis;

import com.rookie.stack.core.config.BootStrapConfig;
import lombok.Getter;
import org.redisson.api.RedissonClient;

/**
 * @Classname RedisManager
 * @Description TODO
 * @Date 2025/1/26 11:24
 * @Created by liujiapeng
 */
public class RedisManager {
    @Getter
    private static volatile RedissonClient redissonClient;
    private static final Object lock = new Object();
    private static BootStrapConfig config; // 保存配置对象

    // 初始化方法（必须由外部调用并传入配置）
    public static void init(BootStrapConfig config) {
        if (redissonClient == null) {
            synchronized (lock) {
                if (redissonClient == null) {
                    RedisManager.config = config; // 保存配置
                    ClientStrategy strategy = createStrategy(config);
                    redissonClient = strategy.getRedissonClient(config.getRookie().getRedis());
                }
            }
        }
    }

    // 根据配置选择策略
    private static ClientStrategy createStrategy(BootStrapConfig config) {
        if (config.getRookie().getRedis().getMode() == RedisMode.CLUSTER) {
            return new ClusterClientStrategy();
        } else {
            return new SingleClientStrategy();
        }
    }

    // 关闭客户端
    public static void shutdown() {
        if (redissonClient != null) {
            redissonClient.shutdown();
            redissonClient = null;
            config = null; // 清理配置
        }
    }
}
