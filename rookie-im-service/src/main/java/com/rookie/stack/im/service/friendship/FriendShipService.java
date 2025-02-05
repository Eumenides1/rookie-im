package com.rookie.stack.im.service.friendship;

import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.im.domain.dto.req.friendship.GetFriendshipRequestReq;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.dto.req.friendship.ProcessRequest;
import com.rookie.stack.im.domain.dto.resp.friendship.FriendshipRequestData;

/**
 * 好友关系相关业务
 */
public interface FriendShipService {

    Long newFriendshipRequest(NewFriendShipReq req);

    /**
     * 获取用户的好友申请列表
     * @param req 用户 id
     * @return 好友申请列表
     */
    PageBaseResp<FriendshipRequestData> queryFriendshipRequests(GetFriendshipRequestReq req);

    /**
     * 处理好友申请
     * @param request 操作好友申请请求体
     */
    void processFriendshipRequest(ProcessRequest request);

}
