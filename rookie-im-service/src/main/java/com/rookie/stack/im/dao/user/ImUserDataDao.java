package com.rookie.stack.im.dao.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.common.constants.enums.user.ImUserStatusEnum;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.domain.dto.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.dto.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.mapper.user.ImUserDataMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Name：ImUserDataDao
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Repository
public class ImUserDataDao extends ServiceImpl<ImUserDataMapper, ImUserData> {

    @Resource
    private ImUserDataMapper imUserDataMapper;

    public IPage<ImUserData> getUserInfoPage( GetUserListPageReq req) {
        Page page = req.plusPage();
        LambdaQueryWrapper<ImUserData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImUserData::getAppId, AppIdContext.getAppId()) // 必选条件
                .eq(ImUserData::getDelFlag, ImUserStatusEnum.NOT_DELETED.getStatus())
                .eq(req.getFriendAllowType() != null, ImUserData::getFriendAllowType, req.getFriendAllowType())
                .eq(req.getDisableAddFriend() != null, ImUserData::getDisableAddFriend, req.getDisableAddFriend())
                .eq(req.getUserType() != null, ImUserData::getUserType, req.getUserType())
                .orderByDesc(ImUserData::getCreatedAt); // 排序
        return this.page(page, queryWrapper);
    }

    public ImUserData getUserInfoById(Long userId) {
        return lambdaQuery()
                .eq(ImUserData::getAppId, AppIdContext.getAppId())
                .eq(ImUserData::getUserId, userId)
                .eq(ImUserData::getDelFlag, ImUserStatusEnum.NOT_DELETED.getStatus())
                .one();
    }

    public void updateUserInfoById(UpdateUserInfoReq req) {
        ImUserData imUserData = new ImUserData();
        BeanUtils.copyProperties(req, imUserData);
        this.updateById(imUserData);
    }

    public void deleteUserInfoById(Long userId) {
        ImUserData imUserData = new ImUserData();
        imUserData.setUserId(userId);
        imUserData.setDelFlag(ImUserStatusEnum.DELETED.getStatus());
        this.updateById(imUserData);
    }

    public List<ImUserData> searchUsers(String phone, String email, Long userId, Integer userType) {
        QueryWrapper<ImUserData> queryWrapper = new QueryWrapper<>();

        // 必须的条件
        queryWrapper.eq("app_id", AppIdContext.getAppId());
        queryWrapper.eq("del_flag", ImUserStatusEnum.NOT_DELETED.getStatus());

        // 动态条件绑定到 app_id 范围内
        queryWrapper.and(wrapper -> {
            Optional.ofNullable(phone).ifPresent(value -> wrapper.or().eq("phone", value));
            Optional.ofNullable(email).ifPresent(value -> wrapper.or().eq("email", value));
            Optional.ofNullable(userId).ifPresent(value -> wrapper.or().eq("user_id", value));
            Optional.ofNullable(userType).ifPresent(value -> wrapper.or().eq("user_type", value));
        });

        return imUserDataMapper.selectList(queryWrapper);
    }

}
