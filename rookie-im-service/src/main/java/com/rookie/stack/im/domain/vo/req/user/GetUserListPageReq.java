package com.rookie.stack.im.domain.vo.req.user;

import com.rookie.stack.im.domain.vo.req.base.PageBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Name：GetUserListPageReq
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@Data
@Schema(description = "获取用户资料列表请求体（支持分页）")
public class GetUserListPageReq extends PageBaseReq {
    @Schema(description = "好友验证方式，0：允许所有人，1：需要验证，2：拒绝所有人")
    private Integer friendAllowType;

    @Schema(description = "是否禁止被添加好友，0：允许，1：禁止")
    private Integer disableAddFriend;

    @Schema(description = "用户类型，0：普通用户，1：管理员，2：其他")
    private Integer userType;
}
