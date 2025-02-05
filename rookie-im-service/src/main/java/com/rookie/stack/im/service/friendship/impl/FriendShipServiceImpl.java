package com.rookie.stack.im.service.friendship.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.common.utils.AssertUtil;
import com.rookie.stack.im.common.constants.enums.friendship.FriendAllowTypeEnum;
import com.rookie.stack.im.common.exception.friendship.FriendShipErrorEnum;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.dao.friendship.ImFriendShipDao;
import com.rookie.stack.im.dao.friendship.ImFriendShipRequestDao;
import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.dto.req.friendship.GetFriendshipRequestReq;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.dto.resp.friendship.FriendshipRequestData;
import com.rookie.stack.im.domain.entity.friendship.ImFriendshipRequest;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.service.friendship.FriendShipService;
import com.rookie.stack.im.service.friendship.adapter.FriendshipRequestAdapter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        // 要添加的这个人的添加好友状态,如果无需验证则直接建立好友关系
        if (userInfoById.getFriendAllowType() == FriendAllowTypeEnum.NO_VERIFICATION.getCode()) {
            // TODO 建立好友关系 并返回
            return null;
        }
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

    @Override
    public PageBaseResp<FriendshipRequestData> queryFriendshipRequests(GetFriendshipRequestReq req) {
        IPage<ImFriendshipRequest> pendingRequests = imFriendShipRequestDao.findPendingRequests(req);
        if (pendingRequests.getRecords().isEmpty()) {
            return null;
        }
        // 3. 批量查询相关用户信息
        Map<Long, ImUserData> userMap = imUserDataDao.queryRequesterUserInfo(pendingRequests.getRecords());
        List<FriendshipRequestData> collect = pendingRequests.getRecords().stream()
                .map(request -> buildFriendshipRequestData(request, userMap))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return PageBaseResp.init(pendingRequests, collect);
    }

    private FriendshipRequestData buildFriendshipRequestData(
            ImFriendshipRequest request,
            Map<Long, ImUserData> userMap
    ) {
        ImUserData user = userMap.get(request.getRequesterId());
        if (user == null) return null; // 或记录日志告警

        return FriendshipRequestData.builder()
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .photo(user.getPhoto())
                .extra(user.getExtra())
                .addWording(request.getAddWording())
                .approveStatus(request.getApproveStatus())
                .createTime(request.getCreatedAt())
                .build();
    }
}
