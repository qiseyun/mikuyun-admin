#!/bin/bash

# ============================================================
# 通用Java服务启停脚本
# 用法: ./service.sh [start|stop|restart|status]
# ============================================================

set -euo pipefail

# ---------- 加载配置 ----------
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ENV_FILE="${SCRIPT_DIR}/.env"

if [[ ! -f "$ENV_FILE" ]]; then
    echo "[ERROR] 未找到配置文件: $ENV_FILE"
    exit 1
fi

# 安全加载env文件（仅允许已知变量）
source_env() {
    while IFS='=' read -r key value; do
        # 跳过注释和空行
        [[ "$key" =~ ^[[:space:]]*# ]] && continue
        [[ -z "$key" ]] && continue
        # 去除首尾空格
        key=$(echo "$key" | xargs)
        value=$(echo "$value" | xargs)
        case "$key" in
            JAR_FILE|PORT|SERVICE_NAME|JAVA_OPTS|APP_ARGS|LOG_FILE)
                export "$key=$value"
                ;;
        esac
    done < "$ENV_FILE"
}

source_env

# ---------- 校验必填项 ----------
for var in JAR_FILE PORT SERVICE_NAME; do
    if [[ -z "${!var:-}" ]]; then
        echo "[ERROR] 配置项 $var 未在 .env 中定义"
        exit 1
    fi
done

# ---------- 工具函数 ----------
log_msg() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1"
}

is_port_in_use() {
    ss -tulnp 2>/dev/null | grep -q ":$1 " || \
    netstat -tulnp 2>/dev/null | grep -q ":$1 "
}

get_pid_by_jar() {
    ps -ef | grep "$JAR_FILE" | grep -v grep | awk '{print $2}'
}

kill_process() {
    local pid=$1
    local desc=$2
    log_msg "正在停止 ${desc}，PID: $pid"
    kill "$pid" 2>/dev/null || true
    # 等待最多10秒优雅退出
    for i in $(seq 1 10); do
        if ! kill -0 "$pid" 2>/dev/null; then
            return 0
        fi
        sleep 1
    done
    # 强制杀死
    log_msg "进程未响应，强制终止 PID: $pid"
    kill -9 "$pid" 2>/dev/null || true
    sleep 1
}

do_stop() {
    local stopped=false

    # 1. 按端口查找并停止
    if is_port_in_use "$PORT"; then
        local pids
        pids=$(lsof -t -i :"$PORT" 2>/dev/null || ss -tulnp 2>/dev/null | grep ":$PORT " | awk '{print $NF}' | sed 's/.*pid=\([0-9]*\).*/\1/')
        if [[ -n "$pids" ]]; then
            for pid in $pids; do
                kill_process "$pid" "端口 $PORT 占用进程"
            done
            stopped=true
        fi
    fi

    # 2. 按JAR文件名查找并停止
    local jar_pid
    jar_pid=$(get_pid_by_jar)
    if [[ -n "$jar_pid" ]]; then
        kill_process "$jar_pid" "$SERVICE_NAME"
        stopped=true
    fi

    if $stopped; then
        log_msg "$SERVICE_NAME 已停止"
    else
        log_msg "$SERVICE_NAME 未在运行"
    fi
}

do_start() {
    # 检查JAR文件是否存在
    if [[ ! -f "$JAR_FILE" ]]; then
        log_msg "[ERROR] JAR文件不存在: $JAR_FILE"
        return 1
    fi

    # 检查端口是否仍被占用
    if is_port_in_use "$PORT"; then
        log_msg "[ERROR] 端口 $PORT 仍被占用，请先执行 stop"
        return 1
    fi

    # 构建启动命令
    local cmd="java"
    [[ -n "${JAVA_OPTS:-}" ]] && cmd="$cmd $JAVA_OPTS"
    cmd="$cmd -jar $JAR_FILE"
    [[ -n "${APP_ARGS:-}" ]] && cmd="$cmd $APP_ARGS"

    # 处理日志输出
    local log_redirect
    if [[ -n "${LOG_FILE:-}" ]]; then
        mkdir -p "$(dirname "$LOG_FILE")"
        log_redirect=">> $LOG_FILE 2>&1"
    else
        log_redirect=">> nohup.out 2>&1"
    fi

    log_msg "正在启动 $SERVICE_NAME ..."
    log_msg "启动命令: $cmd"
    eval "nohup $cmd $log_redirect &"
    local new_pid=$!

    # 等待3秒验证启动
    sleep 3
    if kill -0 "$new_pid" 2>/dev/null; then
        log_msg "$SERVICE_NAME 启动成功，PID: $new_pid，端口: $PORT"
    else
        log_msg "[ERROR] $SERVICE_NAME 启动失败，请检查日志"
        return 1
    fi
}

do_status() {
    local pid
    pid=$(get_pid_by_jar)
    if [[ -n "$pid" ]] && kill -0 "$pid" 2>/dev/null; then
        log_msg "$SERVICE_NAME 运行中，PID: $pid，端口: $PORT"
    else
        log_msg "$SERVICE_NAME 未运行"
    fi
}

# ---------- 主入口 ----------
ACTION="${1:-restart}"

case "$ACTION" in
    start)
        do_start
        ;;
    stop)
        do_stop
        ;;
    restart)
        do_stop
        sleep 1
        do_start
        ;;
    status)
        do_status
        ;;
    *)
        echo "用法: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac

log_msg "执行完成"