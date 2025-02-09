package com.rookie.stack.core.register;

import com.rookie.stack.core.constants.Constants;
import org.I0Itec.zkclient.ZkClient;

/**
 * Name：Zkit
 * Author：eumenides
 * Created on: 2025/2/9
 * Description:
 */
public class Zkit {

    private ZkClient zkClient;

    public Zkit(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public void createRootNode() {
        boolean exists = zkClient.exists(Constants.ImCoreZkRoot);
        if (!exists) {
            zkClient.createPersistent(Constants.ImCoreZkRoot);
        }
        boolean tcpExists = zkClient.exists(Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp);
        if (!tcpExists) {
            zkClient.createPersistent(Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp);
        }
        boolean webExists = zkClient.exists(Constants.ImCoreZkRoot + Constants.ImCoreZkRootWeb);
        if (!webExists) {
            zkClient.createPersistent(Constants.ImCoreZkRoot + Constants.ImCoreZkRootWeb);
        }
    }

    public void createNode(String path) {
        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path);
        }
    }


}
