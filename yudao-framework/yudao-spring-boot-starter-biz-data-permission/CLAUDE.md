[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-biz-data-permission**

# yudao-spring-boot-starter-biz-data-permission - 数据权限模块

## 模块职责

提供数据权限控制功能，实现行级数据过滤：
- 基于部门的数据权限
- 自定义数据权限规则
- 动态 SQL 条件注入

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoDataPermissionAutoConfiguration` | 数据权限自动配置 |
| `YudaoDeptDataPermissionAutoConfiguration` | 部门数据权限自动配置 |

### 核心注解

| 注解 | 说明 |
|-----|------|
| `@DataPermission` | 数据权限注解（可标注在类或方法上） |

### 规则体系

| 类名 | 说明 |
|-----|------|
| `DataPermissionRule` | 数据权限规则接口 |
| `DataPermissionRuleFactory` | 规则工厂接口 |
| `DataPermissionRuleFactoryImpl` | 规则工厂实现 |
| `DeptDataPermissionRule` | 部门数据权限规则 |
| `DeptDataPermissionRuleCustomizer` | 部门规则定制器 |

### AOP 处理

| 类名 | 说明 |
|-----|------|
| `DataPermissionAnnotationInterceptor` | 数据权限注解拦截器 |
| `DataPermissionAnnotationAdvisor` | 数据权限注解切面 |
| `DataPermissionContextHolder` | 数据权限上下文 |

### 数据库处理

| 类名 | 说明 |
|-----|------|
| `DataPermissionRuleHandler` | 数据权限规则处理器（MyBatis 拦截器） |

### 工具类

| 类名 | 说明 |
|-----|------|
| `DataPermissionUtils` | 数据权限工具类 |

## 配置项

无特殊配置项，通过代码配置数据权限规则。

## 使用示例

### 启用/禁用数据权限

```java
@Service
public class UserServiceImpl implements UserService {

    // 默认启用数据权限
    public List<UserDO> getUsers() {
        // 查询会自动添加数据权限条件
        return userMapper.selectList();
    }

    // 禁用数据权限
    @DataPermission(enable = false)
    public List<UserDO> getAllUsers() {
        // 查询不会添加数据权限条件
        return userMapper.selectList();
    }

    // 只使用特定规则
    @DataPermission(includeRules = DeptDataPermissionRule.class)
    public List<UserDO> getUsersByDept() {
        // 只应用部门数据权限规则
        return userMapper.selectList();
    }

    // 排除特定规则
    @DataPermission(excludeRules = DeptDataPermissionRule.class)
    public List<UserDO> getUsersExcludeDept() {
        // 排除部门数据权限规则
        return userMapper.selectList();
    }
}
```

### 配置部门数据权限规则

```java
@Component
public class UserDataPermissionCustomizer implements DeptDataPermissionRuleCustomizer {

    @Override
    public void customize(DeptDataPermissionRule rule) {
        // 配置 system_user 表的部门字段
        rule.addDeptColumn("system_user", "dept_id");
        // 配置 system_user 表的用户字段（用于"仅本人"权限类型）
        rule.addUserColumn("system_user", "id");

        // 配置其他表的权限字段
        rule.addDeptColumn("system_post", "dept_id");
    }
}
```

### 使用工具类控制数据权限

```java
// 临时禁用数据权限
DataPermissionUtils.executeIgnore(() -> {
    // 此代码块内不进行数据权限过滤
    return userMapper.selectList();
});

// 获取当前数据权限上下文
DataPermission annotation = DataPermissionContextHolder.get();
```

### 自定义数据权限规则

```java
@Component
public class CustomDataPermissionRule implements DataPermissionRule {

    @Override
    public Set<String> getTableNames() {
        // 返回此规则适用的表名
        return Sets.newHashSet("custom_table");
    }

    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {
        // 构建过滤条件
        // 返回 null 表示不添加条件
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser == null) {
            return null;
        }

        // 构建 column = value 条件
        String columnName = tableAlias != null ?
            tableAlias.getName() + ".owner_id" : "owner_id";
        return new EqualsTo(
            new Column(columnName),
            new LongValue(loginUser.getId())
        );
    }
}
```

## @DataPermission 注解说明

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|--------|------|
| `enable` | boolean | true | 是否启用数据权限 |
| `includeRules` | Class[] | {} | 只使用指定规则（优先级高） |
| `excludeRules` | Class[] | {} | 排除指定规则（优先级低） |

## 部门数据权限类型

系统支持以下数据权限类型（在权限管理中配置）：

| 类型 | 说明 |
|-----|------|
| 全部数据权限 | 可查看所有数据 |
| 指定部门数据权限 | 可查看指定部门的数据 |
| 本部门数据权限 | 可查看本部门的数据 |
| 本部门及以下数据权限 | 可查看本部门及下级部门的数据 |
| 仅本人数据权限 | 只能查看自己的数据 |

## 工作原理

1. 请求进入时，通过 AOP 获取方法或类上的 `@DataPermission` 注解
2. 将注解信息存入 `DataPermissionContextHolder`
3. MyBatis 执行 SQL 时，`DataPermissionRuleHandler` 拦截器介入
4. 根据上下文和配置的规则，动态修改 SQL 添加过滤条件
5. 例如：`SELECT * FROM user` 变为 `SELECT * FROM user WHERE dept_id IN (1,2,3)`

## 依赖说明

### 核心依赖
- yudao-common
- yudao-spring-boot-starter-mybatis
- yudao-spring-boot-starter-security（可选，部门规则使用）

## 相关文件清单

```
yudao-spring-boot-starter-biz-data-permission/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/datapermission/
    ├── config/
    │   ├── YudaoDataPermissionAutoConfiguration.java
    │   └── YudaoDeptDataPermissionAutoConfiguration.java
    ├── core/
    │   ├── annotation/
    │   │   └── DataPermission.java
    │   ├── aop/
    │   │   ├── DataPermissionAnnotationAdvisor.java
    │   │   ├── DataPermissionAnnotationInterceptor.java
    │   │   └── DataPermissionContextHolder.java
    │   ├── db/
    │   │   └── DataPermissionRuleHandler.java
    │   ├── rule/
    │   │   ├── DataPermissionRule.java
    │   │   ├── DataPermissionRuleFactory.java
    │   │   ├── DataPermissionRuleFactoryImpl.java
    │   │   └── dept/
    │   │       ├── DeptDataPermissionRule.java
    │   │       └── DeptDataPermissionRuleCustomizer.java
    │   └── util/
    │       └── DataPermissionUtils.java
    └── package-info.java
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
