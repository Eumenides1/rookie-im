package com.rookie.stack.im.dao.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.common.constants.enums.user.ImUserStatusEnum;
import com.rookie.stack.im.domain.dto.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.dto.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.mapper.user.ImUserDataMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

/**
 * Name：ImUserDataDao
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Repository
public class ImUserDataDao extends ServiceImpl<ImUserDataMapper, ImUserData> {

    public IPage<ImUserData> getUserInfoPage(Integer appId, GetUserListPageReq req) {
        Page page = req.plusPage();
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImUserData::getAppId, appId) // 必选条件
                .eq(ImUserData::getDelFlag, ImUserStatusEnum.NOT_DELETED.getStatus())
                .eq(req.getFriendAllowType() != null, ImUserData::getFriendAllowType, req.getFriendAllowType())
                .eq(req.getDisableAddFriend() != null, ImUserData::getDisableAddFriend, req.getDisableAddFriend())
                .eq(req.getUserType() != null, ImUserData::getUserType, req.getUserType())
                .orderByDesc(ImUserData::getCreatedAt); // 排序
        return this.page(page, queryWrapper);
    }

    public ImUserData getUserInfoById(Integer appId, Long userId) {
        return lambdaQuery()
                .eq(ImUserData::getAppId, appId)
                .eq(ImUserData::getUserId, userId)
                .one();
    }

    public void updateUserInfoById(Integer appId, UpdateUserInfoReq req) {
        ImUserData imUserData = new ImUserData();
        BeanUtils.copyProperties(req, imUserData);
        this.updateById(imUserData);
    }

    public void deleteUserInfoById(Integer appId, Long userId) {
        ImUserData imUserData = new ImUserData();
        imUserData.setUserId(userId);
        imUserData.setDelFlag(ImUserStatusEnum.DELETED.getStatus());
        this.updateById(imUserData);
    }
}
