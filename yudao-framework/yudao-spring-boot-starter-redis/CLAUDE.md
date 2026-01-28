[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-redis**

# yudao-spring-boot-starter-redis - Redis 封装模块

## 模块职责

提供 Redis 缓存的增强封装，包括：
- RedisTemplate 配置（JSON 序列化）
- Redisson 分布式锁支持
- Spring Cache 集成
- 自定义过期时间的 CacheManager

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoRedisAutoConfiguration` | Redis 自动配置，创建 JSON 序列化的 RedisTemplate |
| `YudaoCacheAutoConfiguration` | Spring Cache 自动配置 |
| `YudaoCacheProperties` | 缓存配置属性 |

### 核心类

| 类名 | 说明 |
|-----|------|
| `TimeoutRedisCacheManager` | 支持自定义过期时间的 RedisCacheManager |

## 配置项

```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    password:
    database: 0

  cache:
    type: redis
    redis:
      time-to-live: 1h  # 默认过期时间

# Redisson 配置（可选）
redisson:
  single-server-config:
    address: redis://127.0.0.1:6379
    database: 0
```

## 使用示例

### RedisTemplate 操作

```java
@Autowired
private RedisTemplate<String, Object> redisTemplate;

// 字符串操作
redisTemplate.opsForValue().set("key", value);
Object value = redisTemplate.opsForValue().get("key");

// 设置过期时间
redisTemplate.opsForValue().set("key", value, 1, TimeUnit.HOURS);

// Hash 操作
redisTemplate.opsForHash().put("hash", "field", value);

// List 操作
redisTemplate.opsForList().rightPush("list", value);

// Set 操作
redisTemplate.opsForSet().add("set", value);

// ZSet 操作
redisTemplate.opsForZSet().add("zset", value, score);
```

### Spring Cache 注解

```java
@Service
public class UserServiceImpl implements UserService {

    // 查询时缓存
    @Cacheable(value = "user", key = "#id")
    public UserDO getUser(Long id) {
        return userMapper.selectById(id);
    }

    // 更新时刷新缓存
    @CachePut(value = "user", key = "#user.id")
    public UserDO updateUser(UserDO user) {
        userMapper.updateById(user);
        return user;
    }

    // 删除时清除缓存
    @CacheEvict(value = "user", key = "#id")
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    // 自定义过期时间（在缓存名后添加#duration）
    @Cacheable(value = "user#1h", key = "#id")  // 1小时过期
    public UserDO getUserWithTTL(Long id) {
        return userMapper.selectById(id);
    }
}
```

### Redisson 分布式锁

```java
@Autowired
private RedissonClient redissonClient;

public void doWithLock() {
    RLock lock = redissonClient.getLock("myLock");
    try {
        // 尝试加锁，最多等待10秒，锁定后30秒自动释放
        if (lock.tryLock(10, 30, TimeUnit.SECONDS)) {
            try {
                // 业务逻辑
            } finally {
                lock.unlock();
            }
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
```

### TimeoutRedisCacheManager 自定义过期时间

缓存名称格式：`cacheName#duration`

支持的时间单位：
- `s` - 秒（如 `user#30s`）
- `m` - 分钟（如 `user#30m`）
- `h` - 小时（如 `user#1h`）
- `d` - 天（如 `user#1d`）

## 依赖说明

### 核心依赖
- Redisson Spring Boot Starter
- Redisson Spring Data 27
- Spring Boot Starter Cache
- Jackson Datatype JSR310（LocalDateTime 序列化）

## 序列化说明

默认使用 JSON 序列化：
- Key：String 序列化
- Value：JSON 序列化（支持 LocalDateTime）

## 相关文件清单

```
yudao-spring-boot-starter-redis/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/redis/
    ├── config/
    │   ├── YudaoRedisAutoConfiguration.java
    │   ├── YudaoCacheAutoConfiguration.java
    │   └── YudaoCacheProperties.java
    └── core/
        └── TimeoutRedisCacheManager.java
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
