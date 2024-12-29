package com.rookie.stack.im.service.app;

import com.rookie.stack.im.domain.dto.req.app.NewAppReq;
import com.rookie.stack.im.domain.dto.resp.app.NewAppResp;

/**
 * Name：ImAppService
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
public interface ImAppService {

    /**
     * 新建 APP
     */
    NewAppResp newApp(NewAppReq req);

    /**
     * 判断 App 名字是否重复
     */
    void appNameExist(String appName);

}
