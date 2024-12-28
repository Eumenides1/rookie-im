package com.rookie.stack.platform.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.platform.common.constants.enums.PlatformAccessKeyStatusEnum;
import com.rookie.stack.platform.domain.entity.PlatformUserAccessKey;
import com.rookie.stack.platform.mapper.PlatformUserAccessKeyMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * Name：PlatformUserDao
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Repository
public class PlatformUserAccessKeyDao extends ServiceImpl<PlatformUserAccessKeyMapper, PlatformUserAccessKey> {

    @Resource
    private PlatformUserAccessKeyMapper platformUserAccessKeyMapper;

    public PlatformUserAccessKey getByUserId(Long userId) {
        return lambdaQuery()
                .eq(PlatformUserAccessKey::getUserId, userId)
                .eq(PlatformUserAccessKey::getStatus, PlatformAccessKeyStatusEnum.UNFROZEN.getStatus())
                .one();
    }



}
