FROM elasticsearch:8.19.9

# 切换到 root 用户以安装插件（ES 官方镜像默认用户是 elasticsearch）
USER root

# 可选：更新 CA 证书（防止 HTTPS 下载失败）
RUN apt-get update && apt-get install -y ca-certificates && rm -rf /var/lib/apt/lists/*

# 安装 IK 分词器插件（使用官方链接）
RUN bin/elasticsearch-plugin install --batch https://release.infinilabs.com/analysis-ik/stable/elasticsearch-analysis-ik-8.19.9.zip

# 恢复 elasticsearch 用户
USER elasticsearch