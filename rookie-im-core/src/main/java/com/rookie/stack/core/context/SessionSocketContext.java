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
    private static final Map<String, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>();
    public static void put(String userId, NioSocketChannel channel) {
        CHANNEL_MAP.put(userId, channel);
    }
    public static NioSocketChannel get(String userId) {
        return CHANNEL_MAP.get(userId);
    }
}
