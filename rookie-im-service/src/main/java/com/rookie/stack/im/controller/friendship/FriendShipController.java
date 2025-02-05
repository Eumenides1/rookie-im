package com.rookie.stack.im.controller.friendship;

import com.rookie.stack.common.domain.dto.resp.ApiResult;
import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.im.domain.dto.req.friendship.GetFriendshipRequestReq;
import com.rookie.stack.im.domain.dto.req.friendship.NewFriendShipReq;
import com.rookie.stack.im.domain.dto.req.friendship.ProcessRequest;
import com.rookie.stack.im.domain.dto.req.friendship.RelationshipCheckReq;
import com.rookie.stack.im.domain.dto.resp.friendship.FriendshipRequestData;
import com.rookie.stack.im.domain.dto.resp.friendship.RelationshipCheckResult;
import com.rookie.stack.im.service.friendship.FriendShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "发送好友申请接口",description = "用户发送好友申请，系统校验双方关系后创建或更新申请记录")
    public ApiResult<Long> newFriendShipRequest(@RequestBody @Valid NewFriendShipReq req) {
        Long requestId = friendShipService.newFriendshipRequest(req);
        return ApiResult.success(requestId);
    }
    @PostMapping("/get")
    @Operation(summary = "获取好友申请信息列表接口",description = "分页获取好友申请信息列表")
    public ApiResult<PageBaseResp<FriendshipRequestData>> getFriendShipRequests(@RequestBody @Valid GetFriendshipRequestReq req) {
        PageBaseResp<FriendshipRequestData> friendshipRequestDataPageBaseResp = friendShipService.queryFriendshipRequests(req);
        return ApiResult.success(friendshipRequestDataPageBaseResp);
    }
    @PutMapping("/process")
    @Operation(summary = "处理好友申请接口", description = "处理好友申请")
    public ApiResult<Void> processFriendRequest(@RequestBody @Valid ProcessRequest request) {
        friendShipService.processFriendshipRequest(request);
        return ApiResult.success();
    }
    @PostMapping("/check")
    @Operation(summary = "批量校验好友关系接口",description = "批量校验好友关系接口")
    public ApiResult<RelationshipCheckResult> checkRelationships( @RequestBody @Valid RelationshipCheckReq req){
        return ApiResult.success(friendShipService.checkRelationships(req));
    }
}
