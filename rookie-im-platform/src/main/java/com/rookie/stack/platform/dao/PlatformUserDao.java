package com.rookie.stack.platform.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rookie.stack.platform.domain.entity.PlatformUser;
import com.rookie.stack.platform.domain.enums.PlatformUserStatusEnum;
import com.rookie.stack.platform.mapper.PlatformUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * Name：PlatformUserDao
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Repository
public class PlatformUserDao extends ServiceImpl<PlatformUserMapper, PlatformUser> {

    @Resource
    private PlatformUserMapper platformUserMapper;

    public PlatformUser getByEmail(String email) {
        return lambdaQuery().
                eq(PlatformUser::getEmail, email).
                eq(PlatformUser::getStatus, PlatformUserStatusEnum.UNFROZEN.getStatus()).
                one();
    }


}
