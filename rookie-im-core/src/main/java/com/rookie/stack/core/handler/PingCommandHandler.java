package com.rookie.stack.core.handler;

import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.constants.Constants;
import com.rookie.stack.core.enums.SystemCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * @Classname PingCommandHandler
 * @Description 心跳检测事件处理器
 * @Date 2025/1/26 16:12
 * @Created by liujiapeng
 */
public class PingCommandHandler implements CommandHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) throws Exception {
        ctx.channel().attr(AttributeKey.valueOf(Constants.ReadTime)).set(System.currentTimeMillis());
    }

    @Override
    public SystemCommand getCommandType() {
        return SystemCommand.PING;
    }
}
