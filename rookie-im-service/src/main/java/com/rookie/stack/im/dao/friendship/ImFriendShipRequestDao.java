package com.rookie.stack.im.dao.friendship;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.domain.dto.req.friendship.GetFriendshipRequestReq;
import com.rookie.stack.im.domain.entity.friendship.ImFriendshipRequest;
import com.rookie.stack.im.mapper.friendship.ImFriendshipRequestMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * Name：ImFriendShipRequestDao
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@Repository
public class ImFriendShipRequestDao extends ServiceImpl<ImFriendshipRequestMapper, ImFriendshipRequest> {

    @Resource
    private ImFriendshipRequestMapper imFriendshipRequestMapper;


    public ImFriendshipRequest findPendingRequest(Long requesterId, Long receiverId) {
        return imFriendshipRequestMapper.findPendingRequest(
                AppIdContext.getAppId(),
                requesterId,receiverId
        );
    }

    public IPage<ImFriendshipRequest> findPendingRequests(GetFriendshipRequestReq req) {
        Page page = req.plusPage();
        LambdaQueryWrapper<ImFriendshipRequest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImFriendshipRequest::getReceiverId, req.getUserId());
        queryWrapper.orderByDesc(ImFriendshipRequest::getCreatedAt);
        return this.page(page, queryWrapper);
    }


}
