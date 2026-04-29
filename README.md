

# MIKUYUN-ADMIN

基于 Spring Boot 3.4.3 + Java 21 的后台管理系统，提供完整的权限管理、文件上传、数据导出等功能。

## 技术栈

| 分类 | 技术 |
|------|------|
| 核心框架 | Spring Boot 3.4.3 / Java 21 |
| 数据库 | MySQL + MyBatis-Plus |
| 缓存 | Redis (Redisson) |
| 配置中心 | Nacos |
| 认证授权 | Sa-Token |
| API文档 | SpringDoc OpenAPI (Swagger) |
| 工具库 | Hutool / EasyExcel |
| 消息队列 | RocketMQ |
| 搜索引擎 | Elasticsearch |
| 定时任务 | XXL-JOB |
| 对象存储 | Minio / 七牛云 |

## 项目结构

```
src/main/java/com/mikuyun/admin/
├── controller/          # 控制器层 (HTTP请求处理)
│   ├── SysUserController.java      # 系统用户管理
│   ├── SysRoleController.java    # 角色管理
│   ├── SysPermissionsController.java  # 权限管理
│   ├── SysLoginController.java     # 登录认证
│   ├── SysFileController.java    # 文件上传
│   ├── DictController.java      # 字典管理
│   ├── ExcelTaskController.java # Excel导出
│   └── demo/                    # 示例代码
├── service/            # 服务层 (业务逻辑)
│   ├── impl/                   # 服务实现
│   └── ISysUserService.java    # 服务接口
├── mapper/              # 数据访问层
│   ├── SysUserMapper.java       # 用户Mapper
│   └── SysRoleMapper.java      # 角色Mapper
├── entity/             # 实体类
│   ├── SysUser.java            # 用户实体
│   ├── SysRole.java          # 角色实体
│   └── SysPermissions.java   # 权限实体
├── evt/                # 请求参数对象
│   ├── LoginEvt.java         # 登录参数
│   ├── AddSysUserEvt.java   # 新增用户参数
│   └── SysUserListEvt.java   # 用户列表参数
├── vo/                 # 响应对象
│   ├── UserTokenVo.java       # Token响应
│   └── SysUserInfo.java      # 用户信息
├── common/             # 通用类
│   ├── R.java              # 统一响应
│   └── Constant.java       # 常量定义
├── exception/          # 异常处理
│   ├── BizException.java    # 业务异常
│   └── GlobalExceptionHandler.java  # 全局异常处理
├── config/             # 配置类
│   ├── RedisConfig.java    # Redis配置
│   └── WebSocketConfig.java # WebSocket配置
├── excel/              # Excel导出
│   ├── ExcelTaskManager.java    # 导出任务管理
│   └── engine/             # 导出引擎
├── rocketmq/           # 消息队列
│   ├── RocketProducer.java    # 生产者
│   └── consumer/               # 消费者
├── util/               # 工具类
│   ├── AhoCorasickAutomatonUtils.java  # 违禁词检测
│   └── TreeUtils.java           # 树形工具
└── socket/             # WebSocket
    └── WebSocketManager.java   # WebSocket管理器
```

## 功能特性

### 🔐 权限管理
- 基于 Sa-Token 的认证授权
- 角色权限管理
- 细粒度权限控制
- Session 管理

### 📁 文件管理
- Minio 对象存储
- 七牛云存储
- 文件类型校验
- 重复文件检测

### 📊 数据导出
- EasyExcel 导出
- 定时任务调度
- 导出速率限制
- 下载链接生成

### 📱 消息队列
- RocketMQ 集成
- 异步消息处理
- 消息消费注册
- 延迟消息

### 🔍 搜索引擎
- Elasticsearch 集成
- 全文检索
- 搜索分页

### 📝 其他功能
- WebSocket 实时通信
- SQL 注入过滤
- 全局跨域配置
- 统一响应封装

## 快速开始

### 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 编译打包

```bash
# 编译项目
mvn clean compile

# 打包 (跳过测试)
mvn clean package -DskipTests

# 打包并运行
mvn clean package -DskipTests && java -jar target/mikuyun-admin.jar
```

### 运行项目

```bash
# 开发模式运行
mvn spring-boot:run
```

### Docker 运行

```bash
# 构建镜像
docker build -t mikuyun-admin:latest .

# 运行容器
docker run -d -p 8091:8091 --name mikuyun-admin mikuyun-admin:latest
```

## 配置说明

### 配置文件位置
`src/main/resources/application.yml`

### 主要配置项

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| server.port | 服务端口 | 8091 |
| spring.env | 环境标识 | local |
| spring.datasource.* | 数据库配置 | - |
| spring.data.redis.* | Redis配置 | - |
| minio.* | Minio配置 | - |
| qiniu.* | 七牛云配置 | - |
| rocketmq.* | RocketMQ配置 | - |
| xxl.job.* | XXL-Job配置 | - |

### 环境切换
支持 `local` / `prod` 环境，通过 `spring.profiles.active` 指定

## API 文档

启动后访问 Swagger 文档：
- Swagger UI: `http://localhost:8091/swagger-ui.html`
- API Docs: `http://8091/v3/api-docs`

## 开发规范

### 代码分层
- **Controller**: 处理请求参数校验、调用Service、返回响应
- **Service**: 业务逻辑处理
- **Mapper**: 数据访问操作
- **Entity**: 数据库实体映射

### 命名规范
- Controller: `XxxController.java`，方法返回 `R