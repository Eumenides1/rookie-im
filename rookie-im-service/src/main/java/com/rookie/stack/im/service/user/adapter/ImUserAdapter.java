package com.rookie.stack.im.service.user.adapter;

import com.rookie.stack.im.common.utils.UserIdGenerator;
import com.rookie.stack.im.domain.entity.ImUserData;
import com.rookie.stack.im.domain.enums.ImUserStatusEnum;
import com.rookie.stack.im.domain.vo.req.user.ImportUserData;
import org.springframework.beans.BeanUtils;

/**
 * Name：ImUserAdapter
 * Author：eumenides
 * Created on: 2024/12/19
 * Description:
 */
public class ImUserAdapter {

    public static ImUserData buildImUserData(ImportUserData importUserData, Integer appId) {
        ImUserData imUserData = new ImUserData();
        BeanUtils.copyProperties(importUserData, imUserData);
        imUserData.setAppId(appId);
        imUserData.setUserId(UserIdGenerator.generate(appId));
        imUserData.setForbiddenFlag(ImUserStatusEnum.ENABLED.getStatus());
        imUserData.setDelFlag(ImUserStatusEnum.NOT_DELETED.getStatus());
        return imUserData;
    }
}