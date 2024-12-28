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
@TableName("application_permission")
public class ApplicationPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限点ID
     */
      @TableId(value = "permission_id", type = IdType.AUTO)
    private Long permissionId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 权限名称
     */
    @TableField("permission_name")
    private String permissionName;

    /**
     * 权限唯一标识
     */
    @TableField("permission_key")
    private String permissionKey;

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
