package com.rookie.stack.platform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.stack.platform.domain.entity.PlatformUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-28
 */
public interface PlatformUserRoleMapper extends BaseMapper<PlatformUserRole> {
    @Select("""
        SELECT DISTINCT r.role_name
           FROM platform_user_role ur
           INNER JOIN platform_role r ON ur.role_id = r.role_id
           WHERE ur.user_id = #{userId} AND ur.app_id = #{appId}
    """)
    List<String> queryRolesByUserId(@Param("userId") Long userId,@Param("appId") Long appId);
}
