#!/bin/bash
# mikuyun-admin 启停脚本
# 用法: ./start_jar.sh {start|stop|restart|status}

APP_NAME="mikuyun-admin-1.0.0.jar"
APP_DIR="$(cd "$(dirname "$0")" && pwd)"
JAR_PATH="$APP_DIR/target/$APP_NAME"
PID_FILE="$APP_DIR/app.pid"
LOG_FILE="$APP_DIR/app.log"

JAVA_OPTS="-Xms512m -Xmx1024m"
SPRING_OPTS="--spring.profiles.active=prod"

get_pid() {
    if [ -f "$PID_FILE" ]; then
        cat "$PID_FILE"
    fi
}

is_running() {
    local pid=$(get_pid)
    [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null
}

start() {
    if is_running; then
        echo "已在运行中, PID: $(get_pid)"
        return 1
    fi
    if [ ! -f "$JAR_PATH" ]; then
        echo "找不到 jar 包: $JAR_PATH"
        return 1
    fi
    echo "启动 $APP_NAME ..."
    nohup java $JAVA_OPTS -jar "$JAR_PATH" $SPRING_OPTS > "$LOG_FILE" 2>&1 &
    echo $! > "$PID_FILE"
    echo "启动成功, PID: $(get_pid), 日志: $LOG_FILE"
}

stop() {
    if ! is_running; then
        echo "未在运行"
        rm -f "$PID_FILE"
        return 0
    fi
    local pid=$(get_pid)
    echo "停止中, PID: $pid ..."
    kill "$pid"
    # 最多等 30 秒, 超时强杀
    for i in $(seq 1 30); do
        if ! kill -0 "$pid" 2>/dev/null; then
            rm -f "$PID_FILE"
            echo "已停止"
            return 0
        fi
        sleep 1
    done
    echo "停止超时, 强制杀掉 ..."
    kill -9 "$pid"
    rm -f "$PID_FILE"
    echo "已停止"
}

status() {
    if is_running; then
        echo "运行中, PID: $(get_pid)"
    else
        echo "未在运行"
    fi
}

case "$1" in
    start)   start ;;
    stop)    stop ;;
    restart) stop && start ;;
    status)  status ;;
    *)       echo "用法: $0 {start|stop|restart|status}" ;;
esac
