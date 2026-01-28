[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-biz-tenant**

# yudao-spring-boot-starter-biz-tenant - 多租户模块

## 模块职责

提供 SaaS 多租户支持，包括：
- 租户上下文管理
- 数据库级租户隔离
- Web 请求租户识别
- 安全认证租户集成
- 定时任务租户支持
- 消息队列租户传递
- 缓存租户隔离

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoTenantAutoConfiguration` | 租户自动配置 |
| `TenantProperties` | 租户配置属性 |

### 租户上下文

| 类名 | 说明 |
|-----|------|
| `TenantContextHolder` | 租户上下文持有者 |
| `TenantUtils` | 租户工具类 |

### 数据库隔离

| 类名 | 说明 |
|-----|------|
| `TenantDatabaseInterceptor` | 租户数据库拦截器（自动添加 tenant_id 条件） |
| `TenantBaseDO` | 租户数据对象基类（继承 BaseDO，包含 tenant_id） |

### 注解

| 注解 | 说明 |
|-----|------|
| `@TenantIgnore` | 忽略租户注解（标记后不进行租户过滤） |

### AOP

| 类名 | 说明 |
|-----|------|
| `TenantIgnoreAspect` | 忽略租户切面 |

### Web 集成

| 类名 | 说明 |
|-----|------|
| `TenantContextWebFilter` | 租户上下文 Web 过滤器 |
| `TenantSecurityWebFilter` | 租户安全过滤器 |
| `TenantVisitContextInterceptor` | 租户访问上下文拦截器 |

### 定时任务

| 类名 | 说明 |
|-----|------|
| `@TenantJob` | 租户定时任务注解 |
| `TenantJobAspect` | 租户定时任务切面 |

### 消息队列

| 类名 | 说明 |
|-----|------|
| `TenantRedisMessageInterceptor` | Redis 消息租户拦截器 |
| `TenantKafkaProducerInterceptor` | Kafka 生产者租户拦截器 |
| `TenantRabbitMQMessagePostProcessor` | RabbitMQ 消息租户处理器 |
| `TenantRocketMQSendMessageHook` | RocketMQ 发送消息租户钩子 |
| `TenantRocketMQConsumeMessageHook` | RocketMQ 消费消息租户钩子 |

### 缓存

| 类名 | 说明 |
|-----|------|
| `TenantRedisCacheManager` | 租户 Redis 缓存管理器（自动添加租户前缀） |

### 服务

| 类名 | 说明 |
|-----|------|
| `TenantFrameworkService` | 租户框架服务接口 |
| `TenantFrameworkServiceImpl` | 租户框架服务实现 |

## 配置项

```yaml
yudao:
  tenant:
    enable: true                    # 是否开启多租户
    ignore-urls:                    # 忽略租户的 URL
      - /admin-api/system/tenant/get-id-by-name
      - /admin-api/system/captcha/**
    ignore-tables:                  # 忽略租户的表
      - system_tenant
      - system_tenant_package
```

## 使用示例

### 获取当前租户

```java
// 获取当前租户 ID
Long tenantId = TenantContextHolder.getTenantId();

// 获取必须的租户 ID（不存在则抛异常）
Long tenantId = TenantContextHolder.getRequiredTenantId();
```

### 忽略租户隔离

```java
@Service
public class TenantServiceImpl implements TenantService {

    // 使用 @TenantIgnore 注解忽略租户过滤
    @TenantIgnore
    public List<TenantDO> getAllTenants() {
        // 此查询不会添加 tenant_id 条件
        return tenantMapper.selectList();
    }
}

// 或者使用 TenantUtils 工具类
public void processAllTenants() {
    TenantUtils.executeIgnore(() -> {
        // 此代码块内不进行租户过滤
        List<TenantDO> tenants = tenantMapper.selectList();
    });
}
```

### 切换租户上下文

```java
// 以指定租户身份执行
TenantUtils.execute(tenantId, () -> {
    // 此代码块内使用指定的租户 ID
    userService.createUser(reqVO);
});
```

### 租户数据对象

```java
// 继承 TenantBaseDO 自动包含 tenant_id
@TableName("system_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String username;
    // 自动包含 tenantId 字段
}
```

### 租户定时任务

```java
@Component("userSyncJob")
public class UserSyncJob implements JobHandler {

    // 使用 @TenantJob 注解，任务会遍历所有租户执行
    @TenantJob
    @Override
    public String execute(String param) throws Exception {
        // 此方法会为每个启用的租户执行一次
        log.info("当前租户: {}", TenantContextHolder.getTenantId());
        // 同步用户逻辑
        return "success";
    }
}
```

### 消息队列租户传递

租户信息会自动在消息中传递：

```java
// 发送消息时，自动携带租户 ID
redisMQTemplate.send(new UserCreateMessage().setUserId(userId));

// 消费消息时，自动恢复租户上下文
@Component
public class UserCreateMessageListener
        extends AbstractRedisStreamMessageListener<UserCreateMessage> {

    @Override
    public void onMessage(UserCreateMessage message) {
        // TenantContextHolder.getTenantId() 已自动设置
        log.info("租户 {} 创建了用户", TenantContextHolder.getTenantId());
    }
}
```

### 缓存租户隔离

使用 `TenantRedisCacheManager` 后，缓存 Key 自动添加租户前缀：

```java
// 原始缓存名: user:1
// 实际缓存名: tenant:1:user:1（假设租户 ID 为 1）

@Cacheable(value = "user", key = "#id")
public UserDO getUser(Long id) {
    return userMapper.selectById(id);
}
```

## TenantContextHolder 方法

| 方法 | 说明 |
|-----|------|
| `getTenantId()` | 获取当前租户 ID |
| `getRequiredTenantId()` | 获取必须的租户 ID（null 则抛异常） |
| `setTenantId(Long)` | 设置当前租户 ID |
| `setIgnore(Boolean)` | 设置是否忽略租户 |
| `isIgnore()` | 是否忽略租户 |
| `clear()` | 清除租户上下文 |

## TenantUtils 方法

| 方法 | 说明 |
|-----|------|
| `execute(tenantId, runnable)` | 以指定租户执行 |
| `execute(tenantId, callable)` | 以指定租户执行（带返回值） |
| `executeIgnore(runnable)` | 忽略租户执行 |
| `executeIgnore(callable)` | 忽略租户执行（带返回值） |

## 依赖说明

### 核心依赖
- yudao-common
- yudao-spring-boot-starter-security
- yudao-spring-boot-starter-mybatis
- yudao-spring-boot-starter-redis
- yudao-spring-boot-starter-job
- Guava

### 可选依赖
- yudao-spring-boot-starter-mq
- Spring Kafka
- Spring RabbitMQ
- RocketMQ Spring Boot Starter

## 相关文件清单

```
yudao-spring-boot-starter-biz-tenant/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/tenant/
    ├── config/
    │   ├── YudaoTenantAutoConfiguration.java
    │   └── TenantProperties.java
    ├── core/
    │   ├── aop/                    # AOP
    │   │   ├── TenantIgnore.java
    │   │   └── TenantIgnoreAspect.java
    │   ├── context/                # 上下文
    │   │   └── TenantContextHolder.java
    │   ├── db/                     # 数据库
    │   │   ├── TenantBaseDO.java
    │   │   └── TenantDatabaseInterceptor.java
    │   ├── job/                    # 定时任务
    │   ├── mq/                     # 消息队列
    │   │   ├── kafka/
    │   │   ├── rabbitmq/
    │   │   ├── redis/
    │   │   └── rocketmq/
    │   ├── redis/                  # 缓存
    │   ├── security/               # 安全
    │   ├── service/                # 服务
    │   ├── util/                   # 工具
    │   └── web/                    # Web
    └── package-info.java
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
