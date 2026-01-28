[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-mybatis**

# yudao-spring-boot-starter-mybatis - MyBatis Plus 增强模块

## 模块职责

提供数据库访问层的增强封装，包括：
- MyBatis Plus 增强（分页、通用 Mapper）
- 多数据源支持
- 数据库连接池（Druid）
- 联表查询（MyBatis Plus Join）
- VO 数据翻译（Easy Trans）
- 多数据库兼容（MySQL、PostgreSQL、Oracle、SQL Server、达梦、人大金仓等）

## 关键类说明

### 核心类

| 类名 | 说明 |
|-----|------|
| `BaseMapperX<T>` | 增强 Mapper 基类，提供便捷 CRUD 方法 |
| `BaseDO` | 数据对象基类，含 id、createTime、updateTime、creator、updater、deleted |
| `LambdaQueryWrapperX<T>` | 增强 Lambda 查询构造器 |
| `QueryWrapperX<T>` | 增强查询构造器 |
| `MPJLambdaWrapperX<T>` | 增强联表查询构造器 |

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoMybatisAutoConfiguration` | MyBatis 自动配置 |
| `YudaoDataSourceAutoConfiguration` | 数据源自动配置 |
| `IdTypeEnvironmentPostProcessor` | ID 生成策略配置 |

### 类型处理器

| 类名 | 说明 |
|-----|------|
| `EncryptTypeHandler` | 字段加密处理器 |
| `IntegerListTypeHandler` | Integer 列表 JSON 存储 |
| `LongListTypeHandler` | Long 列表 JSON 存储 |
| `LongSetTypeHandler` | Long 集合 JSON 存储 |
| `StringListTypeHandler` | String 列表 JSON 存储 |

### 字段处理

| 类名 | 说明 |
|-----|------|
| `DefaultDBFieldHandler` | 默认字段填充处理器（createTime、updateTime、creator、updater） |

### 枚举

| 枚举 | 说明 |
|-----|------|
| `DbTypeEnum` | 数据库类型枚举 |
| `DataSourceEnum` | 数据源类型枚举（MASTER、SLAVE） |

### 工具类

| 类名 | 说明 |
|-----|------|
| `MyBatisUtils` | MyBatis 工具类（分页构建等） |
| `JdbcUtils` | JDBC 工具类（数据库类型判断等） |
| `TranslateUtils` | 数据翻译工具类 |

## 配置项

```yaml
spring:
  datasource:
    dynamic:
      primary: master  # 主数据源
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/ruoyi-vue-pro
          username: root
          password: 123456
        slave:
          url: jdbc:mysql://localhost:3307/ruoyi-vue-pro
          username: root
          password: 123456

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 下划线转驼峰
  global-config:
    db-config:
      id-type: NONE  # ID 生成策略（由 IdTypeEnvironmentPostProcessor 自动配置）
      logic-delete-value: 1
      logic-not-delete-value: 0
  type-aliases-package: cn.iocoder.yudao.module.*.dal.dataobject
```

## 使用示例

### 定义 Mapper

```java
@Mapper
public interface UserMapper extends BaseMapperX<UserDO> {

    default PageResult<UserDO> selectPage(UserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserDO>()
                .likeIfPresent(UserDO::getUsername, reqVO.getUsername())
                .eqIfPresent(UserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(UserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserDO::getId));
    }
}
```

### 联表查询

```java
public PageResult<UserDetailVO> selectJoinPage(PageParam pageParam) {
    MPJLambdaWrapperX<UserDO> wrapper = new MPJLambdaWrapperX<UserDO>()
            .selectAll(UserDO.class)
            .selectAs(DeptDO::getName, UserDetailVO::getDeptName)
            .leftJoin(DeptDO.class, DeptDO::getId, UserDO::getDeptId);
    return userMapper.selectJoinPage(pageParam, UserDetailVO.class, wrapper);
}
```

### 数据对象定义

```java
@TableName("system_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseDO {

    @TableId
    private Long id;
    private String username;
    private String password;

    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> roleIds;
}
```

### 多数据源切换

```java
@DS("slave")  // 切换到从库
public List<UserDO> selectFromSlave() {
    return userMapper.selectList();
}
```

## 依赖说明

### 核心依赖
- MyBatis Plus Boot Starter
- MyBatis Plus Join（联表查询）
- Druid Spring Boot Starter（连接池）
- Dynamic Datasource（多数据源）
- Easy Trans（数据翻译）

### 数据库驱动（可选）
- MySQL Connector
- PostgreSQL
- Oracle JDBC
- SQL Server JDBC
- 达梦 DmJdbcDriver18
- 人大金仓 Kingbase8
- OpenGauss
- TDengine

## 相关文件清单

```
yudao-spring-boot-starter-mybatis/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/
    ├── datasource/
    │   ├── config/                     # 数据源配置
    │   └── core/
    │       ├── enums/                  # 数据源枚举
    │       └── filter/                 # Druid 过滤器
    ├── mybatis/
    │   ├── config/                     # MyBatis 配置
    │   └── core/
    │       ├── dataobject/             # BaseDO
    │       ├── enums/                  # 数据库类型枚举
    │       ├── handler/                # 字段处理器
    │       ├── mapper/                 # BaseMapperX
    │       ├── query/                  # 查询构造器
    │       ├── type/                   # 类型处理器
    │       └── util/                   # 工具类
    └── translate/                      # 数据翻译
        ├── config/
        └── core/
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
