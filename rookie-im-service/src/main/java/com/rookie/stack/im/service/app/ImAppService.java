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

    NewAppResp newApp(NewAppReq req);

}
