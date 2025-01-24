package com.rookie.stack.im.domain.entity.app;

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
 * IM 应用表，保存应用的基础信息、安全字段及扩展配置
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-28
 */
@Getter
@Setter
@TableName("im_app")
public class ImApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用 ID，主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;
      @TableField("app_key")
    private Long appKey;

    /**
     * 应用名称
     */
    @TableField("name")
    private String name;

    /**
     * 应用图标地址
     */
    @TableField("icon_url")
    private String iconUrl;

    /**
     * 应用类型，如聊天、通知等
     */
    @TableField("type")
    private Integer type;

    /**
     * 应用描述
     */
    @TableField("description")
    private String description;

    /**
     * 回调地址，用于接收通知
     */
    @TableField("callback_url")
    private String callbackUrl;

    /**
     * 应用的 API Key，用于身份验证
     */
    @TableField("api_key")
    private String apiKey;

    /**
     * 应用的密钥，用于更安全的通信
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 状态，1 表示启用，0 表示禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 应用配置，存储可扩展的配置信息
     */
    @TableField("config")
    private String config;

    /**
     * 创建人 ID
     */
    @TableField("created_by")
    private Long createdBy;

    /**
     * 更新人 ID
     */
    @TableField("updated_by")
    private Long updatedBy;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
