package com.rookie.stack.platform.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.platform.domain.entity.PlatformUserRole;
import com.rookie.stack.platform.mapper.PlatformUserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rookie.stack.platform.common.constants.constants.AppConstants.COMMON_APPID;

/**
 * Name：PlatformUserRoleDao
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Repository
public class PlatformUserRoleDao extends ServiceImpl<PlatformUserRoleMapper, PlatformUserRole> {

    @Resource
    private PlatformUserRoleMapper platformUserRoleMapper;

    public List<String> queryByUserId(Long userId) {
        return platformUserRoleMapper.queryRolesByUserId(userId, COMMON_APPID);
    }
}
