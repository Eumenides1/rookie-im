#!/bin/bash

# 配置备份文件目录
BACKUP_DIR="/root/rookie-im-server"
# 配置备份文件匹配模式
SERVICE_BACKUP_PATTERN="rookie-im-service*.bak"
PLATFORM_BACKUP_PATTERN="rookie-im-platform*.bak"

# 日志文件
LOG_FILE="/root/cleanup.log"

# 删除一天前的 service 模块备份文件
echo ">>> 开始清理一天前的 service 模块备份文件..." | tee -a ${LOG_FILE}
find "${BACKUP_DIR}" -name "${SERVICE_BACKUP_PATTERN}" -type f -mtime +1 -exec rm -f {} \; >> ${LOG_FILE} 2>&1
if [ $? -eq 0 ]; then
    echo ">>> service 模块备份文件清理完成！" | tee -a ${LOG_FILE}
else
    echo ">>> service 模块清理过程中出现错误！" | tee -a ${LOG_FILE}
    exit 1
fi

# 删除一天前的 platform 模块备份文件
echo ">>> 开始清理一天前的 platform 模块备份文件..." | tee -a ${LOG_FILE}
find "${BACKUP_DIR}" -name "${PLATFORM_BACKUP_PATTERN}" -type f -mtime +1 -exec rm -f {} \; >> ${LOG_FILE} 2>&1
if [ $? -eq 0 ]; then
    echo ">>> platform 模块备份文件清理完成！" | tee -a ${LOG_FILE}
else
    echo ">>> platform 模块清理过程中出现错误！" | tee -a ${LOG_FILE}
    exit 1
fi

echo ">>> 所有模块备份清理完成！" | tee -a ${LOG_FILE}