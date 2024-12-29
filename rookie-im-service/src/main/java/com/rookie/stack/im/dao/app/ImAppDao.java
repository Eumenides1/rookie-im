package com.rookie.stack.im.dao.app;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.common.constants.enums.app.AppStatusEnum;
import com.rookie.stack.im.domain.entity.app.ImApp;
import com.rookie.stack.im.mapper.app.ImAppMapper;
import org.springframework.stereotype.Repository;

/**
 * Name：ImAppDao
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Repository
public class ImAppDao extends ServiceImpl<ImAppMapper, ImApp> {

    public ImApp getByAppName(String appName) {
        return lambdaQuery()
                .eq(ImApp::getName, appName)
                .eq(ImApp::getStatus, AppStatusEnum.ENABLED.getStatus())
                .one();
    }

}
