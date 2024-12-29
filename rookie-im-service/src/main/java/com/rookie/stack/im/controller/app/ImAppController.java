package com.rookie.stack.im.controller.app;

import com.rookie.stack.common.domain.dto.resp.ApiResult;
import com.rookie.stack.im.common.annotations.SkipAppIdValidation;
import com.rookie.stack.im.domain.dto.req.app.NewAppReq;
import com.rookie.stack.im.domain.dto.resp.app.NewAppResp;
import com.rookie.stack.im.service.app.ImAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Name：ImAPpController
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@RestController
@RequestMapping("/im_app")
@SkipAppIdValidation
@Tag(name = "【IM_APP】IM应用相关功能", description = "IM应用相关功能控制层")
public class ImAppController {

    @Resource
    private ImAppService imAppService;

    @PostMapping("/newApp")
    @Operation(summary = "新建 IM 应用接口")
    public ApiResult<NewAppResp> newApp(@RequestBody @Valid NewAppReq newAppReq) {
        NewAppResp newAppResp = imAppService.newApp(newAppReq);
        return ApiResult.success(newAppResp);
    }

    @GetMapping("/check")
    @Operation(summary = "检查应用名称是否重复")
    public ApiResult<Void> checkAppName(@RequestParam String appName) {
        imAppService.appNameExist(appName);
        return ApiResult.success();
    }
}
