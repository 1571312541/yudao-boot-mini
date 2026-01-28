[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-biz-ip**

# yudao-spring-boot-starter-biz-ip - IP 地区解析模块

## 模块职责

提供 IP 和地区相关功能：
- IP 地址解析（获取 IP 对应的地区信息）
- 中国行政区划数据（省市区县）
- 地区编码查询

## 关键类说明

### 核心类

| 类名 | 说明 |
|-----|------|
| `Area` | 地区实体类 |
| `AreaUtils` | 地区工具类 |
| `IPUtils` | IP 工具类 |

### 枚举

| 枚举 | 说明 |
|-----|------|
| `AreaTypeEnum` | 地区类型枚举（国家、省、市、区县） |

## Area 类结构

```java
@Data
public class Area {
    /** 编号 - 区域编码 */
    private Integer id;
    /** 名字 */
    private String name;
    /** 类型 */
    private AreaTypeEnum type;
    /** 父节点 */
    private Area parent;
    /** 子节点 */
    private List<Area> children;
}
```

## 配置项

无特殊配置项，模块内置 IP 库和地区数据。

## 使用示例

### IP 地址解析

```java
// 获取 IP 对应的地区信息
Area area = IPUtils.getArea("114.114.114.114");
// 结果: Area{id=320000, name="江苏省", type=PROVINCE}

// 获取 IP 对应的地区 ID
Integer areaId = IPUtils.getAreaId("114.114.114.114");
// 结果: 320000

// 获取格式化的地区名称
String areaName = IPUtils.getAreaName("114.114.114.114");
// 结果: "江苏省 南京市"
```

### 地区查询

```java
// 根据编码获取地区
Area area = AreaUtils.getArea(110000);
// 结果: Area{id=110000, name="北京市", type=PROVINCE}

// 获取格式化名称（包含父级）
String fullName = AreaUtils.format(110105);
// 结果: "北京市 朝阳区"

// 获取格式化名称（包含所有层级）
String fullName = AreaUtils.format(110105, " ");
// 结果: "北京市 北京市 朝阳区"

// 获取省份列表
List<Area> provinces = AreaUtils.getByType(AreaTypeEnum.PROVINCE);

// 获取指定地区的子地区
List<Area> cities = AreaUtils.getChildren(110000);
// 结果: 北京市的区列表
```

### 地区类型枚举

```java
public enum AreaTypeEnum {

    COUNTRY(0, "国家"),
    PROVINCE(1, "省份"),
    CITY(2, "城市"),
    DISTRICT(3, "区县"),
    ;

    private final Integer type;
    private final String name;
}
```

### 在 Excel 中使用地区转换

```java
@Data
public class UserExcelVO {

    @ExcelProperty(value = "所在地区", converter = AreaConvert.class)
    private Integer areaId;  // 存储地区编码，导出时自动转换为地区名称
}
```

## 内置数据说明

### IP 库

- 基于 [ip2region](https://gitee.com/lionsoul/ip2region) 实现
- 内置离线 IP 库，无需网络请求
- 支持 IPv4 地址解析
- 精确到城市级别

### 行政区划数据

- 基于 [Administrative-divisions-of-China](https://github.com/modood/Administrative-divisions-of-China) 实现
- 包含中国所有省、市、区县数据
- 使用国家统计局标准编码

## IPUtils 方法

| 方法 | 说明 |
|-----|------|
| `getArea(ip)` | 获取 IP 对应的地区对象 |
| `getAreaId(ip)` | 获取 IP 对应的地区编码 |
| `getAreaName(ip)` | 获取 IP 对应的格式化地区名称 |

## AreaUtils 方法

| 方法 | 说明 |
|-----|------|
| `getArea(id)` | 根据编码获取地区 |
| `format(id)` | 格式化地区名称（含父级） |
| `format(id, separator)` | 格式化地区名称（自定义分隔符） |
| `getByType(type)` | 获取指定类型的所有地区 |
| `getChildren(id)` | 获取子地区列表 |
| `getParents(id)` | 获取所有父级地区 |

## 依赖说明

### 核心依赖
- yudao-common
- ip2region（IP 解析库）
- Lombok
- SLF4J

## 相关文件清单

```
yudao-spring-boot-starter-biz-ip/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/ip/
    ├── core/
    │   ├── Area.java               # 地区实体
    │   ├── enums/
    │   │   └── AreaTypeEnum.java   # 地区类型枚举
    │   └── utils/
    │       ├── AreaUtils.java      # 地区工具类
    │       └── IPUtils.java        # IP 工具类
    └── package-info.java
```

## 资源文件

模块内置以下资源文件：
- `ip2region.xdb` - IP 离线库
- `area.csv` - 中国行政区划数据

## 使用场景

1. **用户登录日志** - 记录用户登录 IP 所在地区
2. **访问统计** - 统计访问来源地区分布
3. **地区选择器** - 省市区三级联动选择
4. **Excel 导入导出** - 地区编码与名称转换
5. **收货地址** - 地址中的地区信息处理

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
