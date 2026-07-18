#!/bin/bash
# =============================================================================
# 通用 Spring Boot JAR 启停脚本
# 兼容: CentOS 7+, Ubuntu 18+, Kylin V10, Anolis 等
# 用法:
# chmod +x start_jar.sh
# ./start_jar.sh {start|stop|restart|status}
# =============================================================================

set -u

# ------------------------- 配置区 (按需修改) -------------------------
# JAR包名称 (支持通配符匹配第一个jar，若固定则写全名)
APP_JAR_PATTERN="*.jar"

# 应用根目录 (默认为脚本所在目录)
APP_DIR="$(cd "$(dirname "$0")" && pwd)"

# PID与日志文件路径
PID_FILE="$APP_DIR/app.pid"
LOG_FILE="$APP_DIR/app.log"

# 外部配置目录 (相对于APP_DIR)
CONFIG_DIR="$APP_DIR/config"

# 默认JVM参数 (可通过环境变量 JAVA_OPTS 覆盖)
JAVA_OPTS=""

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

log_info()  { echo -e "${GREEN}[INFO]${NC}  $(date '+%Y-%m-%d %H:%M:%S') $*"; }
log_warn()  { echo -e "${YELLOW}[WARN]${NC}  $(date '+%Y-%m-%d %H:%M:%S') $*"; }
log_error() { echo -e "${RED}[ERROR]${NC} $(date '+%Y-%m-%d %H:%M:%S') $*" >&2; }

# 查找JAR包
find_jar() {
    local jar_path
    # 优先精确匹配，再通配符匹配
    if [ -f "$APP_DIR/$APP_JAR_PATTERN" ] 2>/dev/null; then
        jar_path="$APP_DIR/$APP_JAR_PATTERN"
    else
        jar_path=$(find "$APP_DIR" -maxdepth 1 -name "$APP_JAR_PATTERN" -type f | head -n1)
    fi

    if [ -z "$jar_path" ] || [ ! -f "$jar_path" ]; then
        log_error "未找到JAR包: $APP_DIR/$APP_JAR_PATTERN"
        return 1
    fi
    echo "$jar_path"
}

# 获取真实PID (校验进程是否真正存在且为java进程)
get_pid() {
    if [ -f "$PID_FILE" ]; then
        local pid
        pid=$(cat "$PID_FILE" 2>/dev/null)
        if [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null; then
            # 二次校验: 确认该PID确实是java进程(防止PID被复用)
            if grep -q "java" "/proc/$pid/cmdline" 2>/dev/null; then
                echo "$pid"
                return 0
            fi
        fi
    fi
    return 1
}

is_running() {
    get_pid > /dev/null 2>&1
}

# 检测Java环境
check_java() {
    if ! command -v java &>/dev/null; then
        log_error "未找到 java 命令, 请检查 JAVA_HOME 或 PATH 配置"
        log_error "当前PATH: $PATH"
        return 1
    fi
    log_info "Java版本: $(java -version 2>&1 | head -n1)"
}

start() {
    if is_running; then
        log_warn "应用已在运行中, PID: $(get_pid)"
        return 1
    fi

    check_java || return 1

    local jar_path
    jar_path=$(find_jar) || return 1

    # 确保config目录存在
    mkdir -p "$CONFIG_DIR"

    log_info "启动应用: $(basename "$jar_path")"
    log_info "JVM参数: $JAVA_OPTS"
    log_info "配置目录: $CONFIG_DIR"
    log_info "日志文件: $LOG_FILE"

    # 使用 exec 方式启动，避免多余shell进程
    # nohup + & 后台运行，stdout/stderr 统一输出到日志
    nohup java -jar "$jar_path" \
        $JAVA_OPTS \
        > "$LOG_FILE" 2>&1 &

    local new_pid=$!
    echo "$new_pid" > "$PID_FILE"

    # 等待1秒确认进程未立即退出
    sleep 1
    if kill -0 "$new_pid" 2>/dev/null; then
        log_info " √ 启动成功, PID: $new_pid"
    else
        log_error " × 启动失败, 请查看日志: $LOG_FILE"
        rm -f "$PID_FILE"
        return 1
    fi
}

stop() {
    if ! is_running; then
        log_warn "应用未在运行"
        rm -f "$PID_FILE"
        return 0
    fi

    local pid
    pid=$(get_pid)
    log_info "停止应用中, PID: $pid ..."

    # 优雅关闭 (SIGTERM)
    kill "$pid" 2>/dev/null

    # 最多等待30秒
    local count=0
    while [ $count -lt 30 ]; do
        if ! kill -0 "$pid" 2>/dev/null; then
            rm -f "$PID_FILE"
            log_info " √ 已优雅停止"
            return 0
        fi
        sleep 1
        count=$((count + 1))
    done

    # 超时强制终止
    log_warn "优雅关闭超时(30s), 强制终止 ..."
    kill -9 "$pid" 2>/dev/null
    sleep 1
    rm -f "$PID_FILE"
    log_info " √ 已强制停止"
}

status() {
    if is_running; then
        local pid
        pid=$(get_pid)
        log_info " √ 运行中, PID: $pid"
        # 显示运行时长
        if [ -d "/proc/$pid" ]; then
            local etime
            etime=$(ps -o etime= -p "$pid" 2>/dev/null | tr -d ' ')
            [ -n "$etime" ] && log_info "   运行时长: $etime"
        fi
    else
        log_warn " × 未在运行"
        # 清理残留PID文件
        [ -f "$PID_FILE" ] && rm -f "$PID_FILE"
    fi
}

# ========================= 主入口 =========================
case "${1:-}" in
    start)   start   ;;
    stop)    stop    ;;
    restart) stop && sleep 2 && start ;;
    status)  status  ;;
    *)
        echo "用法: $0 {start|stop|restart|status}"
        echo ""
        echo "环境变量:"
        echo "  JAVA_OPTS  自定义JVM参数 (当前默认: $DEFAULT_JAVA_OPTS)"
        echo ""
        echo "示例:"
        echo "  JAVA_OPTS=\"-Xms1g -Xmx2g\" $0 start"
        exit 1
        ;;
esac