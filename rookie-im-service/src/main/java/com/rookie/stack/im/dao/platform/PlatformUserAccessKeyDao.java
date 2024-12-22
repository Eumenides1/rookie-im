package com.rookie.stack.im.dao.platform;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.domain.entity.platform.PlatformUser;
import com.rookie.stack.im.domain.entity.platform.PlatformUserAccessKey;
import com.rookie.stack.im.mapper.platform.PlatformUserAccessKeyMapper;
import com.rookie.stack.im.mapper.platform.PlatformUserMapper;
import org.springframework.stereotype.Repository;

/**
 * Name：PlatformUserDao
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Repository
public class PlatformUserAccessKeyDao extends ServiceImpl<PlatformUserAccessKeyMapper, PlatformUserAccessKey> {
}
