package com.rookie.stack.im.service.test;

import com.rookie.stack.im.common.utils.IdGenerator;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.mapper.user.ImUserDataMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Name：InsertPerformanceTest
 * Author：eumenides
 * Created on: 2025/1/3
 * Description:
 */
@SpringBootTest
public class InsertPerformanceTest {

    private static final String LOG_FILE_PATH = "performance-test.log";

    private static final Logger log = LoggerFactory.getLogger(InsertPerformanceTest.class);

    @Resource
    private ImUserDataMapper imUserDataMapper;

    private static final int[] DATA_SIZES = {100, 1000, 10000,100000}; // 不同数据量

    @Test
    public void testInsertPerformance() {
        for (int size : DATA_SIZES) {
            List<ImUserData> testData = generateTestData(size);

            logToFileAndConsole("=== 测试开始: 数据量 " + size + " ===");

            // 测试逐条插入
            // measureExecutionTime("逐条插入", () -> insertWithForLoop(testData));

            // 测试批量插入（自定义 SQL）
            measureExecutionTime("批量插入", () -> insertWithBatch(testData));

            // 测试 InsertBatchSomeColumn
            measureExecutionTime("InsertBatchSomeColumn 插入", () -> insertWithInsertBatchSomeColumn(testData));

            logToFileAndConsole("=== 测试结束: 数据量 " + size + " ===");
        }
    }
    private void measureExecutionTime(String operationName, Runnable operation) {
        long start = System.currentTimeMillis();
        try {
            operation.run();
        } catch (Exception e) {
            logToFileAndConsole(operationName + " 失败: " + e.getMessage());
        }
        long end = System.currentTimeMillis();
        logToFileAndConsole(operationName + " 耗时: " + (end - start) + " ms");
    }

    // 逐条插入
    private void insertWithForLoop(List<ImUserData> data) {
        for (ImUserData user : data) {
            imUserDataMapper.insert(user);
        }
    }

    // 逐条插入
    private void insertWithBatch(List<ImUserData> data) {
        imUserDataMapper.insert(data);
    }

    // MyBatis-Plus 的 InsertBatchSomeColumn
    private void insertWithInsertBatchSomeColumn(List<ImUserData> data) {
        imUserDataMapper.insertBatchSomeColumn(data);
    }

    // 生成测试数据
    private List<ImUserData> generateTestData(int size) {
        List<ImUserData> testData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ImUserData user = new ImUserData();
            user.setUserId(IdGenerator.generate(1001));
            user.setAppId(1001);
            user.setNickName("测试用户" + i);
            user.setPassword("password" + i);
            user.setPhoto("https://example.com/photo" + i);
            user.setEmail("user" + i + "@example.com");
            user.setPhone("123456789" + i);
            user.setUserSex(1);
            user.setBirthDay("1990-01-01");
            user.setLocation("测试城市");
            user.setSelfSignature("测试签名");
            user.setFriendAllowType(1);
            user.setForbiddenFlag(0);
            user.setDisableAddFriend(0);
            user.setUserType(1);
            user.setDelFlag(0);
            testData.add(user);
        }
        return testData;
    }

    private void logToFileAndConsole(String message) {
        System.out.println(message); // 打印到控制台
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("日志写入失败: " + e.getMessage());
        }
    }
}
