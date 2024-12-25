package com.rookie.stack.platform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("platform_user_profile")
public class PlatformUserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
      @TableId("user_id")
    private Long userId;

    /**
     * 地域信息
     */
    @TableField("location")
    private String location;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 扩展字段（JSON格式）
     */
    @TableField("extra")
    private String extra;

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
