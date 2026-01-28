[根目录](../CLAUDE.md) > **yudao-module-infra**

# yudao-module-infra - 基础设施模块

## 模块职责

infra 模块主要提供两块能力：
1. **基础设施运维与管理** - 支撑上层通用与核心业务，如定时任务管理、服务器信息等
2. **研发工具** - 提升研发效率与质量，如代码生成器、接口文档等

### 核心功能
- 代码生成器（Codegen）
- 文件管理（File）
- 配置管理（Config）
- 定时任务（Job）
- API 日志（ApiAccessLog、ApiErrorLog）
- 数据源配置（DataSourceConfig）
- Redis 监控
- WebSocket 消息推送

## 模块统计

| 类型 | 数量 |
|-----|------|
| Controller | 16 |
| Service | 19 |
| Data Object | 11 |

## 入口与启动

本模块作为依赖被 yudao-server 引入，无独立启动入口。

### 包结构
```
cn.iocoder.yudao.module.infra
├── api/         # 模块间 API 接口
├── controller/  # REST 控制器
│   ├── admin/   # 后台管理接口 (15个)
│   └── app/     # 移动端接口 (1个)
├── service/     # 业务逻辑
├── dal/         # 数据访问层
├── convert/     # 对象转换
├── enums/       # 枚举定义
├── framework/   # 模块框架配置
└── job/         # 定时任务
```

## 对外接口

### Admin API (后台管理)
| 路径 | 控制器 | 功能 |
|-----|--------|------|
| /admin-api/infra/codegen | CodegenController | 代码生成 |
| /admin-api/infra/config | ConfigController | 参数配置 |
| /admin-api/infra/file | FileController | 文件管理 |
| /admin-api/infra/file-config | FileConfigController | 文件配置 |
| /admin-api/infra/job | JobController | 定时任务 |
| /admin-api/infra/job-log | JobLogController | 任务日志 |
| /admin-api/infra/api-access-log | ApiAccessLogController | API 访问日志 |
| /admin-api/infra/api-error-log | ApiErrorLogController | API 错误日志 |
| /admin-api/infra/data-source-config | DataSourceConfigController | 数据源配置 |
| /admin-api/infra/redis | RedisController | Redis 监控 |

### Demo API (代码生成演示)
| 路径 | 控制器 | 功能 |
|-----|--------|------|
| /admin-api/infra/demo01-contact | Demo01ContactController | 单表示例 |
| /admin-api/infra/demo02-category | Demo02CategoryController | 树表示例 |
| /admin-api/infra/demo03-student-erp | Demo03StudentErpController | ERP 主子表示例 |
| /admin-api/infra/demo03-student-inner | Demo03StudentInnerController | 内嵌主子表示例 |
| /admin-api/infra/demo03-student-normal | Demo03StudentNormalController | 普通主子表示例 |

### App API (移动端)
| 路径 | 控制器 | 功能 |
|-----|--------|------|
| /app-api/infra/file | AppFileController | 文件上传 |

### 模块间 API
| 接口 | 实现类 | 功能 |
|-----|--------|------|
| ConfigApi | ConfigApiImpl | 配置查询 |
| FileApi | FileApiImpl | 文件操作 |
| ApiAccessLogApi | ApiAccessLogApiImpl | 访问日志记录 |
| ApiErrorLogApi | ApiErrorLogApiImpl | 错误日志记录 |
| WebSocketSenderApi | WebSocketSenderApiImpl | WebSocket 消息发送 |

## 关键依赖与配置

### 核心依赖
| 依赖 | 说明 |
|-----|------|
| yudao-spring-boot-starter-biz-tenant | 多租户 |
| yudao-spring-boot-starter-security | 安全认证 |
| yudao-spring-boot-starter-websocket | WebSocket |
| yudao-spring-boot-starter-mybatis | 数据库访问 |
| yudao-spring-boot-starter-redis | 缓存 |
| yudao-spring-boot-starter-job | 定时任务 |
| yudao-spring-boot-starter-mq | 消息队列 |
| yudao-spring-boot-starter-excel | Excel 导出 |
| yudao-spring-boot-starter-monitor | 监控 |
| mybatis-plus-generator | 代码生成表结构解析 |
| velocity-engine-core | 代码生成模板引擎 |
| spring-boot-admin-starter-server | Spring Boot Admin |
| AWS S3 SDK | 对象存储 |
| commons-net | FTP 连接 |
| jsch | SFTP 连接 |
| tika-core | 文件类型识别 |

