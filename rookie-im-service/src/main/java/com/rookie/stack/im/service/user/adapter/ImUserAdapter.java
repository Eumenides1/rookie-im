package com.rookie.stack.im.service.user.adapter;

import com.rookie.stack.im.common.utils.UserIdGenerator;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.domain.enums.user.ImUserStatusEnum;
import com.rookie.stack.im.domain.vo.req.user.ImportUserData;
import com.rookie.stack.im.domain.vo.resp.user.GetUserInfoResp;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<GetUserInfoResp> buildBaseUserInfo(List<ImUserData> importUserDataList) {
        return importUserDataList.stream()
                .map(ImUserAdapter::buildBaseUserInfo)
                .collect(Collectors.toList());
    }

    public static GetUserInfoResp buildBaseUserInfo(ImUserData imUserData) {
        GetUserInfoResp getUserInfoResp = new GetUserInfoResp();
        BeanUtils.copyProperties(imUserData, getUserInfoResp); // 直接复制相同名称的属性
        return getUserInfoResp;
    }
}
