package com.rookie.stack.im.service.friendship;

import com.rookie.stack.im.domain.dto.req.friendship.GetFriendshipRequestReq;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.dto.resp.friendship.FriendshipRequestData;

import java.util.List;

/**
 * 好友关系相关业务
 */
public interface FriendShipService {

    Long newFriendshipRequest(NewFriendShipReq req);

    /**
     * 获取用户的好友申请列表
     * @param userId 用户 id
     * @return 好友申请列表
     */
    List<FriendshipRequestData> queryFriendshipRequests(GetFriendshipRequestReq req);

}
