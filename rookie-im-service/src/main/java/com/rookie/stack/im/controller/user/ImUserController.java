package com.rookie.stack.im.controller.user;

import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.domain.vo.req.base.PageBaseReq;
import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.resp.base.ApiResult;
import com.rookie.stack.im.domain.vo.resp.base.BaseUserInfo;
import com.rookie.stack.im.domain.vo.resp.base.PageBaseResp;
import com.rookie.stack.im.domain.vo.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.ImUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    @Operation(summary = "获取用户信息接口")
    public ApiResult<PageBaseResp<BaseUserInfo>> listUsers(@RequestBody @Valid PageBaseReq pageBaseReq) {
        return ApiResult.success(imUserService.queryUsers(pageBaseReq));
    }
}
