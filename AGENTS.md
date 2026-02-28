# AGENTS.md
(使用opencode的免费模型生成)

## 项目概述

本项目是一个基于 Spring Boot 3.4.3 + Java 21 的后台管理系统 (mikuyun-admin)，使用 Maven 构建。

### 技术栈

- **核心框架**: Spring Boot 3.4.3, Java 21
- **数据库**: MySQL + MyBatis-Plus
- **缓存**: Redis (Redisson)
- **配置中心**: Nacos
- **认证**: Sa-Token
- **文档**: SpringDoc OpenAPI (Swagger)
- **其他**: Hutool, EasyExcel, Elasticsearch, RocketMQ, XXL-JOB 等

### 代码结构

```
src/main/java/com/mikuyun/admin/
├── controller/     # REST API 控制器
├── service/       # 服务接口和实现 (impl/)
├── mapper/        # MyBatis Mapper 接口
├── entity/        # 实体类
├── evt/           # 请求参数对象 (Event/Request)
├── vo/            # 响应对象 (View Object)
├── common/        # 通用类 (R, ResultCode, Constant)
├── exception/     # 异常处理
├── properties/    # 配置属性类
├── util/          # 工具类
├── listener/      # 事件监听器
├── job/           # 定时任务
├── mqRocket/      # RocketMQ 相关
└── socket/        # WebSocket
```

---

## 构建与测试命令

### Maven 常用命令

```bash
# 编译项目
mvn clean compile

# 打包 (跳过测试)
mvn clean package -DskipTests

# 打包并运行
mvn clean package -DskipTests && java -jar target/mikuyun-admin-0.0.1-SNAPSHOT.jar

# 运行项目 (开发)
mvn spring-boot:run

# 运行单个测试类
mvn test -Dtest=MikuyunAdminApplicationTests

# 运行单个测试方法
mvn test -Dtest=MikuyunAdminApplicationTests#pwdDecrypt

# 运行指定测试类
mvn test -Dtest=com.mikuyun.admin.SomeTestClass

# 跳过测试
mvn clean package -DskipTests

# 仅运行测试 (不打包)
mvn test
```

### 配置文件

- 主配置: `src/main/resources/application.yml`
- 支持 profiles: `local`, `dev`, `prod`
- 使用 `@ActiveProfiles("dev")` 指定测试环境

---

## 代码风格指南

### 1. 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | UpperCamelCase | `SysUserService`, `AddSysUserEvt` |
| 方法名 | lowerCamelCase | `getSysUserList()`, `addSysUser()` |
| 变量名 | lowerCamelCase | `sysUser`, `userList` |
| 常量 | UPPER_SNAKE_CASE | `STATUS_NORMAL_INT`, `SUCCESS` |
| 包名 | lowercase | `com.mikuyun.admin.controller` |

### 2. 包结构约定

- **Evt (Event)**: 请求参数对象，放在 `evt/` 包下，按模块分子包
  - 基础请求: `IdEvt`, `LoginEvt`, `StrEvt`
  - 业务请求: `evt/sysuser/AddSysUserEvt`

- **VO (View Object)**: 响应对象，放在 `vo/` 包下
  - `SysUserInfo`, `SysUserListVo`

- **Service**: 接口和实现分离
  - 接口: `ISysConfigService`
  - 实现: `SysConfigServiceImpl` (放在 impl 子包)

### 3. Controller 规范

```java
@Tag(name = "系统参数配置管理")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/sysConfig")
public class SysConfigController {

    private final ISysConfigService sysConfigService;

    @GetMapping(value = "/list")
    @Operation(summary = "系统参数配置列表")
    public R<List<SysConfigListVo>> list(SysConfigListEvt evt) {
        return R.ok(sysConfigService.getSysConfigList(evt));
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/add")
    @Operation(summary = "新增系统参数配置")
    public R<Void> addSysConfig(@RequestBody @Valid AddSysConfigEvt evt) {
        sysConfigService.addSysConfig(evt);
        return R.ok();
    }
}
```

**要点**:
- 使用 `@Tag` 和 `@Operation` 标注接口文档
- 使用 `@RequiredArgsConstructor` 注入依赖
- 使用 `@Valid` 进行参数校验
- 方法返回 `R<T>` 统一响应
- 路由使用 RESTful 风格

### 4. Service 规范

