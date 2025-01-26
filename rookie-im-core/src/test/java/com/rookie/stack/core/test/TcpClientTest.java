package com.rookie.stack.core.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class TcpClientTest {

    public static void main(String[] args) {
        try {
            // 生成IMEI（需与服务端兼容的格式）
            String imei = UUID.randomUUID().toString();
            byte[] imeiBytes = imei.getBytes("UTF-8");
            int imeiLength = imeiBytes.length;

            // 建立Socket连接
            Socket socket = new Socket("127.0.0.1", 9000);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // 协议字段定义
            int command = 0x2328;
            int version = 1;
            int clientType = 4;
            int messageType = 0x0;
            int appId = 10000;
            String userId = "lld";

            // 构造JSON数据体
            String jsonData = "{\"userId\":\"" + userId + "\"}";
            byte[] body = jsonData.getBytes("UTF-8");
            int bodyLength = body.length;

            // 计算总字节数并分配缓冲区（大端序）
            int totalLength = 4 * 7 + imeiLength + bodyLength;
            ByteBuffer buffer = ByteBuffer.allocate(totalLength).order(ByteOrder.BIG_ENDIAN);

            // 填充协议头
            buffer.putInt(command);          // 4 bytes
            buffer.putInt(version);          // 4 bytes
            buffer.putInt(clientType);       // 4 bytes
            buffer.putInt(messageType);      // 4 bytes
            buffer.putInt(appId);            // 4 bytes
            buffer.putInt(imeiLength);       // imei长度 4 bytes
            buffer.putInt(bodyLength);       // body长度 4 bytes
            buffer.put(imeiBytes);           // imei内容
            buffer.put(body);                // body内容

            // 发送完整数据包
            dos.write(buffer.array());
            dos.flush();
            System.out.println("数据发送完成，总字节数: " + buffer.position());

            // 关闭连接
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
