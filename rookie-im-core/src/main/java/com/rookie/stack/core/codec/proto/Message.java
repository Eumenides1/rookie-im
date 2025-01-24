package com.rookie.stack.core.codec.proto;

import lombok.Data;

/**
 * @Classname Message
 * @Description 消息体
 * @Date 2025/1/24 16:31
 * @Created by liujiapeng
 */
@Data
public class Message {

    private MessageHeader header;

    private Object messagePack;

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header +
                ", messagePack=" + messagePack +
                '}';
    }
}
