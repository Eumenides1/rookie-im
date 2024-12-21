package com.rookie.stack.im.service.user;

import com.rookie.stack.im.domain.vo.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.domain.vo.resp.base.PageBaseResp;
import com.rookie.stack.im.domain.vo.resp.user.GetUserInfoResp;
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
    /**
     * 查询批量用户信息接口
     * @param getUserListPageReq
     */
    PageBaseResp<GetUserInfoResp> queryUsers(GetUserListPageReq getUserListPageReq);
    /**
     * 根据 userId 获取用户信息
     * @param userId
     */
    GetUserInfoResp queryUserById(Long userId);

    void updateUserInfo(UpdateUserInfoReq req);
}
