package com.rookie.stack.platform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.stack.platform.domain.entity.PlatformUser;
import com.rookie.stack.platform.domain.vo.FrontUserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-22
 */
public interface PlatformUserMapper extends BaseMapper<PlatformUser> {
    @Select("""
            SELECT 
                u.user_id,
                u.username,
                u.email,
                u.phone,
                u.account_type,
                u.parent_user_id,
                u.avatar_url,
                u.gender,
                p.location, 
                p.birthday, 
                p.extra
            FROM platform_user u
            LEFT JOIN platform_user_profile p ON u.user_id = p.user_id
            WHERE u.user_id = #{userId} AND u.status = 1
            """)
    @Results(id = "FlatFrontUserInfoResultMap", value = {
            @Result(column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "account_type", property = "accountType"),
            @Result(column = "parent_user_id", property = "parentUserId"),
            @Result(column = "avatar_url", property = "avatarUrl"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "location", property = "location"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "extra", property = "extra")
    })
    FrontUserInfo getUserFullInfoById(@Param("userId") Long userId);

}
