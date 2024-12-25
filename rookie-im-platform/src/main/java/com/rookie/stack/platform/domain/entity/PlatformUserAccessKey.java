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
 * @since 2024-12-22
 */
@Getter
@Setter
@TableName("platform_user_access_key")
public class PlatformUserAccessKey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "access_key_id", type = IdType.AUTO)
    private Long accessKeyId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * AccessKey
     */
    @TableField("access_key")
    private String accessKey;

    /**
     * 加密后的 SecretKey
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status")
    private Byte status;

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
