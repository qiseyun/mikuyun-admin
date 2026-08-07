# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 对话约定

请始终使用简体中文与用户对话，并在回答时保持专业、简洁。

## Build & Run

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

# Docker 构建与运行
docker build -t mikuyun-admin:latest .
docker run -d -p 8091:8091 --name mikuyun-admin mikuyun-admin:latest

# 部署脚本 (需先配置 .env 文件)
./deploy.sh              # Docker 一键部署 (自动 git pull + mvn package + docker build + run)
./startJar.sh start      # Java 服务直接启停
./startJar.sh stop
./startJar.sh restart
./startJar.sh status
```

## Architecture Overview

Spring Boot 3.4.3 + Java 21 后台管理系统，采用标准分层架构，配置通过 Nacos 管理（`local` / `prod` 两个 profile 对应不同的 namespace）。

**核心分层：**
- `controller/` — REST API，返回统一响应 `R<T>`，使用 `@Tag`/`@Operation`（SpringDoc）标注接口文档，`@RequiredArgsConstructor` 注入依赖
- `service/` — 接口继承 `IService<T>`，实现继承 `ServiceImpl<M, T>`（MyBatis-Plus），通过 `@RequiredArgsConstructor` 注入依赖
- `mapper/` — MyBatis-Plus Mapper，主类已配置 `@MapperScan("com.mikuyun.admin.mapper")`
- `entity/` — 数据实体，继承 `BaseEntity`（提供 isDelete、createBy、updateBy、gmtCreated、gmtModified 通用字段），表名前缀 `mk_`
- `dto/` — 请求参数对象（`jakarta.validation.constraints` 校验），按模块分子包
- `vo/` — 响应对象，按模块分子包

**其他关键目录：**
- `annotation/` — 自定义注解（`@TokenIgnore` 跳过 Token 校验、`@SecurityVerification` 外部调用鉴权）
- `aop/` — AOP 切面（`SecurityVerificationAspect`）
- `config/` — 配置类，含 MyBatis-Plus、Sa-Token、RocketMQ、Redis、WebSocket、SpringDoc、CORS、SQL 过滤器
- `enums/` — 枚举类（验证码类型、性别、用户类型、SaToken Session 枚举等）
- `exception/` — `BizException` / `ServiceException` + `GlobalExceptionHandler` 全局异常处理
- `excel/` — EasyExcel 导出引擎（策略模式，`ExcelEngineFactory` 工厂选择引擎）
- `factory/` — 工厂类（`AsyncMessageFactory`、`ExcelEngineFactory`）
- `interceptor/` — `MyInterceptor` 继承 Sa-Token 的 `SaInterceptor`，支持 `@TokenIgnore` 注解跳过校验
- `job/` — XXL-JOB 定时任务处理器（`@XxlJob` 注解）
- `listener/` — `MySaTokenListener` 订阅 Sa-Token 登录/退出/踢下线等事件
- `properties/` — `@ConfigurationProperties` 配置属性类
- `rocketmq/` — RocketMQ 生产者/消费者/消息服务（见下方消息队列章节）
- `socket/` — WebSocket 管理器（支持按 satoken 或广播发消息）
- `support/` — `LockTemplateSupport`（Redisson 分布式锁）、`SpringContextUtils`
- `util/` — 工具类（AC 自动机违禁词检测、树形工具、OkHttp、ID 编码等）

**认证与鉴权：**
- Sa-Token 管理登录态，`StpInterfaceImpl` 桥接自定义角色/权限服务（`sysRoleService` / `sysPermissionsService`）
- `MyInterceptor`（继承 `SaInterceptor`）注册为全局拦截器，排除 `/demo/**`、`/auth/login`、Swagger、warm-flow-ui 等路径；通过 `@TokenIgnore` 注解可标记类或方法跳过 Token 校验
- 类或方法上使用 `@SaCheckRole("super_admin")` / `@SaCheckPermission` 做角色和权限控制
- 自定义 AOP `SecurityVerificationAspect` 配合 `@SecurityVerification` 注解，通过请求头 `access_token` 做外部调用鉴权（配置项 `mikuyun.accessToken`）

**消息队列（RocketMQ v5）：**
- 使用新版 `rocketmq-client-java` 5.1.0 API（非旧版 spring-boot-starter）
- `RocketMQAutoConfiguration` 自动装配 `RocketProducer` 和 `ConsumerRegister`，通过 `@ConditionalOnProperty` 控制开关（`rocketmq.enabled`，默认开启）
- `ConsumerRegister` 启动时自动发现所有 `IBaseMessageListener` 实现，按 topic 分组，使用 `FilterExpression.SUB_ALL` 订阅所有 tag，统一 ConsumerGroup
- `TopicMessageListenerWrapper` 根据消息 tag 二次路由到对应的 listener
- 发送端通过 `IAsyncMessageService` → `AbstractAsyncMessageServiceImpl` 模板方法发送，支持单个/批量（多 topic 广播）和延时等级；`AsyncMessageFactory` 工厂根据 `AsyncMessageTypeEnum` 创建消息
- `IBaseMessageListener.getTopic()` + `getTag()` 定义消费路由，`RocketProducer.send()` 发送
- 消息记录通过 `MqMsgRecordService` 入库

**工作流引擎（Warm-Flow）：**
- 集成 `warm-flow-mybatis-plus-sb3-starter` + `warm-flow-plugin-ui-sb-web`（流程设计器 UI）
- 控制器：`controller/flow/` 下 `FlowDefController`（流程定义）、`FlowInsController`（流程实例）、`FlowTaskController`（流程任务）
- 服务：`service/flow/` 下 `IFlowDefService`/`IFlowInsService`/`IFlowTaskService` 及其实现
- 流程监听器：`service/flow/listennr/` 下 `DefStartListener` 等
- 设计器 UI 路径 `/warm-flow-ui/**` 和 `/warm-flow/**` 已在拦截器白名单中

**分布式锁：**`LockTemplateSupport.rLock(key, expire, timeUnit, runnable)` 基于 Redisson 的模板式分布式锁。

**全局异常处理：**`GlobalExceptionHandler` 统一拦截 `Exception`、`NotPermissionException`、`NotRoleException`，返回 `R.error(ResultCode.xxx)`。`ResultCode` 枚举定义所有错误码（1xxxx=Token, 3xxxx=登录, 4xxxx=验证码, 5xxxx=邮件, 6xxxx=服务端）。SaToken 异常通过 `ResultCode.getTokenErrorCode()` 判断是否为已知 token 错误码。

**数据层：**
- 逻辑删除（`isDelete=0/1`），`Constant` 类统一定义常量 `STATUS_DEL_INT=1` / `STATUS_NORMAL_INT=0`
- `MybatisAutoConfiguration` 配置分页插件（`MikuyunPaginationInnerInterceptor`，分页 size<0 时自动置零防全表查询）和元数据自动填充（`MybatisPlusMetaObjectHandler`）
- SQL 注入过滤：`SqlFilterArgumentResolver` 注册为参数解析器

**对象存储：**支持七牛云（`QiniuServiceImpl`）和 RustFS S3 兼容存储（`RustfsServiceImpl`），通过 `FileUploadService` 统一接口调用，`FileUploadServiceImpl` 根据渠道分发。`FileCheckUtils` 做文件校验。

**定时任务（XXL-JOB）：**`JobServiceHandler` 使用 `@XxlJob` 注解定义任务处理器，配置通过 `XxlJobConfig` 属性类管理。

**WebSocket：**`WebSocketManager` 管理连接集合（基于 satoken），支持点对点发送和全服广播。
