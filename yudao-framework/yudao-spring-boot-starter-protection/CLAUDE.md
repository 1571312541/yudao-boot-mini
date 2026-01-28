[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-protection**

# yudao-spring-boot-starter-protection - 服务保障模块

## 模块职责

提供服务保障功能，包括：
- 分布式锁（Lock4j + Redisson）
- 幂等性控制
- 限流（基于 Redis）
- API 签名验证

## 关键类说明

### 分布式锁

| 类名 | 说明 |
|-----|------|
| `YudaoLock4jConfiguration` | 分布式锁配置 |
| `DefaultLockFailureStrategy` | 默认加锁失败策略 |
| `Lock4jRedisKeyConstants` | Redis Key 常量 |

### 幂等性控制

| 类名 | 说明 |
|-----|------|
| `YudaoIdempotentConfiguration` | 幂等配置 |
| `@Idempotent` | 幂等注解 |
| `IdempotentAspect` | 幂等切面 |
| `IdempotentRedisDAO` | 幂等 Redis DAO |
| `IdempotentKeyResolver` | 幂等 Key 解析器接口 |
| `DefaultIdempotentKeyResolver` | 默认 Key 解析器 |
| `UserIdempotentKeyResolver` | 用户维度 Key 解析器 |
| `ExpressionIdempotentKeyResolver` | 表达式 Key 解析器 |

### 限流

| 类名 | 说明 |
|-----|------|
| `YudaoRateLimiterConfiguration` | 限流配置 |
| `@RateLimiter` | 限流注解 |
| `RateLimiterAspect` | 限流切面 |
| `RateLimiterRedisDAO` | 限流 Redis DAO |
| `RateLimiterKeyResolver` | 限流 Key 解析器接口 |
| `DefaultRateLimiterKeyResolver` | 默认 Key 解析器 |
| `UserRateLimiterKeyResolver` | 用户维度 Key 解析器 |
| `ClientIpRateLimiterKeyResolver` | IP 维度 Key 解析器 |
| `ServerNodeRateLimiterKeyResolver` | 服务节点 Key 解析器 |
| `ExpressionRateLimiterKeyResolver` | 表达式 Key 解析器 |

### API 签名验证

| 类名 | 说明 |
|-----|------|
| `YudaoApiSignatureAutoConfiguration` | API 签名自动配置 |
| `@ApiSignature` | API 签名注解 |
| `ApiSignatureAspect` | API 签名切面 |
| `ApiSignatureRedisDAO` | 签名 Redis DAO |

## 配置项

```yaml
# Lock4j 分布式锁配置
lock4j:
  acquire-timeout: 3000   # 获取锁超时时间（毫秒）
  expire: 30000           # 锁过期时间（毫秒）
```

## 使用示例

### 分布式锁

```java
import com.baomidou.lock.annotation.Lock4j;

@Service
public class OrderServiceImpl implements OrderService {

    // 使用 @Lock4j 注解加锁
    @Lock4j(keys = "#orderId", expire = 60000, acquireTimeout = 10000)
    public void processOrder(Long orderId) {
        // 该方法同一时间只会有一个线程执行（基于 orderId）
    }

    // 多个 key 组合
    @Lock4j(keys = {"#userId", "#productId"})
    public void createOrder(Long userId, Long productId) {
        // 基于 userId + productId 组合加锁
    }
}
```

### 幂等性控制

```java
@RestController
public class OrderController {

    // 基于请求参数的幂等
    @PostMapping("/create")
    @Idempotent(timeout = 10)  // 10秒内重复请求会被拦截
    public CommonResult<Long> createOrder(@RequestBody OrderCreateReqVO reqVO) {
        return success(orderService.createOrder(reqVO));
    }

    // 基于用户的幂等
    @PostMapping("/pay")
    @Idempotent(timeout = 60, keyResolver = UserIdempotentKeyResolver.class)
    public CommonResult<Boolean> payOrder(@RequestBody OrderPayReqVO reqVO) {
        return success(orderService.payOrder(reqVO));
    }

    // 基于表达式的幂等
    @PostMapping("/submit")
    @Idempotent(
        timeout = 30,
        keyResolver = ExpressionIdempotentKeyResolver.class,
        keyArg = "#reqVO.orderNo"  // 基于订单号幂等
    )
    public CommonResult<Boolean> submitOrder(@RequestBody OrderSubmitReqVO reqVO) {
        return success(orderService.submitOrder(reqVO));
    }

    // 自定义错误提示
    @PostMapping("/confirm")
    @Idempotent(timeout = 10, message = "订单正在处理中，请勿重复提交")
    public CommonResult<Boolean> confirmOrder(@RequestBody OrderConfirmReqVO reqVO) {
        return success(orderService.confirmOrder(reqVO));
    }
}
```

### 限流

```java
@RestController
public class SmsController {

    // 基于方法的限流（每分钟最多100次）
    @PostMapping("/send")
    @RateLimiter(count = 100, time = 60)
    public CommonResult<Boolean> sendSms(@RequestBody SmsSendReqVO reqVO) {
        return success(smsService.sendSms(reqVO));
    }

    // 基于用户的限流（每用户每分钟最多5次）
    @PostMapping("/sendVerifyCode")
    @RateLimiter(
        count = 5,
        time = 60,
        keyResolver = UserRateLimiterKeyResolver.class
    )
    public CommonResult<Boolean> sendVerifyCode(@RequestBody VerifyCodeReqVO reqVO) {
        return success(smsService.sendVerifyCode(reqVO));
    }

    // 基于 IP 的限流
    @PostMapping("/login")
    @RateLimiter(
        count = 10,
        time = 60,
        keyResolver = ClientIpRateLimiterKeyResolver.class,
        message = "登录尝试次数过多，请稍后再试"
    )
    public CommonResult<LoginRespVO> login(@RequestBody LoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    // 基于表达式的限流
    @PostMapping("/sendTo")
    @RateLimiter(
        count = 3,
        time = 60,
        keyResolver = ExpressionRateLimiterKeyResolver.class,
        keyArg = "#reqVO.mobile"  // 基于手机号限流
    )
    public CommonResult<Boolean> sendToMobile(@RequestBody SmsSendReqVO reqVO) {
        return success(smsService.sendSms(reqVO));
    }
}
```

### API 签名验证

```java
@RestController
public class OpenApiController {

    // 启用 API 签名验证
    @PostMapping("/callback")
    @ApiSignature(appId = "wxpay", timeout = 300)
    public CommonResult<Boolean> callback(@RequestBody PayNotifyReqVO reqVO) {
        // 签名验证通过才会执行
        return success(payService.handleCallback(reqVO));
    }
}
```

## 注解参数说明

### @Idempotent

| 参数 | 类型 | 默认值 | 说明 |
|-----|------|--------|------|
| `timeout` | int | 1 | 幂等时间窗口（秒） |
| `timeUnit` | TimeUnit | SECONDS | 时间单位 |
| `message` | String | "重复请求，请稍后重试" | 错误提示 |
| `keyResolver` | Class | DefaultIdempotentKeyResolver | Key 解析器 |
| `keyArg` | String | "" | Key 表达式（配合 ExpressionIdempotentKeyResolver） |

### @RateLimiter

| 参数 | 类型 | 默认值 | 说明 |
|-----|------|--------|------|
| `count` | int | 100 | 时间窗口内最大请求数 |
| `time` | int | 60 | 时间窗口（秒） |
| `message` | String | "请求过于频繁，请稍后重试" | 错误提示 |
| `keyResolver` | Class | DefaultRateLimiterKeyResolver | Key 解析器 |
| `keyArg` | String | "" | Key 表达式 |

### @Lock4j

| 参数 | 类型 | 默认值 | 说明 |
|-----|------|--------|------|
| `keys` | String[] | {} | 锁 Key（支持 SpEL） |
| `expire` | long | 30000 | 锁过期时间（毫秒） |
| `acquireTimeout` | long | 3000 | 获取锁超时时间（毫秒） |

## 依赖说明

### 核心依赖
- yudao-spring-boot-starter-redis
- yudao-spring-boot-starter-web（provided）
- Lock4j Redisson（可选）

## 相关文件清单

```
yudao-spring-boot-starter-protection/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/
    ├── idempotent/                 # 幂等
    │   ├── config/
    │   └── core/
    │       ├── annotation/
    │       ├── aop/
    │       ├── keyresolver/
    │       └── redis/
    ├── lock4j/                     # 分布式锁
    │   ├── config/
    │   └── core/
    ├── ratelimiter/                # 限流
    │   ├── config/
    │   └── core/
    │       ├── annotation/
    │       ├── aop/
    │       ├── keyresolver/
    │       └── redis/
    └── signature/                  # API 签名
        ├── config/
        └── core/
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
