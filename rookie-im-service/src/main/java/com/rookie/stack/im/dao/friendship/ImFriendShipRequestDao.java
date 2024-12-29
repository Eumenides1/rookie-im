package com.rookie.stack.im.dao.friendship;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.common.context.AppIdContext;
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


}
