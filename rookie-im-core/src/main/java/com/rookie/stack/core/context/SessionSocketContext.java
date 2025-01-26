package com.rookie.stack.core.context;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname SessionSocketContext
 * @Description TODO
 * @Date 2025/1/26 10:35
 * @Created by liujiapeng
 */
public class SessionSocketContext {
    // 改为三重Map结构：AppId → UserId → ClientType → Channel
    private static final Map<Integer, Map<String, Map<Integer, NioSocketChannel>>> CHANNEL_TRI_MAP =
            new ConcurrentHashMap<>();

    private static UserClientDto buildKey(Integer appId, String userId, Integer clientType) {
        return UserClientDto.of(appId,  clientType, userId);
    }
    public static void put(Integer appId, String userId, Integer clientType, NioSocketChannel channel) {
        CHANNEL_TRI_MAP
                // 线程安全的computeIfAbsent
                .computeIfAbsent(appId, k -> new ConcurrentHashMap<>(8))
                .computeIfAbsent(userId, k -> new ConcurrentHashMap<>(3))
                .put(clientType, channel);
    }
    public static NioSocketChannel get(Integer appId, String userId, Integer clientType) {
        Map<String, Map<Integer, NioSocketChannel>> appMap = CHANNEL_TRI_MAP.get(appId);
        if (appMap == null) return null;

        Map<Integer, NioSocketChannel> userMap = appMap.get(userId);
        return (userMap != null) ? userMap.get(clientType) : null;
    }
    public static void remove(Integer appId, String userId, Integer clientType) {
        Map<String, Map<Integer, NioSocketChannel>> appMap = CHANNEL_TRI_MAP.get(appId);
        if (appMap != null) {
            Map<Integer, NioSocketChannel> userMap = appMap.get(userId);
            if (userMap != null) {
                userMap.remove(clientType);
                // 自动清理空Map避免内存泄漏
                if (userMap.isEmpty()) {
                    appMap.remove(userId);
                    if (appMap.isEmpty()) {
                        CHANNEL_TRI_MAP.remove(appId);
                    }
                }
            }
        }
    }
    public static void removeByChannel(NioSocketChannel channel) {
        CHANNEL_TRI_MAP.forEach((appId, appMap) -> {
            appMap.forEach((userId, userMap) -> {
                userMap.entrySet().removeIf(entry -> entry.getValue() == channel);
                if (userMap.isEmpty()) {
                    appMap.remove(userId);
                }
            });
            if (appMap.isEmpty()) {
                CHANNEL_TRI_MAP.remove(appId);
            }
        });
    }


}
