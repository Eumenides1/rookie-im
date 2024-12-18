package com.rookie.stack.im.service.user;

import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.resp.user.ImportUserResp;

/**
 * Name：ImUserService
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
public interface ImUserService {
    /**
     * 导入用户资料接口
     * @param importUserReq
     */
    ImportUserResp importUsers(ImportUserReq importUserReq);
}
