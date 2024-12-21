package com.rookie.stack.im.dao.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.domain.entity.ImUserData;
import com.rookie.stack.im.mapper.ImUserDataMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Name：ImUserDataDao
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Repository
public class ImUserDataDao extends ServiceImpl<ImUserDataMapper, ImUserData> {

    public IPage<ImUserData> getUserInfoPage(Integer appId, Page page) {
        return lambdaQuery()
                .eq(ImUserData::getAppId, appId)
                .orderByDesc(ImUserData::getCreatedAt)
                .page(page);
    }

}
