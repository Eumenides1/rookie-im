package com.rookie.stack.platform.service;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.rookie.stack.platform.dao.PlatformUserRoleDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Name：StpInterfaceImpl
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Service
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private PlatformUserRoleDao platformUserRoleDao;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return platformUserRoleDao.queryByUserId(StpUtil.getLoginIdAsLong());
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return List.of();
    }
}
