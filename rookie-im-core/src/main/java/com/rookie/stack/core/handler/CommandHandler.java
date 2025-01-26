package com.rookie.stack.core.handler;

import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.enums.SystemCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Classname CommandHandler
 * @Description TODO
 * @Date 2025/1/26 15:38
 * @Created by liujiapeng
 */
public interface CommandHandler {
    void handle(ChannelHandlerContext ctx, Message message) throws Exception;
    SystemCommand getCommandType();
}
