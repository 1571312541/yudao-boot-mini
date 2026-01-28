[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-mq**

# yudao-spring-boot-starter-mq - 消息队列模块

## 模块职责

提供消息队列封装，支持多种 MQ 实现：
- Redis Stream（默认）
- Redis Pub/Sub
- RocketMQ
- RabbitMQ
- Kafka

## 关键类说明

### Redis MQ（默认）

| 类名 | 说明 |
|-----|------|
| `YudaoRedisMQProducerAutoConfiguration` | Redis MQ 生产者自动配置 |
| `YudaoRedisMQConsumerAutoConfiguration` | Redis MQ 消费者自动配置 |
| `RedisMQTemplate` | Redis MQ 模板 |
| `RedisMessageInterceptor` | 消息拦截器接口 |

### Redis Stream 消息

| 类名 | 说明 |
|-----|------|
| `AbstractRedisStreamMessage` | Redis Stream 消息基类 |
| `AbstractRedisStreamMessageListener` | Redis Stream 消息监听器基类 |

### Redis Pub/Sub 消息

| 类名 | 说明 |
|-----|------|
| `AbstractRedisChannelMessage` | Redis Channel 消息基类 |
| `AbstractRedisChannelMessageListener` | Redis Channel 消息监听器基类 |

### 消息清理任务

| 类名 | 说明 |
|-----|------|
| `RedisPendingMessageResendJob` | 待处理消息重发任务 |
| `RedisStreamMessageCleanupJob` | Stream 消息清理任务 |

### RabbitMQ

| 类名 | 说明 |
|-----|------|
| `YudaoRabbitMQAutoConfiguration` | RabbitMQ 自动配置 |

## 配置项

```yaml
# Redis MQ（默认使用 Redis Stream）
spring:
  redis:
    host: 127.0.0.1
    port: 6379

# RabbitMQ（可选）
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

# RocketMQ（可选）
rocketmq:
  name-server: localhost:9876
  producer:
    group: ${spring.application.name}

# Kafka（可选）
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: ${spring.application.name}
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
```

## 使用示例

### Redis Stream 消息（推荐）

**1. 定义消息类**

```java
@Data
public class UserCreateMessage extends AbstractRedisStreamMessage {

    private Long userId;
    private String username;

    @Override
    public String getStreamKey() {
        return "user:create";  // Stream Key
    }
}
```

**2. 创建消息监听器**

```java
@Component
public class UserCreateMessageListener
        extends AbstractRedisStreamMessageListener<UserCreateMessage> {

    @Override
    public void onMessage(UserCreateMessage message) {
        log.info("收到用户创建消息: userId={}, username={}",
            message.getUserId(), message.getUsername());
        // 处理消息
    }
}
```

**3. 发送消息**

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisMQTemplate redisMQTemplate;

    public void createUser(UserCreateReqVO reqVO) {
        // 创建用户逻辑...

        // 发送消息
        redisMQTemplate.send(new UserCreateMessage()
            .setUserId(user.getId())
            .setUsername(user.getUsername()));
    }
}
```

### Redis Pub/Sub 消息（广播）

**1. 定义消息类**

```java
@Data
public class CacheRefreshMessage extends AbstractRedisChannelMessage {

    private String cacheKey;

    @Override
    public String getChannel() {
        return "cache:refresh";  // Channel 名称
    }
}
```

**2. 创建消息监听器**

```java
@Component
public class CacheRefreshMessageListener
        extends AbstractRedisChannelMessageListener<CacheRefreshMessage> {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void onMessage(CacheRefreshMessage message) {
        log.info("收到缓存刷新消息: cacheKey={}", message.getCacheKey());
        // 刷新本地缓存
        cacheManager.getCache(message.getCacheKey()).clear();
    }
}
```

**3. 发送广播消息**

```java
@Service
public class CacheServiceImpl {

    @Autowired
    private RedisMQTemplate redisMQTemplate;

    public void refreshCache(String cacheKey) {
        // 发送广播消息（所有订阅者都会收到）
        redisMQTemplate.send(new CacheRefreshMessage().setCacheKey(cacheKey));
    }
}
```

### RabbitMQ 使用

```java
// 发送消息
@Autowired
private RabbitTemplate rabbitTemplate;

public void sendMessage() {
    rabbitTemplate.convertAndSend("exchange", "routingKey", message);
}

// 接收消息
@RabbitListener(queues = "queueName")
public void handleMessage(Message message) {
    // 处理消息
}
```

### RocketMQ 使用

```java
// 发送消息
@Autowired
private RocketMQTemplate rocketMQTemplate;

public void sendMessage() {
    rocketMQTemplate.convertAndSend("topic", message);
}

// 接收消息
@RocketMQMessageListener(topic = "topic", consumerGroup = "group")
@Component
public class MyConsumer implements RocketMQListener<Message> {
    @Override
    public void onMessage(Message message) {
        // 处理消息
    }
}
```

### Kafka 使用

```java
// 发送消息
@Autowired
private KafkaTemplate<String, Object> kafkaTemplate;

public void sendMessage() {
    kafkaTemplate.send("topic", message);
}

// 接收消息
@KafkaListener(topics = "topic")
public void handleMessage(Message message) {
    // 处理消息
}
```

## Redis Stream vs Pub/Sub

| 特性 | Stream | Pub/Sub |
|-----|--------|---------|
| 消息持久化 | 是 | 否 |
| 消费者组 | 支持 | 不支持 |
| 消息确认 | 支持 | 不支持 |
| 适用场景 | 可靠消息传递 | 广播通知 |

## 消息拦截器

```java
@Component
public class TenantMessageInterceptor implements RedisMessageInterceptor {

    @Override
    public void sendMessageBefore(AbstractRedisMessage message) {
        // 发送前设置租户 ID
        Long tenantId = TenantContextHolder.getTenantId();
        message.addHeader("tenant-id", tenantId.toString());
    }

    @Override
    public void consumeMessageBefore(AbstractRedisMessage message) {
        // 消费前恢复租户 ID
        String tenantId = message.getHeader("tenant-id");
        TenantContextHolder.setTenantId(Long.valueOf(tenantId));
    }

    @Override
    public void consumeMessageAfter(AbstractRedisMessage message) {
        // 消费后清理租户上下文
        TenantContextHolder.clear();
    }
}
```

## 依赖说明

### 核心依赖
- yudao-spring-boot-starter-redis

### 可选依赖
- Spring Kafka
- Spring RabbitMQ
- RocketMQ Spring Boot Starter

## 相关文件清单

```
yudao-spring-boot-starter-mq/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/mq/
    ├── rabbitmq/                   # RabbitMQ
    │   └── config/
    └── redis/                      # Redis MQ
        ├── config/
        │   ├── YudaoRedisMQProducerAutoConfiguration.java
        │   └── YudaoRedisMQConsumerAutoConfiguration.java
        └── core/
            ├── RedisMQTemplate.java
            ├── interceptor/
            ├── job/
            ├── message/
            ├── pubsub/             # Pub/Sub
            └── stream/             # Stream
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
