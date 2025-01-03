package com.rookie.stack.im.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rookie.stack.common.domain.dto.resp.PageBaseResp;
import com.rookie.stack.common.exception.BusinessException;
import com.rookie.stack.common.utils.AssertUtil;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.common.exception.user.ImUserErrorEnum;
import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.domain.enums.user.ImUserStatusEnum;
import com.rookie.stack.im.domain.dto.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.dto.req.user.ImportUserData;
import com.rookie.stack.im.domain.dto.req.user.ImportUserReq;
import com.rookie.stack.im.domain.dto.req.user.UpdateUserInfoReq;
import com.rookie.stack.im.domain.dto.resp.user.GetUserInfoResp;
import com.rookie.stack.im.domain.dto.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.ImUserService;
import com.rookie.stack.im.service.user.adapter.ImUserAdapter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Name：ImUserServiceImpl
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Service
@Slf4j
public class ImUserServiceImpl implements ImUserService {

    public final static Integer MAX_IMPORT_COUNT = 100;

    private final static Integer EXECUTOR_COUNT = 10;

    private final ImUserDataDao imUserDataDao;
    private final HttpServletRequest request;

    public ImUserServiceImpl(ImUserDataDao imUserDataDao, HttpServletRequest request) {
        this.imUserDataDao = imUserDataDao;
        this.request = request;
    }

    @Override
    public ImportUserResp importUsers(ImportUserReq importUserReq) {
        List<Long> successId = new ArrayList<>();
        List<Long> failedId = new ArrayList<>();
        Integer appId = AppIdContext.getAppId();
        // 线程池用于并发处理导入
        ExecutorService executor = Executors.newFixedThreadPool(EXECUTOR_COUNT);  // 线程池大小可以根据实际情况调整
        List<Callable<Void>> tasks = new ArrayList<>();
        // 将用户数据分批处理，每批 100 条
        List<List<ImportUserData>> userDataBatches = partitionList(importUserReq.getUserData(), MAX_IMPORT_COUNT);

        // 创建并发任务
        for (List<ImportUserData> batch : userDataBatches) {
            tasks.add(() -> {
                for (ImportUserData importUserData : batch) {
                    ImUserData imUserData = ImUserAdapter.buildImUserData(importUserData, appId);
                    try {
                        boolean save = imUserDataDao.save(imUserData);
                        if (save) {
                            successId.add(imUserData.getUserId());
                        } else {
                            failedId.add(imUserData.getUserId());
                        }
                    } catch (Exception e) {
                        log.error("用户资料导入异常，用户ID: {}, 异常信息: {}", imUserData.getUserId(), e.getMessage(), e);
                        failedId.add(imUserData.getUserId());
                    }
                }
                return null;
            });
        }
        // 执行并发任务
        try {
            List<Future<Void>> futures = executor.invokeAll(tasks);
            // 等待所有任务完成
            for (Future<Void> future : futures) {
                future.get();  // 获取结果，以确保任务完成
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("批量导入异常，异常信息: {}", e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
        ImportUserResp importUserResp = new ImportUserResp();
        importUserResp.setSuccessUsers(successId);
        importUserResp.setFailUsers(failedId);

        return importUserResp;
    }

    @Override
    public PageBaseResp<GetUserInfoResp> queryUsers(GetUserListPageReq getUserListPageReq) {
        Integer appId = AppIdContext.getAppId();
        IPage<ImUserData> imUserDataIPage = imUserDataDao.getUserInfoPage(appId, getUserListPageReq);

        if (CollectionUtil.isEmpty(imUserDataIPage.getRecords())) {
            return PageBaseResp.empty();
        }
        return PageBaseResp.init(imUserDataIPage, ImUserAdapter.buildBaseUserInfo(imUserDataIPage.getRecords()));
    }

    @Override
    public GetUserInfoResp queryUserById(Long userId) {
        Integer appId = AppIdContext.getAppId();
        ImUserData userInfoById = imUserDataDao.getUserInfoById(appId, userId);
        AssertUtil.isNotEmpty(userInfoById, "用户信息不存在！");
        return ImUserAdapter.buildBaseUserInfo(userInfoById);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoReq req) {
        Integer appId = AppIdContext.getAppId();
        // 判断用户信息有效性
        ImUserData userInfoById = imUserDataDao.getUserInfoById(appId, req.getUserId());
        AssertUtil.isNotEmpty(userInfoById, "用户信息不存在！");
        // 用户状态需要为启用状态
        if (!Objects.equals(userInfoById.getForbiddenFlag(), ImUserStatusEnum.ENABLED.getStatus())) {
            throw new BusinessException(ImUserErrorEnum.USER_STATUS_ERROR);
        }
        imUserDataDao.updateUserInfoById(appId, req);
    }

    @Override
    public void deleteUserById(Long userId) {
        Integer appId = AppIdContext.getAppId();
        // 判断用户信息有效性
        ImUserData userInfoById = imUserDataDao.getUserInfoById(appId, userId);
        AssertUtil.isNotEmpty(userInfoById, "用户信息不存在！");
        // 用户状态需要为启用状态
        if (!Objects.equals(userInfoById.getDelFlag(), ImUserStatusEnum.NOT_DELETED.getStatus())) {
            throw new BusinessException(ImUserErrorEnum.USER_STATUS_ERROR);
        }
        imUserDataDao.deleteUserInfoById(appId, userId);
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


