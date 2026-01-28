[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-job**

# yudao-spring-boot-starter-job - 定时任务模块

## 模块职责

提供任务调度功能，包括：
- 定时任务（基于 Quartz）
- 异步任务（基于 Spring Async）
- 任务管理与调度

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoQuartzAutoConfiguration` | Quartz 自动配置 |
| `YudaoAsyncAutoConfiguration` | 异步任务自动配置 |

### 定时任务核心

| 类名 | 说明 |
|-----|------|
| `JobHandler` | 任务处理器接口 |
| `JobHandlerInvoker` | 任务处理器调用者（Quartz Job） |
| `SchedulerManager` | 调度管理器 |

### 任务日志

| 类名 | 说明 |
|-----|------|
| `JobLogFrameworkService` | 任务日志服务接口 |

### 枚举

| 枚举 | 说明 |
|-----|------|
| `JobDataKeyEnum` | 任务数据 Key 枚举 |

### 工具类

| 类名 | 说明 |
|-----|------|
| `CronUtils` | Cron 表达式工具类 |

## 配置项

```yaml
spring:
  quartz:
    job-store-type: jdbc           # 任务存储类型：jdbc/memory
    auto-startup: true             # 自动启动
    startup-delay: 1s              # 延迟启动时间
    wait-for-jobs-to-complete-on-shutdown: true  # 关闭时等待任务完成
    overwrite-existing-jobs: false # 是否覆盖已有任务
    jdbc:
      initialize-schema: never     # 数据库初始化模式：never/always/embedded
    properties:
      org:
        quartz:
          scheduler:
            instanceName: schedulerName
            instanceId: AUTO
          jobStore:
            class: org.springframework.scheduling.quartz.LocalDataSourceJobStore
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            tablePrefix: QRTZ_
            isClustered: true
            clusterCheckinInterval: 15000
            useProperties: false
          threadPool:
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 25
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
```

## 使用示例

### 创建定时任务处理器

```java
@Component("demoJob")
public class DemoJob implements JobHandler {

    @Override
    public String execute(String param) throws Exception {
        log.info("执行定时任务, 参数: {}", param);
        // 业务逻辑
        return "执行成功";
    }
}
```

### 使用 SchedulerManager 管理任务

```java
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private SchedulerManager schedulerManager;

    // 创建任务
    public void createJob(Long jobId, String handlerName, String cron, String param) {
        schedulerManager.addJob(jobId, handlerName, param, cron, 0, 0);
    }

    // 更新任务
    public void updateJob(Long jobId, String handlerName, String cron, String param) {
        schedulerManager.updateJob(jobId, handlerName, param, cron, 0, 0);
    }

    // 删除任务
    public void deleteJob(Long jobId) {
        schedulerManager.deleteJob(jobId);
    }

    // 暂停任务
    public void pauseJob(Long jobId) {
        schedulerManager.pauseJob(jobId);
    }

    // 恢复任务
    public void resumeJob(Long jobId) {
        schedulerManager.resumeJob(jobId);
    }

    // 立即执行一次
    public void triggerJob(Long jobId, String param) {
        schedulerManager.triggerJob(jobId, param);
    }
}
```

### 异步任务

```java
@Service
public class AsyncServiceImpl {

    // 使用 @Async 注解标记异步方法
    @Async
    public void asyncTask() {
        log.info("执行异步任务");
        // 耗时操作
    }

    // 带返回值的异步任务
    @Async
    public CompletableFuture<String> asyncTaskWithResult() {
        // 耗时操作
        return CompletableFuture.completedFuture("执行完成");
    }
}

// 调用异步任务
@RestController
public class DemoController {

    @Autowired
    private AsyncServiceImpl asyncService;

    @PostMapping("/async")
    public CommonResult<Boolean> runAsync() {
        asyncService.asyncTask();  // 立即返回，任务在后台执行
        return success(true);
    }
}
```

### Cron 表达式工具

```java
// 验证 Cron 表达式
boolean valid = CronUtils.isValid("0 0/5 * * * ?");

// 获取下次执行时间
LocalDateTime nextTime = CronUtils.getNextTime("0 0/5 * * * ?");

// 获取接下来 N 次执行时间
List<LocalDateTime> nextTimes = CronUtils.getNextTimes("0 0/5 * * * ?", 5);
```

## JobHandler 接口说明

```java
public interface JobHandler {

    /**
     * 执行任务
     *
     * @param param 任务参数（可为 null）
     * @return 执行结果
     * @throws Exception 执行异常
     */
    String execute(String param) throws Exception;
}
```

## Cron 表达式示例

| 表达式 | 说明 |
|-------|------|
| `0 0/5 * * * ?` | 每5分钟执行一次 |
| `0 0 * * * ?` | 每小时整点执行 |
| `0 0 0 * * ?` | 每天零点执行 |
| `0 0 0 1 * ?` | 每月1号零点执行 |
| `0 0 0 ? * MON` | 每周一零点执行 |
| `0 0 0 1 1 ?` | 每年1月1日零点执行 |

## 依赖说明

### 核心依赖
- Spring Boot Starter Quartz
- Jakarta Validation API

## 相关文件清单

```
yudao-spring-boot-starter-job/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/quartz/
    ├── config/
    │   ├── YudaoQuartzAutoConfiguration.java
    │   └── YudaoAsyncAutoConfiguration.java
    └── core/
        ├── enums/
        │   └── JobDataKeyEnum.java
        ├── handler/
        │   ├── JobHandler.java
        │   └── JobHandlerInvoker.java
        ├── scheduler/
        │   └── SchedulerManager.java
        ├── service/
        │   └── JobLogFrameworkService.java
        └── util/
            └── CronUtils.java
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
