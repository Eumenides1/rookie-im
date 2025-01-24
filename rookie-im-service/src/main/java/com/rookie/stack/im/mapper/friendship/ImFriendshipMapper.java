package com.rookie.stack.im.mapper.friendship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.stack.im.domain.entity.friendship.ImFriendship;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 好友关系表，记录用户之间的好友关系及状态 Mapper 接口
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-29
 */
public interface ImFriendshipMapper extends BaseMapper<ImFriendship> {
    /**
     * 查询用户好友关系
     * @param appId appid
     * @param userId 用户 id
     * @param friendId 好友 id
     * @param status 状态
     * @return 是否是好友/是否删除/是否拉黑
     */
    @Select("SELECT COUNT(1) > 0 FROM im_friendship " +
            "WHERE app_id = #{appId} AND user_id = #{userId} AND friend_id = #{friendId} AND status = #{status}")
    boolean existsByUserIdAndFriendId(@Param("appId") int appId,
                                      @Param("userId") Long userId,
                                      @Param("friendId") Long friendId,
                                      @Param("status") int status);

}
