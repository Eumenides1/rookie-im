#!/bin/bash
# ==================== 配置区 ====================
PROJECT_NAME="rookie-im-server"
REMOTE_USER="root"           # 服务器用户名
REMOTE_HOST="106.54.240.161" # 服务器 IP 地址
REMOTE_PATH="/root/rookie-im-server" # 部署路径
JAVA_OPTIONS="-Xms512m -Xmx1024m" # Java 启动参数

# 模块配置
PLATFORM_JAR_NAME="rookie-im-platform-1.0-SNAPSHOT.jar"
PLATFORM_LOCAL_JAR_PATH="rookie-im-platform/target/${PLATFORM_JAR_NAME}"
SERVICE_JAR_NAME="rookie-im-service-1.0-SNAPSHOT.jar"
SERVICE_LOCAL_JAR_PATH="rookie-im-service/target/${SERVICE_JAR_NAME}"

# ==================== 功能区 ====================

# 打包项目
function build_project() {
    MODULE=$1
    echo ">>> 开始打包模块: ${MODULE}..."
    SCRIPT_DIR=$(dirname "$0")
    PROJECT_DIR=$(cd "$SCRIPT_DIR/../../" && pwd) # 定位到项目根目录
    cd "$PROJECT_DIR" || { echo ">>> 进入项目根目录失败，退出！"; exit 1; }

    # 根据模块名称选择打包
    if [ "$MODULE" == "platform" ]; then
        mvn clean package -pl rookie-im-platform -am -DskipTests
    elif [ "$MODULE" == "service" ]; then
        mvn clean package -pl rookie-im-service -am -DskipTests
    else
        echo ">>> 未知模块: ${MODULE}，退出！"
        exit 1
    fi

    if [ $? -ne 0 ]; then
        echo ">>> 打包失败，退出！"
        exit 1
    fi
    echo ">>> 打包完成！"
}

# 推送文件到服务器
function upload_to_server() {
    MODULE=$1
    JAR_NAME=$2
    LOCAL_JAR_PATH=$3

    echo ">>> 推送模块 ${MODULE} 的文件到服务器..."

    # 检查 jar 文件是否存在
    if [ ! -f "${LOCAL_JAR_PATH}" ]; then
        echo ">>> 未找到 ${LOCAL_JAR_PATH} 文件，退出！"
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
    scp ${LOCAL_JAR_PATH} ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/
    if [ $? -ne 0 ]; then
        echo ">>> 推送失败，退出！"
        exit 1
    fi
    echo ">>> 推送完成！"
}

# 远程启动服务
function run_on_server() {
    MODULE=$1
    JAR_NAME=$2

    echo ">>> 登录服务器并启动 ${MODULE} 模块服务..."
    ssh ${REMOTE_USER}@${REMOTE_HOST} << EOF
        echo ">>> 检查旧服务..."
        ps -ef | grep ${JAR_NAME} | grep -v grep | awk '{print \$2}' | xargs -r kill -9
        echo ">>> 启动新服务..."
        nohup java ${JAVA_OPTIONS} -jar ${REMOTE_PATH}/${JAR_NAME} > ${REMOTE_PATH}/${MODULE}.log 2>&1 &
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
MODULE=$1

if [ -z "$MODULE" ]; then
    echo ">>> 请传递模块名称: platform, service, 或 all"
    exit 1
fi

echo ">>> 开始 CI/CD 流程..."

if [ "$MODULE" == "platform" ]; then
    build_project "platform"
    upload_to_server "platform" "${PLATFORM_JAR_NAME}" "${PLATFORM_LOCAL_JAR_PATH}"
    run_on_server "platform" "${PLATFORM_JAR_NAME}"
fi

if [ "$MODULE" == "service" ]; then
    build_project "service"
    upload_to_server "service" "${SERVICE_JAR_NAME}" "${SERVICE_LOCAL_JAR_PATH}"
    run_on_server "service" "${SERVICE_JAR_NAME}"
fi

if [ "$MODULE" == "all" ]; then
    echo ">>> 打包并部署 platform 模块..."
    build_project "platform"
    upload_to_server "platform" "${PLATFORM_JAR_NAME}" "${PLATFORM_LOCAL_JAR_PATH}"
    run_on_server "platform" "${PLATFORM_JAR_NAME}"

    echo ">>> 打包并部署 service 模块..."
    build_project "service"
    upload_to_server "service" "${SERVICE_JAR_NAME}" "${SERVICE_LOCAL_JAR_PATH}"
    run_on_server "service" "${SERVICE_JAR_NAME}"
fi

echo ">>> 项目部署完成！"