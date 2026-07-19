# MIKUYUN-ADMIN

基于 Spring Boot 3.4.3 + Java 21 的后台管理系统，提供完整的权限管理、流程引擎、文件上传、消息队列等功能。

## 技术栈

| 分类 | 技术 |
|------|------|
| 核心框架 | Spring Boot 3.4.3 / Java 21 |
| 数据库 | MySQL + MyBatis-Plus |
| 缓存 | Redis (Redisson) |
| 配置中心 | Nacos |
| 认证授权 | Sa-Token |
| API 文档 | SpringDoc OpenAPI (Swagger) |
| 工具库 | Hutool / Fastjson2 / Lombok |
| 消息队列 | RocketMQ v5 (rocketmq-client-java) |
| 搜索引擎 | Elasticsearch (Spring Data ES) |
| 定时任务 | XXL-JOB |
| 工作流引擎 | Warm-Flow (warm-flow-mybatis-plus-sb3-starter) |
| 对象存储 | 七牛云 + RustFS (S3 兼容) |
| 数据导出 | EasyExcel |

## 项目结构

```
src/main/java/com/mikuyun/admin/
├── controller/            # 控制器层 (HTTP 请求处理)
│   ├── SysLoginController.java       # 登录认证
│   ├── SysUserController.java        # 系统用户管理
│   ├── SysRoleController.java        # 角色管理
│   ├── SysPermissionsController.java # 权限管理
│   ├── SysFileController.java        # 文件上传
│   ├── DictController.java           # 字典管理
│   ├── ExcelTaskController.java      # Excel 导出
│   ├── flow/                         # 工作流引擎
│   │   ├── FlowDefController.java    #   流程定义管理
│   │   ├── FlowInsController.java    #   流程实例管理
│   │   └── FlowTaskController.java   #   流程任务管理
│   └── demo/                         # 示例代码
├── service/               # 服务层 (业务逻辑)
│   ├── impl/                         # 服务实现
│   ├── flow/                         # 流程引擎服务
│   │   ├── IFlowDefService.java      #   流程定义
│   │   ├── IFlowInsService.java      #   流程实例
│   │   ├── IFlowTaskService.java     #   流程任务
│   │   └── listennr/                 #   流程事件监听器
│   ├── qiniu/                        # 七牛云存储服务
│   └── rustfs/                       # RustFS S3 存储服务
├── mapper/                # 数据访问层 (MyBatis-Plus)
├── entity/                # 实体类 (继承 BaseEntity，表前缀 mk_)
├── dto/                   # 请求参数对象 (Jakarta Validation 校验)
├── vo/                    # 响应对象
├── annotation/            # 自定义注解
│   ├── SecurityVerification.java     # 外部调用鉴权
│   └── TokenIgnore.java              # 跳过 Token 校验
├── aop/                   # AOP 切面
│   └── SecurityVerificationAspect.java
├── common/                # 通用类
│   ├── R.java                       # 统一响应封装
│   └── ResultCode.java              # 错误码枚举
├── config/                # 配置类
│   ├── mybatis/                      # MyBatis-Plus 配置 (分页/自动填充/SQL过滤)
│   ├── StpInterfaceImpl.java         # Sa-Token 权限桥接
│   ├── RocketMQAutoConfiguration.java # RocketMQ 自动装配
│   ├── GlobalCorsConfig.java         # 全局跨域
│   └── SpringDocConfig.java          # API 文档配置
├── enums/                 # 枚举类
├── es/                    # Elasticsearch Repository
├── exception/             # 异常处理
│   ├── BizException.java             # 业务异常
│   ├── ServiceException.java         # 服务异常
│   └── GlobalExceptionHandler.java   # 全局异常拦截
├── excel/                 # Excel 导出引擎
├── factory/               # 工厂类
├── interceptor/           # 自定义拦截器
│   └── MyInterceptor.java           # Sa-Token 拦截器 (支持 @TokenIgnore)
├── job/                   # XXL-JOB 定时任务
├── listener/              # 事件监听器
│   └── MySaTokenListener.java       # Sa-Token 登录/退出事件
├── properties/            # 配置属性类
├── rocketmq/              # RocketMQ 消息队列
│   ├── RocketProducer.java           # 生产者
│   ├── ConsumerRegister.java         # 消费者注册
│   ├── IBaseMessageListener.java     # 消息监听器接口
│   └── consumer/                     # 消费者实现
├── socket/                # WebSocket
│   └── WebSocketManager.java         # 连接管理 (按 satoken/广播)
├── support/               # 支撑工具
│   ├── LockTemplateSupport.java      # Redisson 分布式锁
│   └── SpringContextUtils.java       # Spring 上下文工具
└── util/                  # 工具类
    ├── AhoCorasickAutomatonUtils.java # AC 自动机 (违禁词检测)
    ├── TreeUtils.java                # 树形结构工具
    └── OkHttpUtils.java              # HTTP 客户端
```

