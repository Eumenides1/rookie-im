package com.rookie.stack.core.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class TcpClientTest {
    private static final int LOGIN_COMMAND = 0x2328;
    private static final int LOGOUT_COMMAND = 0x232b;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9000;
    private static final String USER_ID = "jaguarliu";
    private static final int APP_ID = 10000;
    private static final int CLIENT_TYPE = 4;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            // 公共协议参数
            String imei = UUID.randomUUID().toString();
            int version = 1;
            int messageType = 0x0;

            // 发送登录命令
            sendCommand(dos, LOGIN_COMMAND, imei, USER_ID, version, CLIENT_TYPE, messageType, APP_ID);
            System.out.println("登录命令发送完成");

            // 模拟操作间隔
            Thread.sleep(5000);

            // 发送退出命令
            sendCommand(dos, LOGOUT_COMMAND, imei, USER_ID, version, CLIENT_TYPE, messageType, APP_ID);
            System.out.println("退出命令发送完成");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(DataOutputStream dos, int command, String imei,
                                    String userId, int version, int clientType,
                                    int messageType, int appId) throws IOException {
        byte[] packet = buildCommandPacket(command, imei, userId, version,
                clientType, messageType, appId);
        dos.write(packet);
        dos.flush();
    }

    private static byte[] buildCommandPacket(int command, String imei, String userId,
                                             int version, int clientType,
                                             int messageType, int appId)
            throws UnsupportedEncodingException {
        // 构造IMEI和Body
        byte[] imeiBytes = imei.getBytes("UTF-8");
        String jsonBody = "{\"userId\":\"" + userId + "\"}";
        byte[] bodyBytes = jsonBody.getBytes("UTF-8");

        // 计算报文长度
        int totalLength = 4 * 7 + imeiBytes.length + bodyBytes.length;

        // 构建二进制协议
        ByteBuffer buffer = ByteBuffer.allocate(totalLength)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(command)
                .putInt(version)
                .putInt(clientType)
                .putInt(messageType)
                .putInt(appId)
                .putInt(imeiBytes.length)
                .putInt(bodyBytes.length)
                .put(imeiBytes)
                .put(bodyBytes);

        return buffer.array();
    }
}
