package com.rookie.stack.im.service.friendship.impl;

import com.rookie.stack.common.utils.AssertUtil;
import com.rookie.stack.im.common.exception.friendship.FriendShipErrorEnum;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.dao.friendship.ImFriendShipDao;
import com.rookie.stack.im.dao.friendship.ImFriendShipRequestDao;
import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.entity.friendship.ImFriendshipRequest;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.service.friendship.FriendShipService;
import com.rookie.stack.im.service.friendship.adapter.FriendshipRequestAdapter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Name：FriendShipServiceImpl
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@Service
public class FriendShipServiceImpl implements FriendShipService {

    @Resource
    private ImFriendShipDao imFriendShipDao;

    @Resource
    private ImFriendShipRequestDao imFriendShipRequestDao;

    @Resource
    private ImUserDataDao imUserDataDao;

    @Override
    @Transactional
    public Long newFriendshipRequest(NewFriendShipReq req) {
        // 0. 需要添加的这个人首先是要存在的
        ImUserData userInfoById = imUserDataDao.getUserInfoById(req.getReceiverId());
        AssertUtil.isNotEmpty(userInfoById, ImUserErrorEnum.USER_NOT_EXISTS);
        // TODO 校验好友关系应抽离做一个新的服务接口
        // 1. 判断两人好友关系，如果是好友，则直接返回已经是好友
        Boolean isFriend = imFriendShipDao.isFriendShipExists(req.getRequesterId(), req.getReceiverId());
        AssertUtil.isFalse(isFriend, FriendShipErrorEnum.ALREADY_FRIEND_SHIP);
        // 2. 判断两人拉黑状态
        Boolean isBlacked = imFriendShipDao.isFriendBlacked(req.getRequesterId(), req.getReceiverId());
        AssertUtil.isFalse(isBlacked, FriendShipErrorEnum.FRIEND_SHIP_BLACKED);
        // 获取好友申请，如果已经有了好友申请，那就更新当前申请，如果没有就新建
        ImFriendshipRequest pendingRequest = imFriendShipRequestDao.findPendingRequest(req.getRequesterId(), req.getReceiverId());
        if (pendingRequest != null) {
            // 更新现有申请
            pendingRequest.setAddWording(req.getAddWording());
            imFriendShipRequestDao.updateById(pendingRequest);
            return pendingRequest.getRequestId();
        } else {
            ImFriendshipRequest imFriendshipRequest = FriendshipRequestAdapter.buildImFriendshipRequest(req);
            imFriendShipRequestDao.save(imFriendshipRequest);
            return imFriendshipRequest.getRequestId();
        }
    }
}
