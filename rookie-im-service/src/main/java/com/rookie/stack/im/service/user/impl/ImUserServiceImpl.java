package com.rookie.stack.im.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.common.exception.BusinessException;
import com.rookie.stack.common.utils.AssertUtil;
import com.rookie.stack.im.common.constants.enums.user.ImUserStatusEnum;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.dto.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.dto.req.user.ImportUserData;
import com.rookie.stack.im.domain.dto.req.user.ImportUserReq;
import com.rookie.stack.im.domain.dto.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.domain.dto.resp.user.GetUserInfoResp;
import com.rookie.stack.im.domain.dto.resp.user.ImportUserResp;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.service.user.ImUserService;
import com.rookie.stack.im.service.user.adapter.ImUserAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * Name：ImUserServiceImpl
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Service
@Slf4j
public class ImUserServiceImpl implements ImUserService {

    public final static Integer MAX_IMPORT_COUNT = 100000;

    private final ImUserDataDao imUserDataDao;
    private final ExecutorService globalExecutorService;

    public ImUserServiceImpl(ImUserDataDao imUserDataDao, ExecutorService globalExecutorService) {
        this.imUserDataDao = imUserDataDao;
        this.globalExecutorService = globalExecutorService;
    }

    @Override
    public ImportUserResp importUsers(ImportUserReq importUserReq) {
        List<Long> successId = Collections.synchronizedList(new ArrayList<>());
        List<Long> failedId = Collections.synchronizedList(new ArrayList<>());
        Integer appId = AppIdContext.getAppId();

        // 分批处理用户数据
        List<List<ImportUserData>> userDataBatches = partitionList(importUserReq.getUserData(), 1000);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (List<ImportUserData> batch : userDataBatches) {
            tasks.add(() -> {
                List<ImUserData> imUserDataList = batch.stream()
                        .map(data -> ImUserAdapter.buildImUserData(data, appId))
                        .collect(Collectors.toList());
                try {
                    // 批量插入
                    imUserDataDao.batchInsertUsers(imUserDataList);
                    imUserDataList.forEach(user -> successId.add(user.getUserId()));
                } catch (Exception e) {
                    log.error("批量插入失败: {}", e.getMessage(), e);
                    imUserDataList.forEach(user -> failedId.add(user.getUserId()));
                }
                return null;
            });
        }

        try {
            globalExecutorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            log.error("任务中断: {}", e.getMessage(), e);
        }

        ImportUserResp importUserResp = new ImportUserResp();
        importUserResp.setSuccessUsers(successId);
        importUserResp.setFailUsers(failedId);
        return importUserResp;
    }

    @Override
    public PageBaseResp<GetUserInfoResp> queryUsers(GetUserListPageReq getUserListPageReq) {
        IPage<ImUserData> imUserDataIPage = imUserDataDao.getUserInfoPage(getUserListPageReq);

        if (CollectionUtil.isEmpty(imUserDataIPage.getRecords())) {
            return PageBaseResp.empty();
        }
        return PageBaseResp.init(imUserDataIPage, ImUserAdapter.buildBaseUserInfo(imUserDataIPage.getRecords()));
    }

    @Override
    public GetUserInfoResp queryUserById(Long userId) {
        ImUserData userInfoById = imUserDataDao.getUserInfoById(userId);
        AssertUtil.isNotEmpty(userInfoById, "用户信息不存在！");
        return ImUserAdapter.buildBaseUserInfo(userInfoById);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoReq req) {
        Integer appId = AppIdContext.getAppId();
        // 判断用户信息有效性
        ImUserData userInfoById = imUserDataDao.getUserInfoById(req.getUserId());
        AssertUtil.isNotEmpty(userInfoById, "用户信息不存在！");
        // 用户状态需要为启用状态
        if (!Objects.equals(userInfoById.getForbiddenFlag(), ImUserStatusEnum.ENABLED.getStatus())) {
            throw new BusinessException(ImUserErrorEnum.USER_STATUS_ERROR);
        }
        imUserDataDao.updateUserInfoById(req);
    }

    @Override
    public void deleteUserById(Long userId) {
        Integer appId = AppIdContext.getAppId();
        // 判断用户信息有效性
        ImUserData userInfoById = imUserDataDao.getUserInfoById(userId);
        AssertUtil.isNotEmpty(userInfoById, "用户信息不存在！");
        // 用户状态需要为启用状态
        if (!Objects.equals(userInfoById.getDelFlag(), ImUserStatusEnum.NOT_DELETED.getStatus())) {
            throw new BusinessException(ImUserErrorEnum.USER_STATUS_ERROR);
        }
        imUserDataDao.deleteUserInfoById(userId);
    }

    @Override
    public List<GetUserInfoResp> searchUser(String phone, String email, Long userId, Integer userType) {
        List<ImUserData> userData = imUserDataDao.searchUsers(phone, email, userId, userType);
        return ImUserAdapter.buildBaseUserInfo(userData);
    }

    // 分批方法，将用户数据拆分成多个小批次
    private List<List<ImportUserData>> partitionList(List<ImportUserData> list, int batchSize) {
        List<List<ImportUserData>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            batches.add(new ArrayList<>(list.subList(i, Math.min(i + batchSize, list.size()))));
        }
        return batches;
    }
}


