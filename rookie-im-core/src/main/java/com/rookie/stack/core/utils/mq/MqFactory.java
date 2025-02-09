package com.rookie.stack.core.utils.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rookie.stack.core.config.BootStrapConfig;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * Name：MqFactorys
 * Author：eumenides
 * Created on: 2025/2/9
 * Description:
 */
public class MqFactory {


    private static ConnectionFactory connectionFactory = null;

    private static Channel defaultChannel;

    private static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static Channel getDefaultChannel(String channelName) throws IOException, TimeoutException {
        Channel channel = channelMap.get(channelName);
        if (channel == null) {
            channel = getConnection().createChannel();
            channelMap.put(channelName, channel);
        }
        return channel;
    }

    public static Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    public static void init(BootStrapConfig.RabbitMq rabbitMq) {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(rabbitMq.getHost());
            connectionFactory.setPort(rabbitMq.getPort());
            connectionFactory.setUsername(rabbitMq.getUsername());
            connectionFactory.setPassword(rabbitMq.getPassword());
            connectionFactory.setVirtualHost(rabbitMq.getVirtualHost());
        }
    }
}
