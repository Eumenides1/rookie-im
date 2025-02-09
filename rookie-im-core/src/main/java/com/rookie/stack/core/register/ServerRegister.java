package com.rookie.stack.core.register;

import com.rookie.stack.core.config.BootStrapConfig;
import com.rookie.stack.core.constants.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * Name：ServerRegister
 * Author：eumenides
 * Created on: 2025/2/9
 * Description:
 */
@Slf4j
public class ServerRegister implements Runnable{

    private Zkit zkit;
    private String ip;
    private BootStrapConfig.TcpConfig tcpConfig;

    public ServerRegister(Zkit zkit,  String ip,BootStrapConfig.TcpConfig tcpConfig) {
        this.zkit = zkit;
        this.ip = ip;
        this.tcpConfig = tcpConfig;
    }

    @Override
    public void run() {
        zkit.createRootNode();
        String tcpPath = Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp + "/" + ip + ":" +tcpConfig.getTcpPort();
        zkit.createNode(tcpPath);
        log.info("tcp 服务注册成功： {}", tcpPath);
        // TODO 注册 websocket 服务
    }
}
