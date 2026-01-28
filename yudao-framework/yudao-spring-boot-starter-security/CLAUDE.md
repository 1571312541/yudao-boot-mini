[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-security**

# yudao-spring-boot-starter-security - 安全认证模块

## 模块职责

提供用户认证与权限校验功能，包括：
- 用户认证（Token 认证）
- 权限校验（基于 Spring Security）
- 操作日志记录
- 登录用户上下文管理

## 关键类说明

### 登录用户

| 类名 | 说明 |
|-----|------|
| `LoginUser` | 登录用户信息，包含 id、userType、tenantId、scopes、expiresTime 等 |

### 认证过滤器

| 类名 | 说明 |
|-----|------|
| `TokenAuthenticationFilter` | Token 认证过滤器，从请求中提取 Token 并验证 |

### 安全配置

| 类名 | 说明 |
|-----|------|
| `YudaoSecurityAutoConfiguration` | 安全自动配置 |
| `YudaoWebSecurityConfigurerAdapter` | Web 安全配置适配器 |
| `SecurityProperties` | 安全配置属性 |
| `AuthorizeRequestsCustomizer` | 授权请求定制器（用于模块自定义放行路径） |

### 权限服务

| 类名 | 说明 |
|-----|------|
| `SecurityFrameworkService` | 权限校验服务接口 |
| `SecurityFrameworkServiceImpl` | 权限校验服务实现 |

### 异常处理器

| 类名 | 说明 |
|-----|------|
| `AuthenticationEntryPointImpl` | 认证入口点（未认证处理） |
| `AccessDeniedHandlerImpl` | 访问拒绝处理器（无权限处理） |

### 工具类

| 类名 | 说明 |
|-----|------|
| `SecurityFrameworkUtils` | 安全框架工具类（获取当前用户等） |
| `TransmittableThreadLocalSecurityContextHolderStrategy` | 跨线程安全上下文传递 |

### 操作日志

| 类名 | 说明 |
|-----|------|
| `YudaoOperateLogConfiguration` | 操作日志配置 |
| `LogRecordServiceImpl` | 操作日志记录服务（基于 bizlog-sdk） |

## 配置项

```yaml
yudao:
  security:
    permit-all-urls:
      - /admin-api/system/auth/login
      - /admin-api/system/auth/logout
      - /admin-api/system/captcha/**
    mock-enable: false  # 是否开启 Mock 模式
    mock-secret: test   # Mock 模式密钥
```

## 使用示例

### 获取当前登录用户

```java
// 获取当前登录用户
LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();

// 获取当前用户 ID
Long userId = SecurityFrameworkUtils.getLoginUserId();

// 获取当前租户 ID
Long tenantId = SecurityFrameworkUtils.getLoginUserTenantId();
```

### 权限校验

```java
@RestController
public class UserController {

    // 基于注解的权限校验
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    @PostMapping("/create")
    public CommonResult<Long> createUser(@RequestBody UserCreateReqVO reqVO) {
        return success(userService.createUser(reqVO));
    }

    // 任意权限校验
    @PreAuthorize("@ss.hasAnyPermissions('system:user:update', 'system:user:create')")
    @PutMapping("/update")
    public CommonResult<Boolean> updateUser(@RequestBody UserUpdateReqVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    // 角色校验
    @PreAuthorize("@ss.hasRole('admin')")
    @DeleteMapping("/delete")
    public CommonResult<Boolean> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    // 任意角色校验
    @PreAuthorize("@ss.hasAnyRoles('admin', 'manager')")
    @GetMapping("/list")
    public CommonResult<List<UserRespVO>> getUsers() {
        return success(userService.getUsers());
    }
}
```

### 操作日志

```java
// 使用 @LogRecord 注解记录操作日志（基于 bizlog-sdk）
@LogRecord(
    type = "USER",
    subType = "CREATE",
    bizNo = "{{#user.id}}",
    success = "创建用户【{{#user.username}}】成功"
)
public Long createUser(UserCreateReqVO reqVO) {
    // 业务逻辑
}
```

### 自定义放行路径

在业务模块中自定义需要放行的路径：

```java
@Component
public class SystemAuthorizeRequestsCustomizer extends AuthorizeRequestsCustomizer {

    @Override
    public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        // 放行验证码接口
        registry.antMatchers("/admin-api/system/captcha/**").permitAll();
        // 放行登录接口
        registry.antMatchers("/admin-api/system/auth/login").permitAll();
    }
}
```

## LoginUser 字段说明

| 字段 | 类型 | 说明 |
|-----|------|------|
| `id` | Long | 用户编号 |
| `userType` | Integer | 用户类型（1-管理员，2-会员） |
| `tenantId` | Long | 租户编号 |
| `scopes` | List<String> | 授权范围 |
| `expiresTime` | LocalDateTime | Token 过期时间 |
| `info` | Map<String, String> | 额外信息（昵称、部门ID等） |
| `context` | Map<String, Object> | 临时上下文（不持久化） |
| `visitTenantId` | Long | 访问的租户编号（跨租户访问场景） |

## 依赖说明

### 核心依赖
- Spring Boot Starter Security
- Spring Boot Starter AOP
- yudao-spring-boot-starter-web
- bizlog-sdk（操作日志）
- Guava

## 相关文件清单

```
yudao-spring-boot-starter-security/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/
    ├── operatelog/                 # 操作日志
    │   ├── config/
    │   └── core/service/
    └── security/                   # 安全认证
        ├── config/
        │   ├── YudaoSecurityAutoConfiguration.java
        │   ├── YudaoWebSecurityConfigurerAdapter.java
        │   ├── SecurityProperties.java
        │   └── AuthorizeRequestsCustomizer.java
        └── core/
            ├── LoginUser.java
            ├── context/            # 安全上下文
            ├── filter/             # 认证过滤器
            ├── handler/            # 异常处理器
            ├── service/            # 权限服务
            └── util/               # 工具类
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
