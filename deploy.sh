#!/bin/sh

set -e  # 任何命令失败立即退出（关键！）

# === 配置区 ===
PROJECT_NAME=mikuyun-admin
APP_PORT=8091
XXLJOB_PORT=8092
DOCKER_NETWORK=1panel-network
BRANCH=master

# === 日志函数 ===
log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# === 步骤 1：获取当前 Git Commit ID 作为版本号 ===
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    log "错误：当前目录不是 Git 仓库！"
    exit 1
fi

COMMIT_ID=$(git rev-parse --short HEAD)
if [ -z "$COMMIT_ID" ]; then
    log "错误：无法获取 Git commit ID"
    exit 1
fi

TAG="$COMMIT_ID"
IMAGE_NAME="$PROJECT_NAME:$TAG"
LATEST_IMAGE="$PROJECT_NAME:latest"

log "开始部署项目: $PROJECT_NAME，版本: $TAG"

# === 步骤 2：拉取最新代码 ===
log "拉取主分支最新代码..."
git fetch --all && git reset --hard origin/$BRANCH
log "清理未跟踪文件..."
git clean -fd

# === 步骤 3：Maven 打包 ===
log "执行 Maven 打包（跳过测试和 GPG）..."
if ! mvn clean install -Dmaven.test.skip=true -Dgpg.skip=true; then
    log "错误：Maven 构建失败！"
    exit 1
fi

# === 步骤 4：停止并移除旧容器 ===
log "停止并移除旧容器 $PROJECT_NAME ..."
docker stop "$PROJECT_NAME" 2>/dev/null || true
docker rm "$PROJECT_NAME" 2>/dev/null || true

# === 步骤 5：清理旧镜像（保留最近几个版本可选）===
log "移除旧的 latest 镜像（如果存在）..."
docker rmi "$LATEST_IMAGE" 2>/dev/null || true

# === 步骤 6：构建新镜像 ===
log "构建新镜像: $IMAGE_NAME ..."
if ! docker build -t "$IMAGE_NAME" .; then
    log "错误：Docker 镜像构建失败！"
    exit 1
fi

# 打上 latest 标签
docker tag "$IMAGE_NAME" "$LATEST_IMAGE"
log "已打标签: $LATEST_IMAGE"

# === 步骤 7：启动新容器 ===
log "启动新容器 $PROJECT_NAME (端口: $APP_PORT) ..."
if ! docker run -d \
    --name "$PROJECT_NAME" \
    --network "$DOCKER_NETWORK" \
    -p "$APP_PORT:$APP_PORT" \
    -p "$XXLJOB_PORT:$XXLJOB_PORT" \
    "$LATEST_IMAGE"; then
    log "错误：容器启动失败！"
    exit 1
fi

# === 完成 ===
log "部署成功！项目 $PROJECT_NAME 版本 $TAG 已运行。"
log "镜像信息: $IMAGE_NAME"
log "内网地址: http://$PROJECT_NAME:$APP_PORT"
log "外部地址: https://mikuyun-api.mikuyun.online"