```java
public interface SysUserService extends IService<SysUser> {
    UserTokenVo sysAdminLogin(LoginEvt evt);
    SysUserInfo getSysUserInfo(Object sysUserId);
    List<SysUserListVo> getSysUserList(SysUserListEvt evt);
}
```

```java
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final WebConfigProperties webConfigProperties;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserTokenVo sysAdminLogin(LoginEvt evt) {
        // 业务逻辑
    }
}
```

**要点**:
- 接口继承 IService<T>
- 实现类继承 ServiceImpl<M, T>
- 使用 `@Slf4j` 打印日志
- 使用 `@RequiredArgsConstructor` 注入依赖

### 5. Entity 规范

```java
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_sys_user")
@Schema(description = "用户表")
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity {

    @Schema(name = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "用户名")
    private String username;
}
```

**要点**:
- 使用 Lombok 注解
- 使用 `@TableName` 指定表名
- 使用 `@Schema` 标注字段描述
- 继承 `BaseEntity` 获取公共字段

### 6. 请求参数 (Evt) 规范

```java
@Data
public class AddSysUserEvt {

    @Schema(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(name = "用户手机号")
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @Schema(name = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    private String password;
}
```

**要点**:
- 使用 `@Schema` 标注字段文档
- 使用 `jakarta.validation.constraints` 进行校验
- 常用注解: `@NotBlank`, `@NotNull`, `@Pattern`, `@Email`

### 7. 统一响应 (R) 规范

```java
// 成功响应
R.ok()                              // 无数据
R.ok(data)                          // 带数据
R.ok(data, "自定义消息")             // 带数据和消息

// 失败响应
R.failed()                         // 默认失败
R.failed("错误消息")                // 自定义错误消息
R.error(code, "错误消息")           // 指定错误码和消息
R.error(ResultCode.XXX)             // 使用预定义错误码
```

### 8. 异常处理

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public R<Void> handlerException(Exception e) {
        log.error("全局异常拦截: {}", e.getMessage(), e);
        if (e instanceof SaTokenException) {
            return R.error(((SaTokenException) e).getCode(), e.getMessage());
        }
        return R.error(ResultCode.SYSTEM_ERROR);
    }
}
```

### 9. 错误码 (ResultCode)

错误码定义在 `ResultCode` 枚举类中，包含:
- `SUCCESS`: 成功
- `SYSTEM_ERROR`: 系统错误
- `NOT_FOUND`: 资源不存在
- `LOGIN_ERROR`: 登录错误
- `DATA_NOT_EXIST`: 数据不存在

### 10. 导入顺序 (IDEA/Eclipse)

1. `java.*` / `javax.*`
2. `org.*`
3. `com.*`
4. 当前项目包

**按字母排序，同级别按字母顺序**

### 11. 日志规范

```java
// 记录操作
log.info("新增管理员; id: {}", sysUser.getId());

// 记录修改前后数据
log.info("\n修改后台用户信息: \n before:{} \n after:{}", beforeData, afterData);

// 记录错误
log.error("全局异常拦截: {}", e.getMessage(), e);
```

### 12. 数据库字段命名

- 表名: `mk_xxx` (mk = mikuyun)
- 字段名: 下划线命名 `user_name`, `gmt_create`
- 实体字段: 驼峰命名 `userName`, `gmtCreate`

### 13. 测试规范

```java
@ActiveProfiles(value = "dev")
@EnableConfigurationProperties
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MikuyunAdminApplicationTests {

    @Test
    public void pwdDecrypt() {
        // 测试代码
    }
}
```

**要点**:
- 使用 `@ActiveProfiles` 指定测试环境
- 使用 `@SpringBootTest` 启动测试上下文
- 测试类放在 `src/test/java/` 目录

---

## 开发注意事项

1. **MapperScan**: 主类已配置 `@MapperScan("com.mikuyun.admin.mapper")`
2. **认证**: 使用 Sa-Token，接口需登录的用 `@SaCheckLogin` 或 `@SaCheckRole`
3. **参数校验**: Controller 方法参数需加 `@Valid`
4. **统一 返回响应**: 所有 Controller `R<T>` 类型
5. **事务**: Service 方法需事务的加 `@Transactional`

---

## 推荐工具

- IDE: IntelliJ IDEA (推荐)
- API 测试: Postman / Apifox
- 数据库: Navicat / DataGrip
