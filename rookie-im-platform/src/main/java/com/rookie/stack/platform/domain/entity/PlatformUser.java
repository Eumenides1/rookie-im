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
@TableName("platform_user")
public class PlatformUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
      @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 账户类型：1-主账户，2-子账户
     */
    @TableField("account_type")
    private Byte accountType;

    /**
     * 主账户ID
     */
    @TableField("parent_user_id")
    private Long parentUserId;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 性别：0-未知，1-男，2-女
     */
    @TableField("gender")
    private Byte gender;

    /**
     * 状态：1-启用，2-冻结
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
