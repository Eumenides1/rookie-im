package com.rookie.stack.im.dao.group;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.domain.entity.group.ImGroup;
import com.rookie.stack.im.mapper.group.ImGroupMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Classname ImGroupDao
 * @Description TODO
 * @Date 2025/2/6 11:09
 * @Created by liujiapeng
 */
@Repository
public class ImGroupDao extends ServiceImpl<ImGroupMapper, ImGroup> {

    @Resource
    private ImGroupMapper imGroupMapper;

}