## 功能特性

### 🔐 权限管理
- 基于 Sa-Token 的认证授权，支持 `@SaCheckRole` / `@SaCheckPermission` 注解鉴权
- `@TokenIgnore` 注解：类或方法级别跳过 Token 校验
- `@SecurityVerification` + AOP：通过请求头 `access_token` 做外部 API 调用鉴权
- 角色-权限细粒度控制，Session 事件监听

### 🔄 工作流引擎
- Warm-Flow 流程引擎集成，支持流程定义、实例、任务管理
- 内置流程设计器 UI（`/warm-flow-ui/`）
- 流程发布/挂起/激活、复制、删除
- 自定义流程事件监听器

### 📁 文件管理
- 七牛云对象存储（按文件类型分 Bucket：图片/Excel/通用）
- RustFS S3 兼容存储
- 文件类型校验、SHA-256 哈希、重复文件检测

### 📊 数据导出
- EasyExcel 导出引擎（策略模式，支持多种引擎切换）
- 定时任务调度导出、速率限制、下载链接生成

### 📱 消息队列
- RocketMQ v5 新版客户端 API，自动发现消费者
- 异步消息处理、延迟消息、多 Topic 广播
- `IAsyncMessageService` 模板方法发送，消息记录入库

### 🔍 搜索引擎
- Elasticsearch Spring Data Repository，全文检索与分页

### 📝 其他功能
- WebSocket 实时通信（点对点 + 广播）
- SQL 注入过滤（`SqlFilterArgumentResolver`）
- 全局跨域配置（`GlobalCorsConfig`）
- 统一响应封装 `R<T>` + 错误码枚举 `ResultCode`
- Redisson 分布式锁（`LockTemplateSupport`）
- XXL-JOB 定时任务

## 快速开始

### 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- RocketMQ 5.x Proxy（可选，可通过配置关闭）

### 编译运行

```bash
# 编译
mvn clean compile

# 打包 (跳过测试)
mvn clean package -DskipTests

# 开发模式运行
mvn spring-boot:run

# 运行单个测试
mvn test -Dtest=ClassName
mvn test -Dtest=ClassName#methodName
```

### Docker 运行

```bash
# 构建镜像
docker build -t mikuyun-admin:latest .

# 运行容器
docker run -d -p 8091:8091 --name mikuyun-admin mikuyun-admin:latest
```

### 部署脚本

```bash
# Docker 一键部署 (自动 git pull + mvn package + docker build + run，需 Docker 和 1panel-network)
./deploy.sh

# Java 服务直接启停 (需先创建 .env 配置文件，定义 JAR_FILE/PORT/SERVICE_NAME 等)
./startJar.sh start
./startJar.sh stop
./startJar.sh restart
./startJar.sh status
```

## 配置说明

### 环境切换
通过 `spring.profiles.active` 指定：
- `local` — Nacos namespace `3bab571d-...`，开发环境
- `prod` — Nacos namespace `e0d7ece3-...`，生产环境

### 主要外部配置（Nacos 管理）

| 配置项 | 说明 |
|--------|------|
| server.port | 服务端口（默认 8091） |
| spring.datasource.* | Druid 数据库连接池配置 |
| spring.data.redis.* | Redis 配置 |
| mikuyun.accessToken | 外部 API 调用鉴权 Token |
| qiniu.* | 七牛云 AK/SK/Bucket 配置 |
| rustfs.* | RustFS S3 配置 |
| rocketmq.* | RocketMQ endpoint/group/消费线程数 |
| xxl.job.* | XXL-JOB 调度中心配置 |

### 关闭 RocketMQ
在配置中设置 `rocketmq.enabled=false` 即可禁用消息队列模块（默认开启）。

## API 文档

启动后访问 Swagger 文档：
- Swagger UI: `http://localhost:8091/swagger-ui.html`
- API Docs: `http://localhost:8091/v3/api-docs`
- 流程设计器: `http://localhost:8091/warm-flow-ui/`

## 开发规范

### 命名规范
- Controller: `XxxController.java`，方法返回 `R<T>`
- Service 接口: `IXxxService.java`（或 `XxxService.java`），实现类: `XxxServiceImpl.java`
- Mapper: `XxxMapper.java`
- Entity: `Xxx.java`，继承 `BaseEntity`
- DTO: `XxxDto.java`，使用 Jakarta Validation 注解校验
- VO: `XxxVo.java`

### 鉴权规范
- 内部接口：使用 `@SaCheckRole` / `@SaCheckPermission` 控制权限
- 公开接口：使用 `@TokenIgnore` 标记（类级别或方法级别）
- 外部系统调用接口：使用 `@SecurityVerification` 注解，调用方在请求头传入 `access_token`
