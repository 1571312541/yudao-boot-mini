[根目录](../CLAUDE.md) > **yudao-dependencies**

# yudao-dependencies - 依赖版本管理

## 模块职责

yudao-dependencies 是基础 BOM（Bill of Materials）文件，统一管理整个项目的依赖版本，确保所有模块使用一致的第三方库版本，避免版本冲突。

## 项目版本

当前版本：`2025.12-jdk8-SNAPSHOT`

## 入口与启动

本模块为纯 POM 项目，无代码，仅提供依赖版本定义。

## 核心版本定义

### 基础框架
| 依赖 | 版本 | 说明 |
|-----|------|------|
| Spring Boot | 2.7.18 | 基础框架 |
| Spring Framework | 5.3.39 | Spring 核心 |
| Spring Security | 5.8.16 | 安全框架 |

### 数据库相关
| 依赖 | 版本 | 说明 |
|-----|------|------|
| MyBatis | 3.5.19 | ORM 框架 |
| MyBatis Plus | 3.5.15 | MyBatis 增强 |
| MyBatis Plus Join | 1.5.5 | 连表查询 |
| Druid | 1.2.27 | 数据库连接池 |
| Dynamic Datasource | 4.5.0 | 多数据源 |
| Redisson | 3.52.0 | Redis 客户端 |

### Web 相关
| 依赖 | 版本 | 说明 |
|-----|------|------|
| SpringDoc | 1.8.0 | API 文档 |
| Knife4j | 4.5.0 | Swagger 增强 |

### 消息队列
| 依赖 | 版本 | 说明 |
|-----|------|------|
| RocketMQ Spring | 2.3.5 | RocketMQ |

### 监控相关
| 依赖 | 版本 | 说明 |
|-----|------|------|
| SkyWalking | 8.12.0 | 链路追踪 |
| Spring Boot Admin | 2.7.15 | 应用监控 |

### 工作流
| 依赖 | 版本 | 说明 |
|-----|------|------|
| Flowable | 6.8.0 | 工作流引擎 |

### 工具类
| 依赖 | 版本 | 说明 |
|-----|------|------|
| Lombok | 1.18.42 | 代码简化 |
| MapStruct | 1.6.3 | 对象映射 |
| Hutool | 5.8.42 | 工具类库 |
| Guava | 33.5.0-jre | Google 工具库 |
| Velocity | 2.4 | 模板引擎 |
| FastExcel | 1.3.0 | Excel 处理 |
| Tika | 2.9.3 | 文件类型识别 |
| IP2Region | 2.7.0 | IP 地区解析 |

### 三方服务
| 依赖 | 版本 | 说明 |
|-----|------|------|
| AWS SDK | 2.40.15 | S3 对象存储 |
| JustAuth | 1.16.7 | 社交登录 |
| 微信 SDK | 4.7.9 | 微信公众号/小程序 |
| 支付宝 SDK | 4.40.607 | 支付宝支付 |
| 验证码 | 1.4.0 | 滑动验证码 |

### 测试相关
| 依赖 | 版本 | 说明 |
|-----|------|------|
| Podam | 7.2.11 | 随机数据生成 |
| Jedis Mock | 1.1.12 | Redis Mock |
| Mockito Inline | 4.11.0 | Mock 框架 |

### 数据库驱动
| 依赖 | 版本 | 说明 |
|-----|------|------|
| 达梦 | 8.1.3.140 | 达梦数据库 |
| 人大金仓 | 8.6.0 | 人大金仓数据库 |
| OpenGauss | 5.1.0 | OpenGauss 数据库 |
| TDengine | 3.7.9 | 时序数据库 |

### 其他
| 依赖 | 版本 | 说明 |
|-----|------|------|
| Netty | 4.2.9.Final | 网络框架 |
| Vert.x | 4.5.22 | 响应式框架 |
| MQTT | 1.2.5 | 消息协议 |

## 使用方式

在子模块的 pom.xml 中通过 `<dependencyManagement>` 引入：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>cn.iocoder.boot</groupId>
            <artifactId>yudao-dependencies</artifactId>
            <version>${revision}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 相关文件清单

```
yudao-dependencies/
└── pom.xml    # 依赖版本定义
```

## 变更记录 (Changelog)

### 2026-01-28
- 更新依赖版本信息
- 添加更多依赖说明

### 2026-01-23
- 初始化模块文档

---
*文档更新时间: 2026-01-28*
