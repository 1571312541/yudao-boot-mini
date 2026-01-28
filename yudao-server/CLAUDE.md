[根目录](../CLAUDE.md) > **yudao-server**

# yudao-server - 主启动模块

## 模块职责

yudao-server 是整个项目的主启动模块，本质上是一个"空壳容器"。它通过引入各个 `yudao-module-xxx` 的依赖，整合所有业务模块，并提供 RESTful API 给前端项目（如 yudao-ui-admin、yudao-ui-user）。

## 入口与启动

### 主启动类
```
src/main/java/cn/iocoder/yudao/server/YudaoServerApplication.java
```

### 启动命令
```bash
# Maven 启动
mvn spring-boot:run -pl yudao-server

# 或打包后运行
mvn package -pl yudao-server
java -jar yudao-server/target/yudao-server.jar
```

### 启动扫描包
- `${yudao.info.base-package}.server` (cn.iocoder.yudao.server)
- `${yudao.info.base-package}.module` (cn.iocoder.yudao.module)

## 对外接口

本模块不直接提供业务接口，所有 API 接口由引入的业务模块提供：
- `/admin-api/system/**` - 系统模块接口 (47 个 Controller)
- `/admin-api/infra/**` - 基础设施模块接口 (16 个 Controller)
- `/app-api/**` - 移动端接口

### 默认控制器
- `DefaultController.java` - 提供首页重定向等默认行为

## 关键依赖与配置

### 核心依赖
| 依赖 | 说明 |
|-----|------|
| yudao-module-system | 系统核心功能模块 |
| yudao-module-infra | 基础设施模块 |
| yudao-spring-boot-starter-protection | 服务保障组件 |

### 可选模块（已注释）
| 模块 | 说明 |
|-----|------|
| yudao-module-member | 会员中心 |
| yudao-module-report | 数据报表 |
| yudao-module-bpm | 工作流 |
| yudao-module-pay | 支付服务 |
| yudao-module-mp | 微信公众号 |
| yudao-module-mall | 商城 |
| yudao-module-crm | CRM |
| yudao-module-erp | ERP |
| yudao-module-ai | AI 大模型 |
| yudao-module-iot | 物联网 |

### 配置文件
| 文件 | 用途 |
|-----|------|
| application.yaml | 主配置文件 |
| application-local.yaml | 本地开发环境 |
| application-dev.yaml | 开发环境 |

### 关键配置项
```yaml
spring:
  application:
    name: yudao-server
  profiles:
    active: local  # 激活的配置文件

yudao:
  info:
    base-package: cn.iocoder.yudao  # 基础包路径
  tenant:
    enable: true  # 多租户开关
  security:
    permit-all_urls: [...]  # 无需认证的接口
  websocket:
    enable: true
    path: /infra/ws
```

## 数据模型

本模块无独立数据模型，数据模型由各业务模块提供。

## 测试与质量

### 测试类
```
src/test/java/cn/iocoder/yudao/ProjectReactor.java
```

## 常见问题 (FAQ)

### Q: 启动报错怎么办？
A: 请参考官方文档 https://doc.iocoder.cn/quick-start/

### Q: 如何添加新的业务模块？
A: 1. 在 pom.xml 中添加模块依赖
   2. 确保模块包路径在 `cn.iocoder.yudao.module` 下

### Q: 如何切换数据库类型？
A: 修改 application-local.yaml 中的数据源配置，并确保引入对应的数据库驱动

### Q: 如何启用可选模块？
A: 在 pom.xml 中取消对应模块的注释即可

## 相关文件清单

```
yudao-server/
├── pom.xml                                    # Maven 配置
└── src/
    ├── main/
    │   ├── java/.../server/
    │   │   ├── YudaoServerApplication.java   # 主启动类
    │   │   └── controller/
    │   │       └── DefaultController.java    # 默认控制器
    │   └── resources/
    │       ├── application.yaml              # 主配置
    │       ├── application-local.yaml        # 本地配置
    │       └── application-dev.yaml          # 开发配置
    └── test/
        └── java/.../ProjectReactor.java      # 测试类
```

## 变更记录 (Changelog)

### 2026-01-28
- 更新模块统计信息
- 添加可选模块说明
- 完善配置项文档

### 2026-01-23
- 初始化模块文档

---
*文档更新时间: 2026-01-28*
