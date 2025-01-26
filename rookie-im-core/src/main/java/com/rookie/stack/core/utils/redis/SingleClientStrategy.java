package com.rookie.stack.core.utils.redis;

import cn.hutool.core.util.StrUtil;
import com.rookie.stack.core.config.BootStrapConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * @Classname SingleClientStrategy
 * @Description TODO
 * @Date 2025/1/26 13:25
 * @Created by liujiapeng
 */
public class SingleClientStrategy implements ClientStrategy {
    @Override
    public RedissonClient getRedissonClient(BootStrapConfig.RedisConfig config) {
        Config redissonConfig = new Config();
        String address = config.getSingle().getAddress();
        address = address.startsWith("redis://") ? address : "redis://" + address;

        SingleServerConfig serverConfig = redissonConfig.useSingleServer()
                .setAddress(address)
                .setDatabase(config.getDatabase())
                .setTimeout(config.getTimeout())
                .setConnectionMinimumIdleSize(config.getPoolMinIdle())
                .setConnectTimeout(config.getPoolConnTimeout())
                .setConnectionPoolSize(config.getPoolSize());

        if (StrUtil.isNotBlank(config.getPassword())) {
            serverConfig.setPassword(config.getPassword());
        }

        redissonConfig.setCodec(new StringCodec());
        return Redisson.create(redissonConfig);
    }
}
