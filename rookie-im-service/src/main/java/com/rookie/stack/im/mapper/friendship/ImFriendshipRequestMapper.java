package com.rookie.stack.im.mapper.friendship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.stack.im.domain.entity.friendship.ImFriendshipRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 好友请求表，记录用户的好友申请及状态 Mapper 接口
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-29
 */
public interface ImFriendshipRequestMapper extends BaseMapper<ImFriendshipRequest> {

    @Select("SELECT * FROM im_friendship_request " +
            "WHERE app_id = #{appId} AND requester_id = #{requesterId} " +
            "AND receiver_id = #{receiverId} AND approve_status = 0 LIMIT 1")
    ImFriendshipRequest findPendingRequest(@Param("appId") Integer appId,
                                         @Param("requesterId") Long requesterId,
                                         @Param("receiverId") Long receiverId);

}
