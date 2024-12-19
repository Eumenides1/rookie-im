package com.rookie.stack.im.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-18
 */
@Getter
@Setter
@TableName("im_user_data")
public class ImUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Long userId;

    @TableField("app_id")
    private Integer appId;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    @TableField("password")
    private String password;

    @TableField("photo")
    private String photo;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    /**
     * 性别（1:男，2:女，3:其他）
     */
    @TableField("user_sex")
    private Integer userSex;

    /**
     * 生日
     */
    @TableField("birth_day")
    private String birthDay;

    /**
     * 地址
     */
    @TableField("location")
    private String location;

    /**
     * 个性签名
     */
    @TableField("self_signature")
    private String selfSignature;

    /**
     * 加好友验证类型（Friend_AllowType） 1无需验证 2需要验证
     */
    @TableField("friend_allow_type")
    private Integer friendAllowType;

    /**
     * 禁用标识 1禁用
     */
    @TableField("forbidden_flag")
    private Integer forbiddenFlag;

    /**
     * 管理员禁止用户添加加好友：0 未禁用 1 已禁用
     */
    @TableField("disable_add_friend")
    private Integer disableAddFriend;

    /**
     * 禁言标识 1禁言
     */
    @TableField("silent_flag")
    private Integer silentFlag;

    /**
     * 用户类型 1普通用户 2客服 3机器人
     */
    @TableField("user_type")
    private Integer userType;

    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 扩展字段，存储 JSON 格式的数据
     */
    @TableField("extra")
    private String extra;

    /**
     * 创建时间
     */
    @TableField(value = "created_at",fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
