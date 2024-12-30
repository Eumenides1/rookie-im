package com.rookie.stack.im.service.friendship;

import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;

/**
 * 好友关系相关业务
 */
public interface FriendShipService {

    Long newFriendshipRequest(NewFriendShipReq req);



}
