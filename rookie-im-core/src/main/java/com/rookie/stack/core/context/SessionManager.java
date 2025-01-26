package com.rookie.stack.core.context;

import com.alibaba.fastjson.JSON;
import com.rookie.stack.core.codec.pack.LoginPack;
import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.codec.proto.MessageHeader;
import com.rookie.stack.core.constants.Constants;
import com.rookie.stack.core.utils.redis.RedisManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;

/**
 * @Classname SessionManager
 * @Description Session管理工具类
 * @Date 2025/1/26 15:31
 * @Created by liujiapeng
 */
public class SessionManager {
    private static final AttributeKey<String> USER_ID = AttributeKey.valueOf(Constants.UserId);
    private static final AttributeKey<Integer> APP_ID = AttributeKey.valueOf(Constants.AppId);
    private static final AttributeKey<Integer> CLIENT_TYPE = AttributeKey.valueOf(Constants.ClientType);

    public static void saveSession(ChannelHandlerContext ctx, Message message, LoginPack pack) {
        setChannelAttributes(ctx, pack.getUserId(), message.getHeader());
        persistToRedis(message.getHeader(), pack);
        registerLocalSession(ctx, message.getHeader(), pack);
    }

    private static void setChannelAttributes(ChannelHandlerContext ctx, String userId, MessageHeader header) {
        ctx.channel().attr(USER_ID).set(userId);
        ctx.channel().attr(APP_ID).set(header.getAppId());
        ctx.channel().attr(CLIENT_TYPE).set(header.getClientType());
    }

    private static void persistToRedis(MessageHeader header, LoginPack pack) {
        UserSession session = UserSession.fromHeaderAndPack(header, pack);
        RMap<String, String> sessionMap = getSessionMap(header.getAppId(), pack.getUserId());
        sessionMap.put(header.getClientType().toString(), JSON.toJSONString(session));
    }

    private static RMap<String, String> getSessionMap(Integer appId, String userId) {
        String redisKey = appId + Constants.RedisConstants.USER_SESSION + userId;
        return RedisManager.getRedissonClient().getMap(redisKey);
    }

    private static void registerLocalSession(ChannelHandlerContext ctx, MessageHeader header, LoginPack pack) {
        SessionSocketContext.put(
                header.getAppId(),
                pack.getUserId(),
                header.getClientType(),
                (NioSocketChannel) ctx.channel()
        );
    }

    public static void removeSession(ChannelHandlerContext ctx) {
        SessionInfo session = getSessionInfo(ctx);
        removeFromRedis(session);
        removeLocalSession(session);
    }

    private static SessionInfo getSessionInfo(ChannelHandlerContext ctx) {
        return new SessionInfo(
                ctx.channel().attr(USER_ID).get(),
                ctx.channel().attr(APP_ID).get(),
                ctx.channel().attr(CLIENT_TYPE).get()
        );
    }

    private static void removeFromRedis(SessionInfo session) {
        RMap<String, String> sessionMap = getSessionMap(session.appId(), session.userId());
        sessionMap.remove(session.clientType().toString());
    }

    private static void removeLocalSession(SessionInfo session) {
        SessionSocketContext.remove(session.appId(), session.userId(), session.clientType());
    }

    public record SessionInfo(String userId, Integer appId, Integer clientType) {}
}
