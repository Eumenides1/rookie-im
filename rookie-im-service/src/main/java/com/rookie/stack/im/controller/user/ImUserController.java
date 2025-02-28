package com.rookie.stack.im.controller.user;

import com.rookie.stack.common.domain.dto.resp.ApiResult;
import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.domain.dto.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.dto.req.user.ImportUserReq;
import com.rookie.stack.im.domain.dto.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.domain.dto.resp.user.GetUserInfoResp;
import com.rookie.stack.im.domain.dto.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.ImUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.rookie.stack.im.service.user.impl.ImUserServiceImpl.MAX_IMPORT_COUNT;

/**
 * Name：ImUserController
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@RestController
@RequestMapping("/im_user")
@Tag(name = "【IM_USER】用户资料相关功能", description = "用户资料相关功能控制层")
public class ImUserController {

    @Resource
    private ImUserService imUserService;

    @PostMapping("/import")
    @Operation(summary = "批量导入用户资料接口")
    public ApiResult<ImportUserResp> importUsers(@RequestBody ImportUserReq importUserReq) {
        if (importUserReq.getUserData().size() > MAX_IMPORT_COUNT) {
            // 返回导入数量过多异常
            throw new BusinessException(ImUserErrorEnum.OUT_BOUND_MAX_IMPORT);
        }
        ImportUserResp importUserResp = imUserService.importUsers(importUserReq);
        return ApiResult.success(importUserResp);
    }
    @PostMapping("/list")
    @Operation(summary = "获取用户信息列表接口")
    public ApiResult<PageBaseResp<GetUserInfoResp>> listUsers(@RequestBody @Valid GetUserListPageReq pageBaseReq) {
        return ApiResult.success(imUserService.queryUsers(pageBaseReq));
    }
    @GetMapping("/{userId}")
    @Operation(summary = "获取单个用户资料详情接口")
    public ApiResult<GetUserInfoResp> getUserInfo(@PathVariable Long userId) {
        return ApiResult.success(imUserService.queryUserById(userId));
    }
    @PostMapping("/update")
    @Operation(summary = "更新用户资料接口")
    public ApiResult<Void> updateUserInfo(@RequestBody @Valid UpdateUserInfoReq updateUserInfoReq) {
        imUserService.updateUserInfo(updateUserInfoReq);
        return ApiResult.success();
    }
    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "删除用户资料接口")
    public ApiResult<Void> deleteUser(@PathVariable Long userId) {
        imUserService.deleteUserById(userId);
        return ApiResult.success();
    }
    @GetMapping("/search")
    @Operation(summary = "搜索用户信息")
    public ApiResult<List<GetUserInfoResp>> searchUsers(@RequestParam(required = false) String phone,
                                       @RequestParam(required = false) String email,
                                       @RequestParam(required = false) Long userId,
                                       @RequestParam(required = false) Integer userType){
        return ApiResult.success(imUserService.searchUser(phone,email,userId,userType));
    }
}
