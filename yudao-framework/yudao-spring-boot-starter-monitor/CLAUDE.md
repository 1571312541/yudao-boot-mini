[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-monitor**

# yudao-spring-boot-starter-monitor - 监控追踪模块

## 模块职责

提供服务监控与链路追踪功能，包括：
- 链路追踪（SkyWalking 集成）
- 业务追踪注解
- 指标收集（Micrometer + Prometheus）
- Spring Boot Admin 客户端

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoTracerAutoConfiguration` | 链路追踪自动配置 |
| `YudaoMetricsAutoConfiguration` | 指标收集自动配置 |
| `TracerProperties` | 追踪配置属性 |

### 链路追踪

| 类名 | 说明 |
|-----|------|
| `@BizTrace` | 业务追踪注解 |
| `BizTraceAspect` | 业务追踪切面 |
| `TraceFilter` | 追踪过滤器 |
| `TracerFrameworkUtils` | 追踪工具类 |

## 配置项

```yaml
# SkyWalking 配置（通过 Agent 启动参数配置）
# -javaagent:/path/to/skywalking-agent.jar
# -Dskywalking.agent.service_name=yudao-server
# -Dskywalking.collector.backend_service=localhost:11800

# Prometheus 指标端点
management:
  endpoints:
    web:
      exposure:
        include: prometheus,health,info
  metrics:
    export:
      prometheus:
        enabled: true

# Spring Boot Admin 客户端
spring:
  boot:
    admin:
      client:
        url: http://localhost:9090
        instance:
          service-url: http://localhost:48080
```

## 使用示例

### 业务追踪注解

```java
@Service
public class OrderServiceImpl implements OrderService {

    // 使用 @BizTrace 注解标记需要追踪的业务方法
    @BizTrace(id = "#orderId", type = "ORDER_CREATE")
    public Long createOrder(Long orderId, OrderCreateReqVO reqVO) {
        // 业务逻辑
        // 该方法的调用会被记录到链路追踪中
        return orderId;
    }

    @BizTrace(id = "#order.id", type = "ORDER_PAY")
    public void payOrder(OrderDO order) {
        // 支付逻辑
    }
}
```

### 获取 Trace ID

```java
// 获取当前请求的 Trace ID
String traceId = TracerFrameworkUtils.getTraceId();

// 在日志中输出 Trace ID
log.info("处理订单, traceId: {}", traceId);
```

### 自定义 Span

```java
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

public void processOrder() {
    // 添加自定义标签
    ActiveSpan.tag("orderId", orderId.toString());
    ActiveSpan.tag("orderType", orderType);

    // 记录错误
    try {
        // 业务逻辑
    } catch (Exception e) {
        ActiveSpan.error(e);
        throw e;
    }
}
```

### Prometheus 指标

访问 `/actuator/prometheus` 端点获取指标数据：

```
# HELP jvm_memory_used_bytes The amount of used memory
# TYPE jvm_memory_used_bytes gauge
jvm_memory_used_bytes{area="heap",id="PS Eden Space",} 1.2345678E8

# HELP http_server_requests_seconds
# TYPE http_server_requests_seconds summary
http_server_requests_seconds_count{method="GET",status="200",uri="/api/users",} 100.0
http_server_requests_seconds_sum{method="GET",status="200",uri="/api/users",} 1.5
```

### Spring Boot Admin

配置后，服务会自动注册到 Spring Boot Admin Server，提供：
- 应用健康状态监控
- JVM 指标监控
- 日志级别动态调整
- 环境变量查看

## @BizTrace 注解说明

| 属性 | 类型 | 说明 |
|-----|------|------|
| `id` | String | 业务 ID（支持 SpEL 表达式） |
| `type` | String | 业务类型 |

## 依赖说明

### 核心依赖
- Spring Boot Starter AOP
- OpenTracing Util（可选）
- SkyWalking APM Toolkit（可选）
- Micrometer Registry Prometheus（可选）
- Spring Boot Admin Client（可选）

## 与 SkyWalking 集成

1. 下载 SkyWalking Agent
2. 启动应用时添加 JVM 参数：

```bash
java -javaagent:/path/to/skywalking-agent.jar \
     -Dskywalking.agent.service_name=yudao-server \
     -Dskywalking.collector.backend_service=localhost:11800 \
     -jar yudao-server.jar
```

3. 访问 SkyWalking UI 查看链路追踪信息

## 相关文件清单

```
yudao-spring-boot-starter-monitor/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/tracer/
    ├── config/
    │   ├── YudaoTracerAutoConfiguration.java
    │   ├── YudaoMetricsAutoConfiguration.java
    │   └── TracerProperties.java
    └── core/
        ├── annotation/
        │   └── BizTrace.java
        ├── aop/
        │   └── BizTraceAspect.java
        ├── filter/
        │   └── TraceFilter.java
        └── util/
            └── TracerFrameworkUtils.java
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
