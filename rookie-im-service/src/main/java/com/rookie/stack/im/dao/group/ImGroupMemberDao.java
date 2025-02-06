package com.rookie.stack.im.dao.group;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.im.domain.entity.group.ImGroupMember;
import com.rookie.stack.im.mapper.group.ImGroupMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Classname ImGroupMemberDao
 * @Description TODO
 * @Date 2025/2/6 11:19
 * @Created by liujiapeng
 */
@Repository
public class ImGroupMemberDao extends ServiceImpl<ImGroupMemberMapper, ImGroupMember> {

    @Resource
    private ImGroupMemberMapper imGroupMemberMapper;

}
