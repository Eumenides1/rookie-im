package com.rookie.stack.im.controller.user;

import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.resp.ApiResult;
import com.rookie.stack.im.domain.vo.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.ImUserService;
import jakarta.annotation.Resource;
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
public class ImUserController {

    @Resource
    private ImUserService imUserService;

    @PostMapping("/import")
    public ApiResult<ImportUserResp> importUsers(@RequestBody ImportUserReq importUserReq) {
        if (importUserReq.getUserData().size() > MAX_IMPORT_COUNT) {
            // 返回导入数量过多异常
            throw new BusinessException(ImUserErrorEnum.OUT_BOUND_MAX_IMPORT);
        }
        ImportUserResp importUserResp = imUserService.importUsers(importUserReq);
        return ApiResult.success(importUserResp);
    }
}
