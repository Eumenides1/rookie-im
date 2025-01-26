package com.rookie.stack.core.server;

import com.rookie.stack.core.codec.MessageDecoder;
import com.rookie.stack.core.config.BootStrapConfig;
import com.rookie.stack.core.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Classname RookieImServer
 * @Description Rookie IM 主程序
 * @Date 2025/1/24 13:50
 * @Created by liujiapeng
 */
public class RookieImServer {

    BootStrapConfig.TcpConfig tcpConfig;
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ServerBootstrap server;

    public RookieImServer(BootStrapConfig.TcpConfig config) {
        this.tcpConfig = config;
        bossGroup = new NioEventLoopGroup(config.getBossThreadSize());
        workerGroup = new NioEventLoopGroup(config.getWorkThreadSize());
        server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 10240)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MessageDecoder());
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
    }

    public void start() {
        this.server.bind(tcpConfig.getTcpPort());
    }
}
