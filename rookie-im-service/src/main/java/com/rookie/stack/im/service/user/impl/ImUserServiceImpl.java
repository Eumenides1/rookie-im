package com.rookie.stack.im.service.user.impl;

import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.entity.ImUserData;
import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.ImUserService;
import jakarta.annotation.Resource;
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

    @Resource
    private ImUserDataDao imUserDataDao;

    @Override
    public ImportUserResp importUsers(ImportUserReq importUserReq) {
        List<String> successId = new ArrayList<>();
        List<String> failedId = new ArrayList<>();

        // 线程池用于并发处理导入
        ExecutorService executor = Executors.newFixedThreadPool(EXECUTOR_COUNT);  // 线程池大小可以根据实际情况调整
        List<Callable<Void>> tasks = new ArrayList<>();
        // 将用户数据分批处理，每批 100 条
        List<List<ImUserData>> userDataBatches = partitionList(importUserReq.getUserData(), MAX_IMPORT_COUNT);

        // 创建并发任务
        for (List<ImUserData> batch : userDataBatches) {
            tasks.add(() -> {
                for (ImUserData imUserData : batch) {
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
    // 分批方法，将用户数据拆分成多个小批次
    private List<List<ImUserData>> partitionList(List<ImUserData> list, int batchSize) {
        List<List<ImUserData>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            batches.add(new ArrayList<>(list.subList(i, Math.min(i + batchSize, list.size()))));
        }
        return batches;
    }
}


