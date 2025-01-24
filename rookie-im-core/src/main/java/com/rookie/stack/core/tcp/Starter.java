package com.rookie.stack.core.tcp;

import com.rookie.stack.core.config.BootStrapConfig;
import com.rookie.stack.core.tcp.server.RookieImServer;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Classname Starter
 * @Description TODO
 * @Date 2025/1/24 13:50
 * @Created by liujiapeng
 */
public class Starter {
    public static void main(String[] args) {
        if (args.length > 0) {
            start(args[0]);
        }
    }
    private static void start (String path)  {
        Yaml yaml = new Yaml();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("D:\\code\\rookie-im\\rookie-im-core\\src\\main\\resources\\rookie.yaml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(500);
        }
        BootStrapConfig bootStrapConfig = yaml.loadAs(fileInputStream, BootStrapConfig.class);
        new RookieImServer(bootStrapConfig.getRookie()).start();
    }
}
