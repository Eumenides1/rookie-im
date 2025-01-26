package com.rookie.stack.core.utils.redis;

import cn.hutool.core.util.StrUtil;
import com.rookie.stack.core.config.BootStrapConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname ClusterClientStrategy
 * @Description TODO
 * @Date 2025/1/26 13:48
 * @Created by liujiapeng
 */
public class ClusterClientStrategy implements ClientStrategy {
    @Override
    public RedissonClient getRedissonClient(BootStrapConfig.RedisConfig config) {
        Config redissonConfig = new Config();
        List<String> nodes = Arrays.stream(config.getCluster().getNodes().split(","))
                .map(node -> node.startsWith("redis://") ? node : "redis://" + node.trim())
                .toList();

        ClusterServersConfig clusterConfig = redissonConfig.useClusterServers()
                .addNodeAddress(nodes.toArray(new String[0]))
                .setScanInterval(2000)
                .setMasterConnectionMinimumIdleSize(config.getPoolMinIdle())
                .setSlaveConnectionMinimumIdleSize(config.getPoolMinIdle())
                .setConnectTimeout(config.getPoolConnTimeout())
                .setTimeout(config.getTimeout());

        if (StrUtil.isNotBlank(config.getPassword())) {
            clusterConfig.setPassword(config.getPassword());
        }

        redissonConfig.setCodec(new StringCodec());
        return Redisson.create(redissonConfig);
    }
}