### 配置项
```yaml
yudao:
  codegen:
    base-package: cn.iocoder.yudao
    db-schemas: ruoyi-vue-pro
    front-type: 20      # 前端模板类型
    vo-type: 10         # VO 类型
    delete-batch-enable: true  # 是否生成批量删除接口
    unit-test-enable: false    # 是否生成单元测试
  websocket:
    enable: true
    path: /infra/ws
    sender-type: local  # local/redis/rocketmq/kafka/rabbitmq
```

## 数据模型

### 核心表
| 表名 | 数据对象 | 说明 |
|-----|---------|------|
| infra_codegen_table | CodegenTableDO | 代码生成表定义 |
| infra_codegen_column | CodegenColumnDO | 代码生成字段定义 |
| infra_config | ConfigDO | 参数配置 |
| infra_file | FileDO | 文件表 |
| infra_file_config | FileConfigDO | 文件配置 |
| infra_file_content | FileContentDO | 文件内容 |
| infra_job | JobDO | 定时任务 |
| infra_job_log | JobLogDO | 任务日志 |
| infra_api_access_log | ApiAccessLogDO | API 访问日志 |
| infra_api_error_log | ApiErrorLogDO | API 错误日志 |
| infra_data_source_config | DataSourceConfigDO | 数据源配置 |

### 示例表（代码生成演示）
| 表名 | 数据对象 | 说明 |
|-----|---------|------|
| infra_demo01_contact | Demo01ContactDO | 单表示例 |
| infra_demo02_category | Demo02CategoryDO | 树表示例 |
| infra_demo03_student | Demo03StudentDO | 主子表示例 |
| infra_demo03_course | Demo03CourseDO | 子表-课程 |
| infra_demo03_grade | Demo03GradeDO | 子表-成绩 |

## 测试与质量

测试文件位于 `yudao-server/src/test/` 目录。

## 常见问题 (FAQ)

### Q: 代码生成器如何使用？
A: 1. 在 数据源配置 中添加数据源
   2. 在 代码生成 中导入表
   3. 配置生成选项后下载代码

### Q: 如何添加新的文件存储方式？
A: 实现 FileClient 接口，参考 S3FileClient、FtpFileClient 等实现

### Q: 如何配置 S3 对象存储？
A: 1. 在 文件配置 中添加 S3 类型配置
   2. 填写 endpoint、accessKey、secretKey、bucket 等参数

### Q: 定时任务如何创建？
A: 1. 实现 JobHandler 接口
   2. 在 定时任务 页面添加任务，填写处理器名称和 CRON 表达式

## 相关文件清单

```
yudao-module-infra/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/module/infra/
    ├── api/                    # 模块间 API
    ├── controller/
    │   ├── admin/              # 后台管理接口
    │   │   ├── codegen/        # 代码生成
    │   │   ├── config/         # 参数配置
    │   │   ├── file/           # 文件管理
    │   │   ├── job/            # 定时任务
    │   │   ├── logger/         # 日志管理
    │   │   ├── db/             # 数据源
    │   │   ├── redis/          # Redis 监控
    │   │   └── demo/           # 演示示例
    │   └── app/                # 移动端接口
    ├── service/                # 业务服务
    │   ├── codegen/            # 代码生成服务
    │   ├── config/             # 配置服务
    │   ├── file/               # 文件服务
    │   ├── job/                # 任务服务
    │   ├── logger/             # 日志服务
    │   └── db/                 # 数据源服务
    ├── dal/                    # 数据访问
    │   ├── dataobject/         # 数据对象
    │   └── mysql/              # Mapper
    ├── convert/                # 对象转换
    ├── enums/                  # 枚举
    └── framework/              # 框架配置
        └── file/               # 文件客户端
```

## 变更记录 (Changelog)

### 2026-01-28
- 更新模块统计信息
- 完善 API 接口列表
- 添加 Demo API 文档

### 2026-01-23
- 初始化模块文档

---
*文档更新时间: 2026-01-28*
