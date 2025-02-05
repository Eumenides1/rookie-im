package com.rookie.stack.im.dao.friendship;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.common.constants.enums.friendship.FriendshipStatusEnum;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.domain.entity.friendship.ImFriendship;
import com.rookie.stack.im.domain.vo.RelationshipVO;
import com.rookie.stack.im.mapper.friendship.ImFriendshipMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Name：ImFriendShipRequestDao
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@Repository
public class ImFriendShipDao extends ServiceImpl<ImFriendshipMapper, ImFriendship> {

    @Resource
    private ImFriendshipMapper imFriendshipMapper;

    public Boolean isFriendShipExists(Long userId, Long friendId) {
        return imFriendshipMapper.existsByUserIdAndFriendId(
                AppIdContext.getAppId(),
                userId,friendId, FriendshipStatusEnum.NORMAL.getStatus()
        );
    }

    public Boolean isFriendBlacked(Long userId, Long friendId) {
        return imFriendshipMapper.existsByUserIdAndFriendId(
                AppIdContext.getAppId(),
                userId,friendId, FriendshipStatusEnum.BLOCKED.getStatus()
        );
    }

    public List<RelationshipVO> getBatchRelationships(Long userId,
                                                       List<Long> targetIds) {
        return imFriendshipMapper.selectRelationships(
                userId,
                AppIdContext.getAppId(),
                targetIds
        );
    }

}
