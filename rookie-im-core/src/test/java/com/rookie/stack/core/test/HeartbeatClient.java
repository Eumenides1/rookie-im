package com.rookie.stack.core.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatClient {

        // 协议常量
        private static final String SERVER_IP = "127.0.0.1";
        private static final int SERVER_PORT = 9000;
        private static final int LOGIN_COMMAND = 0x2328;
        private static final int HEARTBEAT_COMMAND = 0x270f;
        private static final int LOGOUT_COMMAND = 0x232b;
        private static final int VERSION = 1;
        private static final int CLIENT_TYPE = 4;
        private static final int APP_ID = 10000;
        private static final String USER_ID = "lld";

        private volatile boolean connected = false;
        private ScheduledExecutorService scheduler;
        private Socket socket;
        private DataOutputStream dos;

        public static void main(String[] args) {
            new HeartbeatClient().start();
        }

        public void start() {
            try {
                // 1. 建立连接
                connect();

                // 2. 发送登录请求
                sendLogin();
                System.out.println("登录请求已发送");

                // 等待1秒确保登录完成
                Thread.sleep(1000);

                // 3. 启动心跳
                startHeartbeat();

                // 4. 保持连接（模拟实际使用）
                Thread.sleep(30000);

                // 5. 发送登出
                sendLogout();
                System.out.println("登出请求已发送");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cleanUp();
            }
        }

        private void connect() throws IOException {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            dos = new DataOutputStream(socket.getOutputStream());
            connected = true;
            System.out.println("成功连接到服务器");
        }

        private void sendLogin() throws IOException {
            String imei = UUID.randomUUID().toString();
            String jsonBody = String.format("{\"userId\":\"%s\"}", USER_ID);
            sendCommand(LOGIN_COMMAND, imei, jsonBody);
        }

        private void startHeartbeat() {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    if (connected) {
                        String imei = UUID.randomUUID().toString();
                        sendCommand(HEARTBEAT_COMMAND, imei, "{}");
                        System.out.println("心跳包已发送");
                    }
                } catch (IOException e) {
                    System.err.println("心跳发送失败: " + e.getMessage());
                    reconnect();
                }
            }, 0, 10, TimeUnit.SECONDS);
        }

        private void sendLogout() throws IOException {
            String imei = UUID.randomUUID().toString();
            sendCommand(LOGOUT_COMMAND, imei, "{}");
        }

        private void sendCommand(int command, String imei, String jsonBody) throws IOException {
            byte[] imeiBytes = imei.getBytes("UTF-8");
            byte[] bodyBytes = jsonBody.getBytes("UTF-8");

            ByteBuffer buffer = ByteBuffer.allocate(7 * 4 + imeiBytes.length + bodyBytes.length)
                    .order(ByteOrder.BIG_ENDIAN)
                    .putInt(command)
                    .putInt(VERSION)
                    .putInt(CLIENT_TYPE)
                    .putInt(0x0) // messageType
                    .putInt(APP_ID)
                    .putInt(imeiBytes.length)
                    .putInt(bodyBytes.length)
                    .put(imeiBytes)
                    .put(bodyBytes);

            dos.write(buffer.array());
            dos.flush();
        }

        private void reconnect() {
            cleanUp();
            System.out.println("尝试重新连接...");
            while (true) {
                try {
                    connect();
                    sendLogin();
                    startHeartbeat();
                    return;
                } catch (Exception e) {
                    System.err.println("重连失败，10秒后重试...");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) { /* ignore */ }
                }
            }
        }

        private void cleanUp() {
            connected = false;
            try {
                if (scheduler != null) scheduler.shutdown();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("资源关闭异常: " + e.getMessage());
            }
        }
    }
