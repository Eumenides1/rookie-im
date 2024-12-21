package com.rookie.stack.im.domain.vo.base;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Name：BaseUserInfo
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@Data
@Schema(description = "用户基础信息")
public class BaseUserInfo {

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

    @Schema(description = "扩展字段，存储JSON格式的额外信息")
    private String extra;  // 存储 JSON 数据


}
