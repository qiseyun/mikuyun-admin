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

**认证与鉴权：**
- Sa-Token 管理登录态，`StpInterfaceImpl` 桥接自定义角色/权限服务
- 类或方法上使用 `@SaCheckRole("super_admin")` / `@SaCheckPermission` 做角色和权限控制 / `@SaIgnore` 跳过token校验
- 自定义 AOP `SecurityVerificationAspect` 配合 `@SecurityVerification` 注解，通过请求头 `access_token` 做外部调用鉴权

**消息队列（RocketMQ）：**
- `ConsumerRegister` 启动时自动发现所有 `IBaseMessageListener` 实现，按 topic 分组订阅
- `TopicMessageListenerWrapper` 根据消息 tag 二次路由到对应的 listener
- 发送端通过 `IAsyncMessageService` → `AbstractAsyncMessageServiceImpl` 模板方法发送，支持单个/批量（多 topic 广播）和延时等级
- `IBaseMessageListener.getTopic()` + `getTag()` 定义消费路由，`RocketProducer.send()` 发送

**分布式锁：**`LockTemplateSupport.rLock(key, expire, timeUnit, runnable)` 基于 Redisson 的模板式分布式锁。

**全局异常处理：**`GlobalExceptionHandler` 统一拦截 `Exception`、`NotPermissionException`、`NotRoleException`，返回 `R.error(ResultCode.xxx)`。`ResultCode` 枚举定义所有错误码（1xxxx=Token, 3xxxx=登录, 4xxxx=验证码, 5xxxx=邮件, 6xxxx=服务端）。SaToken 异常通过 `ResultCode.getTokenErrorCode()` 判断是否为已知 token 错误码。

**数据层：**逻辑删除（`isDelete=0/1`），`Constant` 类统一定义常量 `STATUS_DEL_INT=1` / `STATUS_NORMAL_INT=0` 及成功失败标记。

**对象存储：**支持 Minio 和七牛云，通过 `MinioConfiguration` / `QiniuService` 配置驱动。
