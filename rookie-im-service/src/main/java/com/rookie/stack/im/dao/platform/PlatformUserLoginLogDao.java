package com.rookie.stack.im.dao.platform;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.domain.entity.platform.PlatformUserLoginLog;
import com.rookie.stack.im.mapper.platform.PlatformUserLoginLogMapper;
import org.springframework.stereotype.Repository;

/**
 * Name：PlatformUserDao
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Repository
public class PlatformUserLoginLogDao extends ServiceImpl<PlatformUserLoginLogMapper, PlatformUserLoginLog> {
}
