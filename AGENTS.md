# Repository Guidelines

mikuyun-admin 是基于 Spring Boot 3.4.3 + Java 21 的后台管理系统。本文档是贡献者指南，帮助你在修改代码时保持一致。

## 项目结构与模块组织

源码位于 `src/main/java/com/mikuyun/admin/`，按职责分包：

- `controller/` — REST API，返回统一响应 `R<T>`，用 `@Tag`/`@Operation` 标注接口文档
- `service/` — 业务逻辑，接口继承 `IService<T>`，实现类继承 `ServiceImpl<M, T>`
- `mapper/` + `src/main/resources/mapper/` — MyBatis-Plus Mapper 与对应 XML
- `entity/` — 数据实体，继承 `BaseEntity`，表名前缀 `mk_`
- `dto/` / `vo/` — 请求/响应对象，按业务模块分子包
- `config/`、`common/`、`util/`、`enums/`、`exception/` — 配置、通用类与工具

文档与数据库脚本位于 `01_docs/`（含 Nacos、MySQL、ES、RocketMQ 的 docker-compose 示例）。测试代码应放在 `src/test/java/com/mikuyun/admin/`。

## 构建、测试与开发命令

```bash
mvn clean compile                # 编译
mvn clean package -DskipTests    # 打包（跳过测试）
mvn spring-boot:run              # 本地开发运行
mvn test -Dtest=ClassName        # 运行指定测试类
docker build -t mikuyun-admin:latest .  # 构建镜像
./deploy.sh                      # Docker 一键部署（需先配置 .env）
./start_jar.sh start|stop|restart|status  # 直接启停 Java 服务
```

配置由 Nacos 管理（`local`/`prod` profile），本地示例见 `01_docs/本地配置文件.yml`。

## 编码规范与命名

- 缩进 4 空格；类名 `UpperCamelCase`，方法/变量 `lowerCamelCase`，常量 `UPPER_SNAKE_CASE`
- 控制器统一返回 `R<T>`，异常交给 `GlobalExceptionHandler`；新接口用 `@Tag`/`@Operation` 补充文档
- 依赖注入优先使用 Lombok `@RequiredArgsConstructor`
- 实体继承 `BaseEntity`，数据库表名前缀 `mk_`；DTO 用 `jakarta.validation` 注解校验
- 权限控制使用 `@SaCheckRole`/`@SaCheckPermission`，需放行的接口加 `@TokenIgnore`

## 测试指南

当前仓库暂无测试代码，但已引入 `spring-boot-starter-test`。新增测试放在 `src/test/java/com/mikuyun/admin/`，类名以 `Test` 结尾（如 `SysUserServiceTest`），方法名描述被测行为。运行 `mvn test` 执行全部测试，`mvn test -Dtest=ClassName#methodName` 执行单个方法。

## 提交与 Pull Request 规范

提交信息遵循 Conventional Commits，使用中文描述，scope 标明影响模块：

```
feat(core): 新增字典管理接口功能
fix(service): 重构文件上传服务
docs(core): 更新说明文档
```

常用 scope：`core`、`config`、`service`。提交前检查 `git diff` 确保无调试残留；PR 需包含改动说明、关联 issue（如有）、影响面与验证结果，涉及界面变更时附截图。
