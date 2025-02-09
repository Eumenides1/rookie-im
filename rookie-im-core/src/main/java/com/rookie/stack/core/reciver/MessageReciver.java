package com.rookie.stack.core.reciver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rookie.stack.core.constants.Constants;
import com.rookie.stack.core.utils.mq.MqFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Name：MessageReciver
 * Author：eumenides
 * Created on: 2025/2/9
 * Description:
 */
@Slf4j
public class MessageReciver {

    private static void startReciverMessage(){
        try {
            Channel defaultChannel = MqFactory.getDefaultChannel(Constants.RabbitConstants.MessageService2Im);
            log.info("init new channel{}",defaultChannel);
            defaultChannel.queueDeclare(Constants.RabbitConstants.MessageService2Im, true, false, false, null);
            defaultChannel.queueBind(
                    Constants.RabbitConstants.MessageService2Im,
                    Constants.RabbitConstants.MessageService2Im,
                    ""
            );
            defaultChannel.basicConsume(Constants.RabbitConstants.MessageService2Im,false,
                    new DefaultConsumer(defaultChannel){
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            // TODO 处理消息服务发来的消息
                            String message = new String(body);
                            log.info("message str: {}", message);
                        }
                    }
            );
        } catch (Exception e) {
        }
    }
    public static void init(){
        startReciverMessage();
    }
}
