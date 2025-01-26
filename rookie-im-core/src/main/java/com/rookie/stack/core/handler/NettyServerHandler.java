package com.rookie.stack.core.handler;

import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.context.SessionManager;
import com.rookie.stack.core.enums.SystemCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @Classname NettyServerHandler
 * @Description 核心业务处理器
 * @Date 2025/1/24 16:50
 * @Created by liujiapeng
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static final Map<SystemCommand, CommandHandler> HANDLERS = initHandlers();
    private static Map<SystemCommand, CommandHandler> initHandlers() {
        Map<SystemCommand, CommandHandler> map = new EnumMap<>(SystemCommand.class);
        map.put(SystemCommand.LOGIN, new LoginCommandHandler());
        map.put(SystemCommand.LOGOUT, new LogoutCommandHandler());
        return Collections.unmodifiableMap(map);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        SystemCommand command = SystemCommand.match(message.getHeader().getCommand());
        CommandHandler handler = HANDLERS.get(command);
        if (handler != null) {
            handler.handle(channelHandlerContext, message);
        } else {
            logger.warn("Unsupported command: {}", command);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Channel error: {}", ctx.channel().id(), cause);
        SessionManager.removeSession(ctx);
        ctx.close();
    }
}
