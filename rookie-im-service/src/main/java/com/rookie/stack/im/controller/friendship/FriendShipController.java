package com.rookie.stack.im.controller.friendship;

import com.rookie.stack.common.domain.dto.resp.ApiResult;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.dto.resp.app.NewAppResp;
import com.rookie.stack.im.service.friendship.FriendShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name：FriendShipController
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@RestController
@RequestMapping("/im_friendship")
@Tag(name = "【IM_FRIENDSHIP】IM用户关系相关功能", description = "IM用户关系相关功能控制层")
public class FriendShipController {

    @Resource
    private FriendShipService friendShipService;

    @PostMapping("/create")
    @Operation(summary = "发送好友申请接口")
    public ApiResult<NewAppResp> newFriendShipRequest(@RequestBody @Valid NewFriendShipReq req) {
        friendShipService.newFriendshipRequest(req);
        return ApiResult.success();
    }
}
