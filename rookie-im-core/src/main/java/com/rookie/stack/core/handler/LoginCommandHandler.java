package com.rookie.stack.core.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rookie.stack.core.codec.pack.LoginPack;
import com.rookie.stack.core.codec.proto.Message;
import com.rookie.stack.core.context.SessionManager;
import com.rookie.stack.core.enums.SystemCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Classname LoginCommandHandler
 * @Description TODO
 * @Date 2025/1/26 15:38
 * @Created by liujiapeng
 */
public class LoginCommandHandler implements CommandHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        LoginPack pack = parseLoginPack(message);
        SessionManager.saveSession(ctx, message, pack);
    }

    private LoginPack parseLoginPack(Message message) {
        return JSON.parseObject(
                JSON.toJSONString(message.getMessagePack()),
                new TypeReference<LoginPack>() {}.getType()
        );
    }

    @Override
    public SystemCommand getCommandType() {
        return SystemCommand.LOGIN;
    }
}
