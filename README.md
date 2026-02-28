# mikuyun-admin
(使用opencode的免费模型生成和编辑)

基于 Spring Boot 3.4.3 + Java 21 的后台管理系统。

## 技术栈

- Spring Boot 3.4.3 / Java 21
- MySQL + MyBatis-Plus
- Redis (Redisson)
- Nacos 配置中心
- Sa-Token 认证
- SpringDoc OpenAPI (Swagger)
- Hutool / EasyExcel / Elasticsearch / RocketMQ / XXL-JOB

## 快速开始

```bash
# 编译
mvn clean compile

# 打包
mvn clean package -DskipTests

# 运行
mvn spring-boot:run
```

## 项目结构

```
src/main/java/com/mikuyun/admin/
├── controller/     # 控制器
├── service/       # 服务层
├── mapper/        # 数据访问层
├── entity/        # 实体类
├── evt/           # 请求参数
├── vo/            # 响应对象
├── common/        # 通用类
└── exception/     # 异常处理
```

## 配置

- 配置文件: `src/main/resources/application.yml` (可复制本地配置文件 `01_docs/本地配置文件.yml`)
- 环境 profile: `local` / `prod`
- 默认端口: 8091

## API 文档

启动后访问: `http://localhost:8080/swagger-ui.html`

## License

MIT
