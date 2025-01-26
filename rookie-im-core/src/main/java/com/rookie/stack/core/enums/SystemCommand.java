package com.rookie.stack.core.enums;

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
    LOGIN(0x2328);
    private int command;
    SystemCommand(int command) {
        this.command = command;
    }
    @Override
    public int getCommand() {
        return command;
    }
}
