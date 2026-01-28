[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-web**

# yudao-spring-boot-starter-web - Web 框架模块

## 模块职责

提供 Web 层的通用配置和功能封装，包括：
- 全局异常处理
- 统一响应封装
- API 访问日志
- 数据脱敏
- API 加密
- XSS 防护
- Swagger/Knife4j 文档
- Jackson 序列化配置
- Banner 输出

## 关键类说明

### 全局处理器

| 类名 | 说明 |
|-----|------|
| `GlobalExceptionHandler` | 全局异常处理器，统一处理各类异常 |
| `GlobalResponseBodyHandler` | 全局响应体处理器，统一封装响应 |

### API 日志

| 类名 | 说明 |
|-----|------|
| `ApiAccessLogFilter` | API 访问日志过滤器 |
| `ApiAccessLogInterceptor` | API 访问日志拦截器 |
| `@ApiAccessLog` | API 日志注解 |
| `OperateTypeEnum` | 操作类型枚举（GET/CREATE/UPDATE/DELETE/EXPORT/IMPORT） |

### 数据脱敏

| 注解 | 说明 |
|-----|------|
| `@DesensitizeBy` | 自定义脱敏注解 |
| `@MobileDesensitize` | 手机号脱敏（138****8888） |
| `@EmailDesensitize` | 邮箱脱敏 |
| `@IdCardDesensitize` | 身份证脱敏 |
| `@BankCardDesensitize` | 银行卡脱敏 |
| `@ChineseNameDesensitize` | 中文姓名脱敏 |
| `@PasswordDesensitize` | 密码脱敏（******） |
| `@CarLicenseDesensitize` | 车牌号脱敏 |
| `@FixedPhoneDesensitize` | 固定电话脱敏 |
| `@SliderDesensitize` | 滑动脱敏（自定义保留位数） |
| `@RegexDesensitize` | 正则脱敏 |

### API 加密

| 类名 | 说明 |
|-----|------|
| `@ApiEncrypt` | API 加密注解 |
| `ApiEncryptFilter` | API 加密过滤器 |
| `ApiEncryptProperties` | 加密配置属性 |

### XSS 防护

| 类名 | 说明 |
|-----|------|
| `XssFilter` | XSS 过滤器 |
| `XssCleaner` | XSS 清理接口 |
| `JsoupXssCleaner` | 基于 Jsoup 的 XSS 清理实现 |
| `XssStringJsonDeserializer` | JSON 反序列化时清理 XSS |
| `XssProperties` | XSS 配置属性 |

### Swagger 配置

| 类名 | 说明 |
|-----|------|
| `YudaoSwaggerAutoConfiguration` | Swagger 自动配置 |
| `SwaggerProperties` | Swagger 配置属性 |
| `Knife4jOpenApiCustomizer` | Knife4j 定制器 |

### Web 配置

| 类名 | 说明 |
|-----|------|
| `YudaoWebAutoConfiguration` | Web 自动配置 |
| `WebProperties` | Web 配置属性 |
| `WebFrameworkUtils` | Web 框架工具类 |

### 过滤器

| 类名 | 说明 |
|-----|------|
| `ApiRequestFilter` | API 请求基础过滤器 |
| `CacheRequestBodyFilter` | 请求体缓存过滤器（支持多次读取） |
| `DemoFilter` | 演示模式过滤器 |

### Jackson 配置

| 类名 | 说明 |
|-----|------|
| `YudaoJacksonAutoConfiguration` | Jackson 自动配置 |

### Banner

| 类名 | 说明 |
|-----|------|
| `BannerApplicationRunner` | 启动 Banner 输出 |

## 配置项

```yaml
yudao:
  web:
    admin-api:
      prefix: /admin-api    # 管理后台 API 前缀
    app-api:
      prefix: /app-api      # 用户端 API 前缀

  swagger:
    title: 管理后台
    description: 提供管理员的 RESTful API
    version: ${yudao.info.version}
    base-package: cn.iocoder.yudao.module

  xss:
    enable: true
    exclude-urls:
      - /admin-api/system/notice/*

  api-encrypt:
    enable: false
    aes-key: xxx  # AES 密钥
    rsa-private-key: xxx  # RSA 私钥
```

## 使用示例

### API 日志注解

```java
@PostMapping("/create")
@Operation(summary = "创建用户")
@ApiAccessLog(operateType = OperateTypeEnum.CREATE)
public CommonResult<Long> createUser(@RequestBody UserCreateReqVO reqVO) {
    return success(userService.createUser(reqVO));
}
```

### 数据脱敏

```java
@Data
public class UserRespVO {

    @MobileDesensitize
    private String mobile;  // 输出: 138****8888

    @EmailDesensitize
    private String email;   // 输出: t***@example.com

    @IdCardDesensitize
    private String idCard;  // 输出: 110***********1234

    @ChineseNameDesensitize
    private String name;    // 输出: 张*

    @PasswordDesensitize
    private String password; // 输出: ******

    @SliderDesensitize(prefixKeep = 3, suffixKeep = 4)
    private String custom;  // 自定义保留前3后4位
}
```

### API 加密

```java
@PostMapping("/login")
@ApiEncrypt  // 启用请求/响应加密
public CommonResult<LoginRespVO> login(@RequestBody LoginReqVO reqVO) {
    return success(authService.login(reqVO));
}
```

### XSS 防护

XSS 过滤默认启用，会自动清理请求参数中的恶意脚本。

可通过配置排除特定 URL：
```yaml
yudao:
  xss:
    exclude-urls:
      - /admin-api/system/notice/*  # 富文本内容
```

## 依赖说明

### 核心依赖
- Spring Boot Starter Web
- Knife4j OpenAPI3（API 文档）
- SpringDoc OpenAPI UI
- Jsoup（XSS 清理）
- Spring Security Core（provided，异常处理使用）

## 相关文件清单

```
yudao-spring-boot-starter-web/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/
    ├── apilog/                 # API 日志
    │   ├── config/
    │   └── core/
    ├── banner/                 # Banner
    ├── desensitize/            # 数据脱敏
    │   └── core/
    │       ├── base/           # 基础注解和处理器
    │       ├── regex/          # 正则脱敏
    │       └── slider/         # 滑动脱敏
    ├── encrypt/                # API 加密
    │   ├── config/
    │   └── core/
    ├── jackson/                # Jackson 配置
    ├── swagger/                # Swagger 配置
    ├── web/                    # Web 核心配置
    │   ├── config/
    │   └── core/
    │       ├── filter/
    │       ├── handler/
    │       └── util/
    └── xss/                    # XSS 防护
        ├── config/
        └── core/
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
