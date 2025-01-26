package com.rookie.stack.core.handler;

import cn.hutool.json.JSONObjectIter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.rookie.stack.core.codec.pack.LoginPack;
import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.constants.Constants;
import com.rookie.stack.core.context.SessionSocketContext;
import com.rookie.stack.core.context.UserSession;
import com.rookie.stack.core.enums.SystemCommand;
import com.rookie.stack.core.utils.redis.RedisManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname NettyServerHandler
 * @Description 核心业务处理器
 * @Date 2025/1/24 16:50
 * @Created by liujiapeng
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        logger.info(message.toString());
        Integer command = message.getHeader().getCommand();
        // 登录Command
        if (command == SystemCommand.LOGIN.getCommand()) {
            LoginPack pack = JSON.parseObject(JSONObject.toJSONString(message.getMessagePack()),
                    new TypeReference<LoginPack>() {}.getType());
            channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).set(pack.getUserId());
            // 将channel存起来
            UserSession userSession = new UserSession();
            userSession.setAppId(message.getHeader().getAppId());
            userSession.setClientType(message.getHeader().getClientType());
            userSession.setUserId(pack.getUserId());
            userSession.setConnectState(UserSession.ConnectState.ONLINE_STATUS.getCode());
            RedissonClient client = RedisManager.getRedissonClient();
            RMap<String , String> map = client.getMap(message.getHeader().getAppId() + Constants.RedisConstants.USER_SESSION + pack.getUserId());
            map.put(message.getHeader().getClientType()+"", JSONObject.toJSONString(userSession));
            SessionSocketContext.put(pack.getUserId(), (NioSocketChannel) channelHandlerContext.channel());
        }
    }
}
