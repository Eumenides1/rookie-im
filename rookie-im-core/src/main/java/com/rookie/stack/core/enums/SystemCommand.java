package com.rookie.stack.core.enums;

import java.util.Arrays;

/**
 * @Classname SystemCommand
 * @Description 系统相关指令
 * @Date 2025/1/26 10:19
 * @Created by liujiapeng
 */
public enum SystemCommand implements Command {
    /**
     * 登录 9000
     */
    LOGIN(0x2328),
    LOGOUT(0x232b),
    RECONNECT(0x2330),
    PING(0x270f),
    UNKNOWN(-1);
    private int command;
    SystemCommand(int command) {
        this.command = command;
    }
    @Override
    public int getCommand() {
        return command;
    }

    public static SystemCommand match(Integer commandCode) {
        return Arrays.stream(values())
                .filter(cmd -> cmd.command == commandCode)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
