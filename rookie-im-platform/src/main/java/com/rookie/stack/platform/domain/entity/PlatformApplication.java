package com.rookie.stack.platform.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
@TableName("platform_application")
public class PlatformApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
      @TableId(value = "app_id", type = IdType.AUTO)
    private Long appId;

    /**
     * 应用名称
     */
    @TableField("app_name")
    private String appName;

    /**
     * 应用唯一标识（公开）
     */
    @TableField("app_key")
    private String appKey;

    /**
     * 应用密钥（私密）
     */
    @TableField("app_secret")
    private String appSecret;

    /**
     * 应用所有者的用户ID（主账号）
     */
    @TableField("app_owner")
    private Long appOwner;

    /**
     * 应用状态：1-启用，2-禁用
     */
    @TableField("app_status")
    private Byte appStatus;

    /**
     * 回调URL（如登录/授权回调）
     */
    @TableField("callback_url")
    private String callbackUrl;

    /**
     * 应用图标URL
     */
    @TableField("app_icon")
    private String appIcon;

    /**
     * 应用描述
     */
    @TableField("description")
    private String description;

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
