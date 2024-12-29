package com.rookie.stack.im.service.user;

import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.im.domain.dto.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.dto.req.user.ImportUserReq;
import com.rookie.stack.im.domain.dto.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.domain.dto.resp.user.GetUserInfoResp;
import com.rookie.stack.im.domain.dto.resp.user.ImportUserResp;

import java.util.List;

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

    /**
     * 更新用户信息
     * @param req
     */
    void updateUserInfo(UpdateUserInfoReq req);

    /**
     * 删除用户信息
     * @param userId
     */
    void deleteUserById(Long userId);

    /**
     * 搜索用户
     * @param phone 用户手机号
     * @param email 用户邮箱
     * @param userId 用户 id
     * @param userType 用户类型
     * @return 用户信息
     */
    List<GetUserInfoResp> searchUser(String phone, String email, Long userId, Integer userType);
}
