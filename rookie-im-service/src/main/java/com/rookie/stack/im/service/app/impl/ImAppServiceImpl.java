package com.rookie.stack.im.service.app.impl;

import com.rookie.stack.im.dao.app.ImAppDao;
import com.rookie.stack.im.domain.dto.req.app.NewAppReq;
import com.rookie.stack.im.domain.dto.resp.app.NewAppResp;
import com.rookie.stack.im.domain.entity.app.ImApp;
import com.rookie.stack.im.service.app.ImAppService;
import com.rookie.stack.im.service.app.adapter.ImAppAdapter;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Name：ImAppServiceImpl
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Service
public class ImAppServiceImpl implements ImAppService {

    @Resource
    private ImAppDao imAppDao;

    @Override
    public NewAppResp newApp(NewAppReq req) {
        ImApp imApp = ImAppAdapter.buildImApp(req);
        imAppDao.save(imApp);
        NewAppResp newAppResp = new NewAppResp();
        BeanUtils.copyProperties(imApp, newAppResp);
        return newAppResp;
    }
}
