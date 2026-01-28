[根目录](../CLAUDE.md) > **yudao-module-system**

# yudao-module-system - 系统核心模块

## 模块职责

system 模块提供通用业务功能，支撑上层的核心业务。主要包括：
- 用户管理（AdminUser）
- 部门管理（Dept）
- 岗位管理（Post）
- 角色权限（Role、Menu、Permission）
- 数据字典（DictType、DictData）
- 通知公告（Notice）
- 短信服务（Sms）
- 邮件服务（Mail）
- 站内信（Notify）
- OAuth2 认证
- 社交登录（Social）
- 多租户（Tenant）
- 操作日志（OperateLog、LoginLog）

## 模块统计

| 类型 | 数量 |
|-----|------|
| Controller | 47 |
| Service | 38 |
| Data Object | 31 |

## 入口与启动

本模块作为依赖被 yudao-server 引入，无独立启动入口。

### 包结构
```
cn.iocoder.yudao.module.system
├── api/         # 模块间 API 接口
├── controller/  # REST 控制器
│   ├── admin/   # 后台管理接口 (34个)
│   └── app/     # 移动端接口 (3个)
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
| /admin-api/system/auth | AuthController | 认证登录 |
| /admin-api/system/user | UserController | 用户管理 |
| /admin-api/system/user/profile | UserProfileController | 用户个人中心 |
| /admin-api/system/dept | DeptController | 部门管理 |
| /admin-api/system/post | PostController | 岗位管理 |
| /admin-api/system/role | RoleController | 角色管理 |
| /admin-api/system/menu | MenuController | 菜单管理 |
| /admin-api/system/permission | PermissionController | 权限分配 |
| /admin-api/system/dict-data | DictDataController | 字典数据 |
| /admin-api/system/dict-type | DictTypeController | 字典类型 |
| /admin-api/system/notice | NoticeController | 通知公告 |
| /admin-api/system/sms-channel | SmsChannelController | 短信渠道 |
| /admin-api/system/sms-template | SmsTemplateController | 短信模板 |
| /admin-api/system/sms-log | SmsLogController | 短信日志 |
| /admin-api/system/mail-account | MailAccountController | 邮箱账号 |
| /admin-api/system/mail-template | MailTemplateController | 邮件模板 |
| /admin-api/system/mail-log | MailLogController | 邮件日志 |
| /admin-api/system/notify-message | NotifyMessageController | 站内信消息 |
| /admin-api/system/notify-template | NotifyTemplateController | 站内信模板 |
| /admin-api/system/oauth2-client | OAuth2ClientController | OAuth2 客户端 |
| /admin-api/system/oauth2-token | OAuth2TokenController | OAuth2 令牌 |
| /admin-api/system/oauth2-user | OAuth2UserController | OAuth2 用户 |
| /admin-api/system/tenant | TenantController | 租户管理 |
| /admin-api/system/tenant-package | TenantPackageController | 租户套餐 |
| /admin-api/system/social-client | SocialClientController | 社交客户端 |
| /admin-api/system/social-user | SocialUserController | 社交用户 |
| /admin-api/system/login-log | LoginLogController | 登录日志 |
| /admin-api/system/operate-log | OperateLogController | 操作日志 |
| /admin-api/system/captcha | CaptchaController | 验证码 |
| /admin-api/system/area | AreaController | 地区 |

### App API (移动端)
| 路径 | 控制器 | 功能 |
|-----|--------|------|
| /app-api/system/dict-data | AppDictDataController | 字典数据 |
| /app-api/system/area | AppAreaController | 地区 |
| /app-api/system/tenant | AppTenantController | 租户 |

### 模块间 API
| 接口 | 实现类 | 功能 |
|-----|--------|------|
| AdminUserApi | AdminUserApiImpl | 用户查询 |
| DeptApi | DeptApiImpl | 部门查询 |
| PostApi | PostApiImpl | 岗位查询 |
| RoleApi | RoleApiImpl | 角色查询 |
| PermissionApi | PermissionApiImpl | 权限校验 |
| DictDataApi | DictDataApiImpl | 字典查询 |
| SmsSendApi | SmsSendApiImpl | 短信发送 |
| SmsCodeApi | SmsCodeApiImpl | 验证码 |
| MailSendApi | MailSendApiImpl | 邮件发送 |
| NotifyMessageSendApi | NotifyMessageSendApiImpl | 站内信发送 |
| OAuth2TokenApi | OAuth2TokenApiImpl | Token 管理 |
| SocialUserApi | SocialUserApiImpl | 社交用户 |
| SocialClientApi | SocialClientApiImpl | 社交客户端 |
| LoginLogApi | LoginLogApiImpl | 登录日志 |
| OperateLogApi | OperateLogApiImpl | 操作日志 |
| TenantApi | TenantApiImpl | 租户查询 |

## 关键依赖与配置

### 核心依赖
| 依赖 | 说明 |
|-----|------|
| yudao-module-infra | 基础设施模块 |
| yudao-spring-boot-starter-biz-data-permission | 数据权限 |
| yudao-spring-boot-starter-biz-tenant | 多租户 |
| yudao-spring-boot-starter-biz-ip | IP 地区 |
| yudao-spring-boot-starter-security | 安全认证 |
| yudao-spring-boot-starter-mybatis | 数据库访问 |
| yudao-spring-boot-starter-redis | 缓存 |
| yudao-spring-boot-starter-job | 定时任务 |
| yudao-spring-boot-starter-mq | 消息队列 |
| yudao-spring-boot-starter-excel | Excel 导出 |
| JustAuth | 社交登录 |
| wx-java-mp | 微信公众号 |
| wx-java-miniapp | 微信小程序 |
| captcha | 验证码 |

## 数据模型

### 核心表
| 表名 | 数据对象 | 说明 |
|-----|---------|------|
| system_users | AdminUserDO | 用户表 |
| system_dept | DeptDO | 部门表 |
| system_post | PostDO | 岗位表 |
| system_user_post | UserPostDO | 用户岗位关联 |
| system_role | RoleDO | 角色表 |
| system_menu | MenuDO | 菜单表 |
| system_role_menu | RoleMenuDO | 角色菜单关联 |
| system_user_role | UserRoleDO | 用户角色关联 |
| system_dict_type | DictTypeDO | 字典类型 |
| system_dict_data | DictDataDO | 字典数据 |
| system_notice | NoticeDO | 通知公告 |
| system_sms_channel | SmsChannelDO | 短信渠道 |
| system_sms_template | SmsTemplateDO | 短信模板 |
| system_sms_code | SmsCodeDO | 短信验证码 |
| system_sms_log | SmsLogDO | 短信日志 |
| system_mail_account | MailAccountDO | 邮箱账号 |
| system_mail_template | MailTemplateDO | 邮件模板 |
| system_mail_log | MailLogDO | 邮件日志 |
| system_notify_template | NotifyTemplateDO | 站内信模板 |
| system_notify_message | NotifyMessageDO | 站内信消息 |
| system_oauth2_client | OAuth2ClientDO | OAuth2 客户端 |
| system_oauth2_access_token | OAuth2AccessTokenDO | 访问令牌 |
| system_oauth2_refresh_token | OAuth2RefreshTokenDO | 刷新令牌 |
| system_oauth2_approve | OAuth2ApproveDO | 授权确认 |
| system_oauth2_code | OAuth2CodeDO | 授权码 |
| system_tenant | TenantDO | 租户 |
| system_tenant_package | TenantPackageDO | 租户套餐 |
| system_social_client | SocialClientDO | 社交客户端 |
| system_social_user | SocialUserDO | 社交用户 |
| system_social_user_bind | SocialUserBindDO | 社交用户绑定 |
| system_login_log | LoginLogDO | 登录日志 |
| system_operate_log | OperateLogDO | 操作日志 |

## 测试与质量

测试文件位于 `yudao-server/src/test/` 目录。

## 常见问题 (FAQ)

### Q: 如何添加新的权限点？
A: 1. 在 MenuController 添加菜单/按钮
   2. 在接口方法上添加 @PreAuthorize 注解

### Q: 如何自定义短信渠道？
A: 实现 SmsClient 接口，参考现有渠道实现

### Q: 如何配置社交登录？
A: 1. 在 system_social_client 表添加客户端配置
   2. 参考 JustAuth 文档配置各平台参数

## 相关文件清单

```
yudao-module-system/
├── pom.xml
└── src/main/java/cn/iocoder/yudao/module/system/
    ├── api/                    # 模块间 API
    ├── controller/
    │   ├── admin/              # 后台管理接口
    │   │   ├── auth/           # 认证
    │   │   ├── user/           # 用户
    │   │   ├── dept/           # 部门
    │   │   ├── permission/     # 权限
    │   │   ├── dict/           # 字典
    │   │   ├── sms/            # 短信
    │   │   ├── mail/           # 邮件
    │   │   ├── notify/         # 站内信
    │   │   ├── oauth2/         # OAuth2
    │   │   ├── tenant/         # 租户
    │   │   ├── socail/         # 社交
    │   │   └── logger/         # 日志
    │   └── app/                # 移动端接口
    ├── service/                # 业务服务
    ├── dal/                    # 数据访问
    │   ├── dataobject/         # 数据对象
    │   └── mysql/              # Mapper
    ├── convert/                # 对象转换
    ├── enums/                  # 枚举
    └── framework/              # 框架配置
```

## 变更记录 (Changelog)

### 2026-01-28
- 更新模块统计信息
- 完善 API 接口列表
- 添加 App API 文档

### 2026-01-23
- 初始化模块文档

---
*文档更新时间: 2026-01-28*
