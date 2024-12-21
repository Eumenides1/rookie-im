package com.rookie.stack.im.domain.vo.resp.base;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Name：BaseUserInfo
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@Data
@Schema(description = "前端所需用户信息")
public class BaseUserInfo {
    @Schema(description = "应用ID")
    private Integer appId;

    @Schema(description = "用户唯一标识")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户头像URL")
    private String photo;

    @Schema(description = "用户性别，0：未知，1：男，2：女")
    private Integer userSex;

    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "用户邮箱地址")
    private String email;

    @Schema(description = "用户生日，格式：yyyy-MM-dd")
    private String birthDay;

    @Schema(description = "用户所在地")
    private String location;

    @Schema(description = "用户个性签名")
    private String selfSignature;

    @Schema(description = "好友验证方式，0：允许所有人，1：需要验证，2：拒绝所有人")
    private Integer friendAllowType;

    @Schema(description = "是否禁止被添加好友，0：允许，1：禁止")
    private Integer disableAddFriend;

    @Schema(description = "用户类型，0：普通用户，1：管理员，2：其他")
    private Integer userType;

    @Schema(description = "扩展字段，存储JSON格式的额外信息")
    private String extra;  // 存储 JSON 数据
}
