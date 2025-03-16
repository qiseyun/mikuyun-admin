#!/bin/sh

# 定义容器和镜像的名称
PROJECT_NAME=mikuyun-admin

# 拉取最新代码
echo "[$(date)] 拉取主分支代码 ..."
git pull

# maven打包
echo "[$(date)] 执行maven打包 $PROJECT_NAME ..."
mvn clean install -Dmaven.test.skip=true -Dgpg.skip=true

# 停止并移除旧容器
echo "[$(date)] 停止并移除旧容器 $PROJECT_NAME ..."
docker stop $PROJECT_NAME || true
docker rm $PROJECT_NAME || true

# 移除旧镜像
echo "[$(date)] 移除旧镜像 $PROJECT_NAME:latest ..."
docker rmi $PROJECT_NAME:latest || true

# 构建镜像
echo "[$(date)] 构建镜像 $PROJECT_NAME ..."
docker build -t $PROJECT_NAME:latest .

# 启动容器
echo "[$(date)] 启动容器 $PROJECT_NAME ..."
docker run -d --name $PROJECT_NAME --network 1panel-network -p 8091:8091 -p 8092:8092  $PROJECT_NAME:latest

# 发布完毕
echo "[$(date)] $PROJECT_NAME 发布完毕 ..."
