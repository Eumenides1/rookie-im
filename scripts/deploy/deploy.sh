#!/bin/bash
# ==================== 配置区 ====================
PROJECT_NAME="rookie-im-server"
REMOTE_USER="root"           # 服务器用户名
REMOTE_HOST="106.54.240.161"      # 服务器 IP 地址
REMOTE_PATH="/root/rookie-im-server" # 部署路径
JAR_NAME="rookie-im-service-1.0-SNAPSHOT.jar"    # 打包生成的 jar 文件名
LOCAL_JAR_PATH="rookie-im-service/target/rookie-im-service-1.0-SNAPSHOT.jar" # 本地 jar 文件路径
JAVA_OPTIONS="-Xms512m -Xmx1024m" # Java 启动参数

# ==================== 功能区 ====================

# 打包项目
function build_project() {
    echo ">>> 开始打包项目..."
    SCRIPT_DIR=$(dirname "$0")
    PROJECT_DIR=$(cd "$SCRIPT_DIR/../../" && pwd) # 定位到项目根目录
    # shellcheck disable=SC2164
    cd "$PROJECT_DIR"
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo ">>> 打包失败，退出！"
        exit 1
    fi
    echo ">>> 打包完成！"
}

# 推送文件到服务器
function upload_to_server() {
    echo ">>> 推送文件到服务器..."

    # 进入 target 目录
    cd rookie-im-service/target || { echo ">>> 未找到 target 目录，退出！"; exit 1; }

    # 检查 jar 文件是否存在
    if [ ! -f "${JAR_NAME}" ]; then
        echo ">>> 未找到 ${JAR_NAME} 文件，退出！"
        exit 1
    fi

    # 在服务器上备份旧的 jar 文件
    echo ">>> 备份旧文件..."
    ssh ${REMOTE_USER}@${REMOTE_HOST} << EOF
        if [ -f "${REMOTE_PATH}/${JAR_NAME}" ]; then
            TIMESTAMP=\$(date +%Y%m%d%H%M%S)
            mv ${REMOTE_PATH}/${JAR_NAME} ${REMOTE_PATH}/${JAR_NAME}.\${TIMESTAMP}.bak
            echo ">>> 旧文件已备份为 ${JAR_NAME}.\${TIMESTAMP}.bak"
        fi
EOF

    # 推送新文件到服务器
    echo ">>> 推送新文件到服务器..."
    scp ${JAR_NAME} ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/
    if [ $? -ne 0 ]; then
        echo ">>> 推送失败，退出！"
        exit 1
    fi
    echo ">>> 推送完成！"
}

# 远程启动服务
function run_on_server() {
    echo ">>> 登录服务器并启动服务..."
    ssh ${REMOTE_USER}@${REMOTE_HOST} << EOF
        echo ">>> 检查旧服务..."
        ps -ef | grep ${JAR_NAME} | grep -v grep | awk '{print \$2}' | xargs -r kill -9
        echo ">>> 启动新服务..."
        nohup java ${JAVA_OPTIONS} -jar ${REMOTE_PATH}/${JAR_NAME} > ${REMOTE_PATH}/app.log 2>&1 &
        echo ">>> 新服务已启动！"
        exit
EOF
    if [ $? -ne 0 ]; then
        echo ">>> 服务启动失败！"
        exit 1
    fi
    echo ">>> 服务已启动！"
}



# ==================== 脚本入口 ====================

echo ">>> 开始 CI/CD 流程..."
build_project
upload_to_server
run_on_server
echo ">>> 项目部署完成！"

