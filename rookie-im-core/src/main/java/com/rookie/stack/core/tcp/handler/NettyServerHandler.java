package com.rookie.stack.core.tcp.handler;

import com.rookie.stack.core.codec.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Classname NettyServerHandler
 * @Description TODO
 * @Date 2025/1/24 16:50
 * @Created by liujiapeng
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        System.out.println(message);
    }
}
