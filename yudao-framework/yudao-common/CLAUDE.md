[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-common**

# yudao-common - 公共基础模块

## 模块职责

yudao-common 是整个框架的基础模块，提供通用的 POJO 类、枚举、工具类、异常处理、验证注解等基础设施。所有其他模块都依赖于此模块。

## 关键类说明

### 通用响应对象

| 类名 | 说明 |
|-----|------|
| `CommonResult<T>` | 统一 API 响应封装，包含 code、msg、data |
| `PageResult<T>` | 分页结果封装 |
| `PageParam` | 分页请求参数 |
| `SortablePageParam` | 支持排序的分页请求参数 |
| `SortingField` | 排序字段定义 |

### 异常处理

| 类名 | 说明 |
|-----|------|
| `ErrorCode` | 错误码定义接口 |
| `ServiceException` | 业务异常（用户可见） |
| `ServerException` | 服务端异常（系统内部） |
| `GlobalErrorCodeConstants` | 全局错误码常量 |
| `ServiceExceptionUtil` | 异常工具类 |

### 通用枚举

| 枚举 | 说明 |
|-----|------|
| `CommonStatusEnum` | 通用状态（开启/关闭） |
| `UserTypeEnum` | 用户类型（管理员/会员） |
| `TerminalEnum` | 终端类型 |
| `WebFilterOrderEnum` | Web 过滤器顺序 |
| `DateIntervalEnum` | 日期间隔类型 |

### 工具类

| 类名 | 说明 |
|-----|------|
| `JsonUtils` | JSON 序列化/反序列化 |
| `DateUtils` | 日期处理 |
| `LocalDateTimeUtils` | LocalDateTime 处理 |
| `CollectionUtils` | 集合操作增强 |
| `MapUtils` | Map 操作增强 |
| `ArrayUtils` | 数组操作 |
| `StrUtils` | 字符串处理 |
| `BeanUtils` | Bean 拷贝 |
| `ObjectUtils` | 对象操作 |
| `NumberUtils` | 数值操作 |
| `MoneyUtils` | 金额处理（分转元等） |
| `FileUtils` | 文件操作 |
| `HttpUtils` | HTTP 请求 |
| `ServletUtils` | Servlet 工具 |
| `SpringUtils` | Spring 上下文 |
| `SpringExpressionUtils` | SpEL 表达式解析 |
| `ValidationUtils` | 参数校验 |
| `CacheUtils` | 缓存工具 |
| `TracerUtils` | 链路追踪 |

### 验证注解

| 注解 | 说明 |
|-----|------|
| `@Mobile` | 手机号验证 |
| `@Telephone` | 座机号验证 |
| `@InEnum` | 枚举值验证 |

### 模块间 API（biz 包）

提供跨模块调用的 API 接口定义：

| 接口 | 说明 |
|-----|------|
| `ApiAccessLogCommonApi` | API 访问日志 |
| `ApiErrorLogCommonApi` | API 错误日志 |
| `DictDataCommonApi` | 字典数据 |
| `OperateLogCommonApi` | 操作日志 |
| `OAuth2TokenCommonApi` | OAuth2 Token |
| `PermissionCommonApi` | 权限校验 |
| `TenantCommonApi` | 租户信息 |

## 核心接口

| 接口 | 说明 |
|-----|------|
| `ArrayValuable` | 枚举值数组接口，配合 @InEnum 使用 |
| `KeyValue<K,V>` | 键值对通用接口 |

## 配置项

本模块无特殊配置项。

## 使用示例

### 统一响应

```java
// 成功响应
return CommonResult.success(data);

// 错误响应
return CommonResult.error(ErrorCode.USER_NOT_FOUND);

// 检查响应并获取数据
User user = userApi.getUser(userId).getCheckedData();
```

### 分页查询

```java
public PageResult<UserVO> getUsers(PageParam pageParam) {
    // 返回分页结果
    return new PageResult<>(list, total);
}
```

### 枚举验证

```java
public class UserCreateReqVO {
    @InEnum(UserStatusEnum.class)
    private Integer status;
}
```

### 工具类使用

```java
// JSON 操作
String json = JsonUtils.toJsonString(obj);
User user = JsonUtils.parseObject(json, User.class);

// 集合操作
List<Long> ids = CollectionUtils.convertList(users, User::getId);
Map<Long, User> userMap = CollectionUtils.convertMap(users, User::getId);

// Bean 拷贝
UserVO vo = BeanUtils.toBean(user, UserVO.class);
```

## 依赖说明

### 核心依赖
- Spring Core/Web（provided）
- Jackson（JSON 处理）
- Hutool（工具类库）
- MapStruct（对象转换）
- Guava（Google 工具库）
- TransmittableThreadLocal（跨线程上下文传递）
- SkyWalking APM Toolkit（链路追踪）
- Easy Trans（VO 数据翻译）

## 相关文件清单

```
yudao-common/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/common/
    ├── biz/                    # 模块间 API 定义
    │   ├── infra/logger/       # 日志 API
    │   └── system/             # 系统 API
    ├── core/                   # 核心接口
    ├── enums/                  # 通用枚举
    ├── exception/              # 异常处理
    ├── pojo/                   # 通用 POJO
    ├── util/                   # 工具类
    └── validation/             # 验证注解
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
