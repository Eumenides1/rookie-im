package com.rookie.stack.im.service.friendship.adapter;

import com.rookie.stack.im.common.constants.enums.base.ReadStatusEnum;
import com.rookie.stack.im.common.constants.enums.friendship.FriendshipRequestStatusEnum;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.common.utils.IdGenerator;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.entity.friendship.ImFriendshipRequest;
import org.springframework.beans.BeanUtils;

/**
 * Name：FriendshipRequestAdapter
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
public class FriendshipRequestAdapter {

    public static ImFriendshipRequest buildImFriendshipRequest(NewFriendShipReq req) {
        ImFriendshipRequest imFriendshipRequest = new ImFriendshipRequest();
        BeanUtils.copyProperties(req, imFriendshipRequest);
        imFriendshipRequest.setAppId(AppIdContext.getAppId());
        imFriendshipRequest.setRequestId(IdGenerator.generate());
        imFriendshipRequest.setApproveStatus(FriendshipRequestStatusEnum.PENDING.getStatus());
        imFriendshipRequest.setReadStatus(ReadStatusEnum.UNREAD.getStatus());
        return imFriendshipRequest;
    }

}
