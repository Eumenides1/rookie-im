package com.rookie.stack.im.service.app.adapter;

import com.rookie.stack.common.exception.BusinessException;
import com.rookie.stack.common.exception.CommonErrorEnum;
import com.rookie.stack.common.utils.JsonUtils;
import com.rookie.stack.im.common.utils.IdGenerator;
import com.rookie.stack.im.domain.dto.req.app.NewAppReq;
import com.rookie.stack.im.domain.entity.app.ImApp;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Name：ImAppAdapter
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
public class ImAppAdapter {
    public static ImApp buildImApp(NewAppReq req) {
        ImApp imApp = new ImApp();
        BeanUtils.copyProperties(req, imApp);
        imApp.setAppKey(IdGenerator.generate());
        // TODO 待优化 apikey 和 secret 同时存在时设置
        if (req.getApiKey() != null && req.getApiSecret() != null) {
            imApp.setSecretKey(new BCryptPasswordEncoder().encode(req.getApiSecret()));
        }
        if (req.getConfig() != null) {
            // TODO 这里只能判断是不是一个 json，后续扩展 json 解析
            if(JsonUtils.isValidJson(req.getConfig())){
                throw new BusinessException(CommonErrorEnum.PARAM_VALID);
            }
            imApp.setConfig(req.getConfig());
        }
        return imApp;
    }
}
