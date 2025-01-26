package com.rookie.stack.core.handler;

import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.context.SessionManager;
import com.rookie.stack.core.enums.SystemCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Classname LogoutCommandHandler
 * @Description TODO
 * @Date 2025/1/26 15:39
 * @Created by liujiapeng
 */
public class LogoutCommandHandler implements CommandHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        SessionManager.removeSession(ctx);
        ctx.channel().close();
    }

    @Override
    public SystemCommand getCommandType() {
        return SystemCommand.LOGOUT;
    }
}
