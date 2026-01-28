[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-websocket**

# yudao-spring-boot-starter-websocket - WebSocket 模块

## 模块职责

提供 WebSocket 实时通信支持，包括：
- WebSocket 会话管理
- 多节点消息广播（支持 Redis、RocketMQ、RabbitMQ、Kafka）
- 与登录用户集成
- JSON 消息处理

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoWebSocketAutoConfiguration` | WebSocket 自动配置 |
| `WebSocketProperties` | WebSocket 配置属性 |

### 会话管理

| 类名 | 说明 |
|-----|------|
| `WebSocketSessionManager` | 会话管理器接口 |
| `WebSocketSessionManagerImpl` | 会话管理器实现 |
| `WebSocketSessionHandlerDecorator` | 会话处理器装饰器 |

### 消息处理

| 类名 | 说明 |
|-----|------|
| `JsonWebSocketMessageHandler` | JSON 消息处理器 |
| `JsonWebSocketMessage` | JSON 消息对象 |
| `WebSocketMessageListener` | 消息监听器接口 |

### 消息发送器

| 类名 | 说明 |
|-----|------|
| `WebSocketMessageSender` | 消息发送器接口 |
| `AbstractWebSocketMessageSender` | 抽象消息发送器 |
| `LocalWebSocketMessageSender` | 本地消息发送器（单节点） |
| `RedisWebSocketMessageSender` | Redis 消息发送器（多节点） |
| `RocketMQWebSocketMessageSender` | RocketMQ 消息发送器 |
| `RabbitMQWebSocketMessageSender` | RabbitMQ 消息发送器 |
| `KafkaWebSocketMessageSender` | Kafka 消息发送器 |

### 安全集成

| 类名 | 说明 |
|-----|------|
| `LoginUserHandshakeInterceptor` | 登录用户握手拦截器 |
| `WebSocketAuthorizeRequestsCustomizer` | WebSocket 路径权限配置 |

### 工具类

| 类名 | 说明 |
|-----|------|
| `WebSocketFrameworkUtils` | WebSocket 工具类 |

## 配置项

```yaml
yudao:
  websocket:
    enable: true
    path: /ws                    # WebSocket 连接路径
    sender-type: redis           # 消息发送类型：local/redis/rocketmq/rabbitmq/kafka
    allowed-origins: "*"         # 允许的跨域源
```

## 使用示例

### 创建消息监听器

```java
@Component
public class NoticeMessageListener implements WebSocketMessageListener<NoticeMessage> {

    @Override
    public void onMessage(WebSocketSession session, NoticeMessage message) {
        // 处理接收到的消息
        log.info("收到消息: {}", message);
    }

    @Override
    public String getType() {
        return "notice";  // 消息类型
    }
}
```

### 发送消息

```java
@Service
public class NoticeServiceImpl {

    @Autowired
    private WebSocketMessageSender messageSender;

    // 发送给指定用户
    public void sendToUser(Long userId, Integer userType, String message) {
        messageSender.sendObject(userType, userId, "notice",
            new NoticeMessage().setContent(message));
    }

    // 发送给指定会话
    public void sendToSession(String sessionId, String message) {
        messageSender.sendObject(sessionId, "notice",
            new NoticeMessage().setContent(message));
    }

    // 发送给所有用户
    public void sendToAll(Integer userType, String message) {
        messageSender.sendObject(userType, "notice",
            new NoticeMessage().setContent(message));
    }
}
```

### 前端连接示例

```javascript
// 建立 WebSocket 连接
const ws = new WebSocket('ws://localhost:48080/ws?token=' + accessToken);

ws.onopen = function() {
    console.log('WebSocket 连接成功');
};

ws.onmessage = function(event) {
    const message = JSON.parse(event.data);
    console.log('收到消息:', message);
    // message.type - 消息类型
    // message.content - 消息内容
};

ws.onclose = function() {
    console.log('WebSocket 连接关闭');
};

// 发送消息
ws.send(JSON.stringify({
    type: 'notice',
    content: { text: 'Hello' }
}));
```

### 多节点广播

当部署多个服务节点时，使用 Redis/RocketMQ/RabbitMQ/Kafka 作为消息中间件，可实现跨节点消息广播：

```yaml
yudao:
  websocket:
    sender-type: redis  # 使用 Redis Pub/Sub 实现多节点广播
```

## 消息格式

### 请求消息

```json
{
    "type": "notice",
    "content": {
        "title": "系统通知",
        "text": "您有新的待办事项"
    }
}
```

### 响应消息

```json
{
    "type": "notice",
    "content": {
        "title": "系统通知",
        "text": "您有新的待办事项"
    }
}
```

## 依赖说明

### 核心依赖
- Spring Boot Starter WebSocket
- yudao-spring-boot-starter-security（provided）
- yudao-spring-boot-starter-mq
- yudao-spring-boot-starter-biz-tenant（provided）

### 可选依赖
- Spring Kafka
- Spring RabbitMQ
- RocketMQ Spring Boot Starter

## 相关文件清单

```
yudao-spring-boot-starter-websocket/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/websocket/
    ├── config/
    │   ├── YudaoWebSocketAutoConfiguration.java
    │   └── WebSocketProperties.java
    └── core/
        ├── handler/                # 消息处理器
        ├── listener/               # 消息监听器
        ├── message/                # 消息对象
        ├── security/               # 安全集成
        ├── sender/                 # 消息发送器
        │   ├── local/              # 本地发送
        │   ├── redis/              # Redis 发送
        │   ├── rocketmq/           # RocketMQ 发送
        │   ├── rabbitmq/           # RabbitMQ 发送
        │   └── kafka/              # Kafka 发送
        ├── session/                # 会话管理
        └── util/                   # 工具类
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
