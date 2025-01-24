package com.rookie.stack.platform.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-28
 */
@Getter
@Setter
@TableName("platform_user_role")
public class PlatformUserRole implements Serializable {

    public PlatformUserRole(Long userId, Long appId, Long roleId) {
        this.userId = userId;
        this.appId = appId;
        this.roleId = roleId;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "user_role_id", type = IdType.AUTO)
    private Long userRoleId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 最近更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
