[根目录](../../CLAUDE.md) > [yudao-framework](../CLAUDE.md) > **yudao-spring-boot-starter-excel**

# yudao-spring-boot-starter-excel - Excel 导入导出模块

## 模块职责

提供 Excel 导入导出功能，包括：
- Excel 导出（支持样式、下拉框）
- Excel 导入（支持校验）
- 字典转换
- 地区转换
- 金额转换

## 关键类说明

### 配置类

| 类名 | 说明 |
|-----|------|
| `YudaoDictAutoConfiguration` | 字典自动配置 |

### 工具类

| 类名 | 说明 |
|-----|------|
| `ExcelUtils` | Excel 导入导出工具类 |
| `DictFrameworkUtils` | 字典工具类 |

### 注解

| 注解 | 说明 |
|-----|------|
| `@DictFormat` | 字典格式化（导出时将值转换为字典标签） |
| `@ExcelColumnSelect` | 下拉框列（导出时生成下拉选项） |

### 转换器

| 类名 | 说明 |
|-----|------|
| `DictConvert` | 字典转换器 |
| `AreaConvert` | 地区转换器 |
| `MoneyConvert` | 金额转换器（分转元） |
| `JsonConvert` | JSON 转换器 |

### 处理器

| 类名 | 说明 |
|-----|------|
| `SelectSheetWriteHandler` | 下拉框 Sheet 写入处理器 |
| `ColumnWidthMatchStyleStrategy` | 列宽自适应策略 |
| `ExcelColumnSelectFunction` | 下拉框选项函数接口 |

### 字典验证

| 注解 | 说明 |
|-----|------|
| `@InDict` | 字典值验证 |
| `InDictValidator` | 字典验证器 |
| `InDictCollectionValidator` | 字典集合验证器 |

## 配置项

无特殊配置项，依赖字典数据服务提供字典数据。

## 使用示例

### 导出 Excel

**1. 定义导出 VO**

```java
@Data
public class UserExcelVO {

    @ExcelProperty("用户编号")
    private Long id;

    @ExcelProperty("用户名称")
    private String username;

    @ExcelProperty("手机号码")
    private String mobile;

    // 使用字典转换
    @ExcelProperty(value = "用户状态", converter = DictConvert.class)
    @DictFormat("system_common_status")  // 字典类型
    private Integer status;

    // 使用地区转换
    @ExcelProperty(value = "所在地区", converter = AreaConvert.class)
    private Integer areaId;

    // 使用金额转换（分转元）
    @ExcelProperty(value = "账户余额", converter = MoneyConvert.class)
    private Integer balance;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}
```

**2. 导出接口**

```java
@RestController
public class UserController {

    @GetMapping("/export")
    @Operation(summary = "导出用户列表")
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    public void exportUsers(@Valid UserExportReqVO reqVO,
                           HttpServletResponse response) throws IOException {
        // 查询数据
        List<UserDO> list = userService.getUsers(reqVO);
        // 转换数据
        List<UserExcelVO> data = BeanUtils.toBean(list, UserExcelVO.class);
        // 导出 Excel
        ExcelUtils.write(response, "用户列表.xlsx", "用户", UserExcelVO.class, data);
    }
}
```

### 导出带下拉框的 Excel

**1. 定义导出模板 VO**

```java
@Data
public class UserImportTemplateVO {

    @ExcelProperty("用户名称")
    private String username;

    @ExcelProperty("手机号码")
    private String mobile;

    // 下拉框 - 使用字典
    @ExcelProperty("用户状态")
    @ExcelColumnSelect(dictType = "system_common_status")
    private String status;

    // 下拉框 - 使用自定义函数
    @ExcelProperty("所属部门")
    @ExcelColumnSelect(functionClass = DeptSelectFunction.class)
    private String deptName;
}

// 自定义下拉选项函数
@Component
public class DeptSelectFunction implements ExcelColumnSelectFunction {

    @Autowired
    private DeptService deptService;

    @Override
    public List<String> getOptions() {
        return deptService.getDeptList().stream()
            .map(DeptDO::getName)
            .collect(Collectors.toList());
    }
}
```

**2. 导出模板**

```java
@GetMapping("/import-template")
@Operation(summary = "下载用户导入模板")
public void importTemplate(HttpServletResponse response) throws IOException {
    // 导出空数据的 Excel（带下拉框）
    ExcelUtils.write(response, "用户导入模板.xlsx", "用户",
        UserImportTemplateVO.class, Collections.emptyList());
}
```

### 导入 Excel

**1. 定义导入 VO**

```java
@Data
public class UserImportVO {

    @ExcelProperty("用户名称")
    @NotEmpty(message = "用户名称不能为空")
    private String username;

    @ExcelProperty("手机号码")
    @Mobile
    private String mobile;

    @ExcelProperty("用户状态")
    @InDict("system_common_status")  // 字典值验证
    private String status;
}
```

**2. 导入接口**

```java
@PostMapping("/import")
@Operation(summary = "导入用户")
@PreAuthorize("@ss.hasPermission('system:user:import')")
public CommonResult<UserImportRespVO> importUsers(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "updateSupport", defaultValue = "false") Boolean updateSupport)
        throws IOException {
    // 读取 Excel
    List<UserImportVO> list = ExcelUtils.read(file, UserImportVO.class);
    // 导入数据
    return success(userService.importUsers(list, updateSupport));
}
```

### 字典工具使用

```java
// 根据字典类型和值获取标签
String label = DictFrameworkUtils.getDictDataLabel("system_common_status", 1);
// 结果: "开启"

// 根据字典类型和标签获取值
String value = DictFrameworkUtils.parseDictDataValue("system_common_status", "开启");
// 结果: "1"
```

## ExcelUtils 方法说明

| 方法 | 说明 |
|-----|------|
| `write(response, filename, sheetName, head, data)` | 导出 Excel |
| `read(file, head)` | 读取 Excel |
| `read(file, head, isValidate)` | 读取 Excel（可选校验） |

## 注解说明

### @DictFormat

| 属性 | 类型 | 说明 |
|-----|------|------|
| `value` | String | 字典类型 |

### @ExcelColumnSelect

| 属性 | 类型 | 说明 |
|-----|------|------|
| `dictType` | String | 字典类型（使用字典数据作为下拉选项） |
| `functionClass` | Class | 自定义选项函数类 |

### @InDict

| 属性 | 类型 | 说明 |
|-----|------|------|
| `value` | String | 字典类型 |
| `message` | String | 错误提示 |

## 依赖说明

### 核心依赖
- FastExcel（高性能 Excel 库）
- yudao-common
- Guava

### 可选依赖
- yudao-spring-boot-starter-biz-ip（地区转换使用）

## 相关文件清单

```
yudao-spring-boot-starter-excel/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/framework/
    ├── dict/                       # 字典
    │   ├── config/
    │   ├── core/
    │   └── validation/
    └── excel/                      # Excel
        └── core/
            ├── annotations/        # 注解
            ├── convert/            # 转换器
            ├── function/           # 下拉函数
            ├── handler/            # 写入处理器
            └── util/               # 工具类
```

## 变更记录 (Changelog)

### 2026-01-28
- 初始化子模块文档

---
*文档生成时间: 2026-01-28*
