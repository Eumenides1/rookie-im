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
@TableName("platform_user_login_log")
public class PlatformUserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
      @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 登录IP（IPv4或IPv6）
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 用户代理信息（浏览器、设备等）
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 状态：1-正常，2-异常
     */
    @TableField("status")
    private Byte status;
}
