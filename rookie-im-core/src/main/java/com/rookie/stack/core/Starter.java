package com.rookie.stack.core;

import com.rookie.stack.core.config.BootStrapConfig;
import com.rookie.stack.core.reciver.MessageReciver;
import com.rookie.stack.core.register.ServerRegister;
import com.rookie.stack.core.register.Zkit;
import com.rookie.stack.core.server.RookieImServer;
import com.rookie.stack.core.utils.mq.MqFactory;
import com.rookie.stack.core.utils.redis.RedisManager;
import org.I0Itec.zkclient.ZkClient;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Classname Starter
 * @Date 2025/1/24 13:50
 * @Created by liujiapeng
 */
public class Starter {
    public static void main(String[] args) {
        if (args.length > 0) {
            start(args[0]);
        }
    }
    private static void start (String path) {
        Yaml yaml = new Yaml();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            BootStrapConfig bootStrapConfig = yaml.loadAs(fileInputStream, BootStrapConfig.class);
            new RookieImServer(bootStrapConfig.getRookie()).start();
            RedisManager.init(bootStrapConfig);
            MqFactory.init(bootStrapConfig.getRookie().getRabbitmq());
            MessageReciver.init();
            registerZk(bootStrapConfig);
        } catch (FileNotFoundException | UnknownHostException e) {
            e.printStackTrace();
            System.exit(500);
        }
    }

    private static void registerZk(BootStrapConfig config) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        ZkClient zkClient = new ZkClient(config.getRookie().getZkConfig().getZkAddr(),
                config.getRookie().getZkConfig().getZkConnectTimeout());

        Zkit zkit = new Zkit(zkClient);
        ServerRegister serverRegister = new ServerRegister(zkit, hostAddress, config.getRookie());
        Thread thread = new Thread(serverRegister);
        thread.start();
    }
}
