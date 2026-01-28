[根目录](../CLAUDE.md) > **yudao-framework**

# yudao-framework - 技术框架层

## 模块职责

yudao-framework 是技术组件层，每个子包代表一个组件。每个组件包括两部分：
- `core` 包：该组件的核心封装
- `config` 包：该组件基于 Spring 的配置

### 组件分类
1. **框架组件**：MyBatis、Redis 等技术框架的拓展
2. **业务组件**：与业务相关的组件封装（Maven 名包含 `biz`）

## 子模块统计

| 类型 | 数量 |
|-----|------|
| 基础模块 | 1 |
| 框架组件 | 10 |
| 业务组件 | 3 |
| **总计** | **14** |

## 子模块索引

### 基础模块
| 模块 | 职责 | 文档 |
|-----|------|------|
| yudao-common | 公共工具类、通用定义 | [CLAUDE.md](./yudao-common/CLAUDE.md) |

### 框架组件
| 模块 | 职责 | 文档 |
|-----|------|------|
| yudao-spring-boot-starter-mybatis | MyBatis Plus 增强、多数据源 | [CLAUDE.md](./yudao-spring-boot-starter-mybatis/CLAUDE.md) |
| yudao-spring-boot-starter-redis | Redis + Redisson 封装 | [CLAUDE.md](./yudao-spring-boot-starter-redis/CLAUDE.md) |
| yudao-spring-boot-starter-web | Web 层通用配置 | [CLAUDE.md](./yudao-spring-boot-starter-web/CLAUDE.md) |
| yudao-spring-boot-starter-security | Spring Security + OAuth2 | [CLAUDE.md](./yudao-spring-boot-starter-security/CLAUDE.md) |
| yudao-spring-boot-starter-websocket | WebSocket 支持 | [CLAUDE.md](./yudao-spring-boot-starter-websocket/CLAUDE.md) |
| yudao-spring-boot-starter-monitor | 监控与追踪 | [CLAUDE.md](./yudao-spring-boot-starter-monitor/CLAUDE.md) |
| yudao-spring-boot-starter-protection | 服务保障（限流、幂等等） | [CLAUDE.md](./yudao-spring-boot-starter-protection/CLAUDE.md) |
| yudao-spring-boot-starter-job | Quartz 定时任务 | [CLAUDE.md](./yudao-spring-boot-starter-job/CLAUDE.md) |
| yudao-spring-boot-starter-mq | 消息队列（Redis/RocketMQ/Kafka/RabbitMQ） | [CLAUDE.md](./yudao-spring-boot-starter-mq/CLAUDE.md) |
| yudao-spring-boot-starter-excel | Excel 导入导出 | [CLAUDE.md](./yudao-spring-boot-starter-excel/CLAUDE.md) |

### 业务组件
| 模块 | 职责 | 文档 |
|-----|------|------|
| yudao-spring-boot-starter-biz-tenant | 多租户支持 | [CLAUDE.md](./yudao-spring-boot-starter-biz-tenant/CLAUDE.md) |
| yudao-spring-boot-starter-biz-data-permission | 数据权限 | [CLAUDE.md](./yudao-spring-boot-starter-biz-data-permission/CLAUDE.md) |
| yudao-spring-boot-starter-biz-ip | IP 地区解析 | [CLAUDE.md](./yudao-spring-boot-starter-biz-ip/CLAUDE.md) |

## 关键配置类

