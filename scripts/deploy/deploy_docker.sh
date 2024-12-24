#!/bin/bash

# ==================== 配置区 ====================
APP_NAME="rookie-im-server"               # 应用名称
DEFAULT_VERSION="latest"                  # 默认版本号
VERSION=${1:-$DEFAULT_VERSION}            # 从第一个参数获取版本号，默认值为 "latest"
IMAGE_NAME="eumenides741/${APP_NAME}:${VERSION}" # Docker 镜像名称
REMOTE_USER="root"                        # 服务器用户名
REMOTE_HOST="106.54.240.161"              # 服务器 IP 地址
DEPLOY_PATH="/root/rookie-im-server"      # 部署路径

# ==================== 功能区 ====================

# 构建 Docker 镜像
function build_image() {
    echo ">>> 构建 Docker 镜像（版本：${VERSION}）..."

    # 获取脚本所在目录，定位项目根目录
    SCRIPT_DIR=$(dirname "$0")
    PROJECT_DIR=$(cd "$SCRIPT_DIR/../.." && pwd) # 项目根目录

    echo ">>> 切换到项目目录：${PROJECT_DIR}"
    cd "$PROJECT_DIR" || { echo ">>> 切换到项目目录失败，退出！"; exit 1; }

    # 构建镜像
    docker build -t ${IMAGE_NAME} -f "${PROJECT_DIR}/Dockerfile" .
    if [ $? -ne 0 ]; then
        echo ">>> 镜像构建失败，退出！"
        exit 1
    fi
    echo ">>> Docker 镜像构建完成！"
}

# 推送镜像到 Docker Registry
function push_image() {
    echo ">>> 推送镜像到 Docker Registry（版本：${VERSION}）..."
    docker push ${IMAGE_NAME}
    if [ $? -ne 0 ]; then
        echo ">>> 推送失败，退出！"
        exit 1
    fi
    echo ">>> 镜像推送完成！"
}

# 部署到服务器
function deploy_to_server() {
    echo ">>> 部署到远程服务器（版本：${VERSION}）..."
    ssh ${REMOTE_USER}@${REMOTE_HOST} << EOF
        echo ">>> 停止旧容器..."
        docker stop ${APP_NAME} || true
        docker rm ${APP_NAME} || true
        echo ">>> 拉取最新镜像..."
        docker pull ${IMAGE_NAME}
        echo ">>> 启动新容器..."
        docker run -d --name ${APP_NAME} -p 8080:8080 ${IMAGE_NAME}
        echo ">>> 部署完成！（版本：${VERSION}）"
EOF
    if [ $? -ne 0 ]; then
        echo ">>> 部署失败，退出！"
        exit 1
    fi
    echo ">>> 部署完成！"
}

# ==================== 脚本入口 ====================

if [ -z "$VERSION" ]; then
    echo ">>> 未指定版本号，默认使用 latest"
fi

echo ">>> 开始 CI/CD 流程..."
build_image
push_image
deploy_to_server
echo ">>> 项目部署完成！（版本：${VERSION}）"