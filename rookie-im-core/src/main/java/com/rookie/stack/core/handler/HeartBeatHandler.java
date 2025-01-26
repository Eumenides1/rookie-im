package com.rookie.stack.core.handler;

import com.rookie.stack.core.constants.Constants;
import com.rookie.stack.core.context.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname HeartBeatHandler
 * @Description 心跳处理逻辑
 * @Date 2025/1/26 16:07
 * @Created by liujiapeng
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    private Long heartBeatTime;

    public HeartBeatHandler(Long heartBeatTime) {
        this.heartBeatTime = heartBeatTime;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.info("进入读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                logger.info("进入写空闲");
            }else if (event.state() == IdleState.ALL_IDLE) {
                // 全空闲
                Long lastReadTIme = (Long) ctx.channel().attr(AttributeKey.valueOf(Constants.ReadTime)).get();
                Long now = System.currentTimeMillis();
                if (lastReadTIme != null && now - lastReadTIme > heartBeatTime) {
                    logger.warn("心跳超时触发, channel={}", ctx.channel().id());
                    SessionManager.handleHeartbeatTimeout((NioSocketChannel) ctx.channel());
                }
            }
        }
    }
}
