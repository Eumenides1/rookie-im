package com.rookie.stack.im.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.stack.im.domain.entity.user.ImUserData;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-18
 */
public interface ImUserDataMapper extends BaseMapper<ImUserData> {
    /**
     * 批量插入方法
     * 需要在自定义 SqlInjector 中配置 InsertBatchSomeColumn 才能生效
     *
     * @param entityList 实体列表
     * @return 插入条数
     */
    int insertBatchSomeColumn(List<ImUserData> entityList);

}