### 自动配置类
| 模块 | 配置类 |
|-----|--------|
| mybatis | YudaoMybatisAutoConfiguration, YudaoDataSourceAutoConfiguration |
| redis | YudaoRedisAutoConfiguration, YudaoCacheAutoConfiguration |
| web | YudaoWebAutoConfiguration, YudaoSwaggerAutoConfiguration, YudaoJacksonAutoConfiguration |
| security | YudaoSecurityAutoConfiguration |
| websocket | YudaoWebSocketAutoConfiguration |
| monitor | YudaoTracerAutoConfiguration, YudaoMetricsAutoConfiguration |
| protection | YudaoApiSignatureAutoConfiguration, YudaoIdempotentConfiguration, YudaoRateLimiterConfiguration |
| job | YudaoQuartzAutoConfiguration, YudaoAsyncAutoConfiguration |
| mq | YudaoRedisMQProducerAutoConfiguration, YudaoRedisMQConsumerAutoConfiguration, YudaoRabbitMQAutoConfiguration |
| excel | YudaoDictAutoConfiguration |
| tenant | YudaoTenantAutoConfiguration |
| data-permission | YudaoDataPermissionAutoConfiguration, YudaoDeptDataPermissionAutoConfiguration |

## 入口与启动

本模块作为依赖被业务模块引入，通过 Spring Boot 自动配置机制加载。

## 核心功能说明

### yudao-common
- 通用工具类（CollectionUtils, JsonUtils, DateUtils 等）
- 通用响应对象（CommonResult, PageResult）
- 异常处理（ServiceException, ErrorCode）
- 验证注解（@Mobile, @InEnum 等）
- 模块间 API 定义（biz 包下的 API 接口）

### yudao-spring-boot-starter-security
- TokenAuthenticationFilter：Token 认证过滤器
- SecurityFrameworkService：权限校验服务
- LoginUser：登录用户上下文

### yudao-spring-boot-starter-biz-tenant
- TenantContextHolder：租户上下文
- TenantDatabaseInterceptor：租户数据隔离拦截器
- @TenantIgnore：忽略租户注解

### yudao-spring-boot-starter-biz-data-permission
- @DataPermission：数据权限注解
- DeptDataPermissionRule：部门数据权限规则
- DataPermissionRuleHandler：数据权限处理器

### yudao-spring-boot-starter-mq
支持多种消息队列实现：
- Redis Stream
- RocketMQ
- Kafka
- RabbitMQ

### yudao-spring-boot-starter-protection
- 限流（@RateLimiter）
- 幂等（@Idempotent）
- 分布式锁（@Lock4j）
- API 签名验证（@ApiSignature）

## 相关文件清单

```
yudao-framework/
├── pom.xml
├── CLAUDE.md
├── yudao-common/                               # 公共模块
│   └── CLAUDE.md
├── yudao-spring-boot-starter-mybatis/          # MyBatis
│   └── CLAUDE.md
├── yudao-spring-boot-starter-redis/            # Redis
│   └── CLAUDE.md
├── yudao-spring-boot-starter-web/              # Web
│   └── CLAUDE.md
├── yudao-spring-boot-starter-security/         # 安全
│   └── CLAUDE.md
├── yudao-spring-boot-starter-websocket/        # WebSocket
│   └── CLAUDE.md
├── yudao-spring-boot-starter-monitor/          # 监控
│   └── CLAUDE.md
├── yudao-spring-boot-starter-protection/       # 服务保障
│   └── CLAUDE.md
├── yudao-spring-boot-starter-job/              # 定时任务
│   └── CLAUDE.md
├── yudao-spring-boot-starter-mq/               # 消息队列
│   └── CLAUDE.md
├── yudao-spring-boot-starter-excel/            # Excel
│   └── CLAUDE.md
├── yudao-spring-boot-starter-biz-tenant/       # 多租户
│   └── CLAUDE.md
├── yudao-spring-boot-starter-biz-data-permission/  # 数据权限
│   └── CLAUDE.md
└── yudao-spring-boot-starter-biz-ip/           # IP 地区
    └── CLAUDE.md
```

## 变更记录 (Changelog)

### 2026-01-28
- 为所有 14 个子模块生成独立 CLAUDE.md 文档
- 添加子模块文档链接

### 2026-01-23
- 初始化模块文档

---
*文档更新时间: 2026-01-28*
