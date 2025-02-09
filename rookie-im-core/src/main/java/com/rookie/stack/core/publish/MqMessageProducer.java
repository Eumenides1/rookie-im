package com.rookie.stack.core.publish;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rookie.stack.core.utils.mq.MqFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Name：MqMessageProducer
 * Author：eumenides
 * Created on: 2025/2/9
 * Description:
 */
@Slf4j
public class MqMessageProducer {
    public static void sendMessage(Object message) {
        Channel channel = null;
        String channelName = "";
        try {
            channel = MqFactory.getDefaultChannel(channelName);
            channel.basicPublish(channelName, "",null,
                    JSONObject.toJSONString(message).getBytes());
        }catch (Exception e) {
            log.error("发送消息出现异常{}", e.getMessage());
        }
    }
}
