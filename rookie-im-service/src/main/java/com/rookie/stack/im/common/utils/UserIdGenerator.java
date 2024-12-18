package com.rookie.stack.im.common.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Name：UserIdGenerator
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
public class UserIdGenerator {

    // 雪花算法的配置常量
    private static final long EPOCH = 1633046400000L;  // 起始时间（2021年10月1日00:00:00）
    private static final long APP_ID_BITS = 10L;        // AppId的位数
    private static final long SEQUENCE_BITS = 12L;      // 序列号的位数
    private static final long APP_ID_SHIFT = SEQUENCE_BITS;  // AppId偏移量
    private static final long TIMESTAMP_SHIFT = APP_ID_BITS + SEQUENCE_BITS;  // 时间戳偏移量
    private static final long MAX_APP_ID = (1L << APP_ID_BITS) - 1; // 最大 AppId
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1; // 最大序列号

    private long lastTimestamp = -1L;  // 最后生成的时间戳
    private AtomicLong sequence = new AtomicLong(0);  // 自增序列，保证同一毫秒内生成唯一 ID

    // 使用静态实例实现单例
    private static final UserIdGenerator INSTANCE = new UserIdGenerator();

    // 私有构造函数，防止外部创建实例
    private UserIdGenerator() {}

    // 获取单例实例
    public static UserIdGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * 生成用户ID
     * @param appId 应用ID
     * @return 生成的唯一用户ID
     */
    public synchronized long generateUserId(long appId) {
        if (appId < 0 || appId > MAX_APP_ID) {
            throw new IllegalArgumentException("AppId must be between 0 and " + MAX_APP_ID);
        }

        long timestamp = System.currentTimeMillis() - EPOCH;

        // 如果当前时间戳与上次相同，说明同一毫秒内生成多个 ID
        if (timestamp == lastTimestamp) {
            // 如果序列号已经达到最大值，等待下一毫秒
            if (sequence.get() == MAX_SEQUENCE) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 如果是新的一毫秒，重置序列号
            sequence.set(0);
        }

        lastTimestamp = timestamp;

        // 生成UserId
        long userId = (timestamp << TIMESTAMP_SHIFT) | (appId << APP_ID_SHIFT) | sequence.getAndIncrement();
        return userId;
    }

    /**
     * 等待下一毫秒
     * @param lastTimestamp 上一次生成ID的时间戳
     * @return 下一毫秒的时间戳
     */
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis() - EPOCH;
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis() - EPOCH;
        }
        return timestamp;
    }

    // 直接提供静态方法来生成UserId
    public static long generate(long appId) {
        return getInstance().generateUserId(appId);
    }

    public static void main(String[] args) {
        // 假设 AppId 是 1
        long appId = 1;

        // 生成 10 个用户ID
        for (int i = 0; i < 10; i++) {
            long userId = UserIdGenerator.generate(appId);
            System.out.println("Generated UserId: " + userId);
        }
    }
}