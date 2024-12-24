#!/bin/bash

# 配置备份文件目录
BACKUP_DIR="/root/rookie-im-server"
# 配置备份文件匹配模式
BACKUP_PATTERN="rookie-im-server*.bak"

# 删除两天前的备份文件
echo ">>> 开始清理两天前的备份文件..."
find "${BACKUP_DIR}" -name "${BACKUP_PATTERN}" -type f -mtime +2 -exec rm -f {} \; >> cleanup.log 2>&1

if [ $? -eq 0 ]; then
    echo ">>> 清理完成！"
else
    echo ">>> 清理过程中出现错误！"
    exit 1
fi