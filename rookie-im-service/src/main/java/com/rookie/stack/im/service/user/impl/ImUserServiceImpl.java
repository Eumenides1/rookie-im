package com.rookie.stack.im.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rookie.stack.im.common.exception.AppIdMissingException;
import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.entity.ImUserData;
import com.rookie.stack.im.domain.vo.req.base.PageBaseReq;
import com.rookie.stack.im.domain.vo.req.user.GetUserListPageReq;
import com.rookie.stack.im.domain.vo.req.user.ImportUserData;
import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.resp.base.BaseUserInfo;
import com.rookie.stack.im.domain.vo.resp.base.PageBaseResp;
import com.rookie.stack.im.domain.vo.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.ImUserService;
import com.rookie.stack.im.service.user.adapter.ImUserAdapter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        // 获取 AppId （注解已经校验过了，所以这里不会为空）但是还是校验下
        String appIdHeader = request.getHeader("AppId");
        if (appIdHeader == null) {
            throw new AppIdMissingException("AppId is missing in request header");
        }
        Integer appId = Integer.parseInt(appIdHeader); // 假设 appId 为 Long 类型，如果是其它类型，需要转换

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
    public PageBaseResp<BaseUserInfo> queryUsers(GetUserListPageReq getUserListPageReq) {
        // 获取 AppId （注解已经校验过了，所以这里不会为空）但是还是校验下
        String appIdHeader = request.getHeader("AppId");
        if (appIdHeader == null) {
            throw new AppIdMissingException("AppId is missing in request header");
        }
        Integer appId = Integer.parseInt(appIdHeader); // 假设 appId 为 Long 类型，如果是其它类型，需要转换

        IPage<ImUserData> imUserDataIPage = imUserDataDao.getUserInfoPage(appId, getUserListPageReq);

        if (CollectionUtil.isEmpty(imUserDataIPage.getRecords())) {
            return PageBaseResp.empty();
        }
        return PageBaseResp.init(imUserDataIPage, ImUserAdapter.buildBaseUserInfo(imUserDataIPage.getRecords()));
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


