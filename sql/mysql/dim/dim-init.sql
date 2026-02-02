-- =============================================
-- DIM 模块数据库初始化脚本
-- 涿州综合管理系统业务表
-- =============================================

-- ========== 1. 访客管理 ==========

-- 访客信息表
CREATE TABLE `dim_visitor`
(
    `id`                bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`              tinyint           DEFAULT NULL COMMENT '人员类型（字典：dim_visitor_type）',
    `name`              varchar(50)       DEFAULT NULL COMMENT '姓名',
    `gender`            varchar(10)       DEFAULT NULL COMMENT '性别',
    `birthday`          date              DEFAULT NULL COMMENT '出生日期',
    `phone`             varchar(20)       DEFAULT NULL COMMENT '联系电话',
    `unit_name`         varchar(255)      DEFAULT NULL COMMENT '单位名称',
    `id_num`            varchar(50)       DEFAULT NULL COMMENT '证件号码',
    `img_info`          varchar(255)      DEFAULT NULL COMMENT '人像信息(路径)',
    `car_info`          varchar(255)      DEFAULT NULL COMMENT '车辆信息',
    `visitor_num`       int               DEFAULT 1 COMMENT '随行人数',
    `visiting_unit`     varchar(255)      DEFAULT NULL COMMENT '被访单位',
    `interviewee_id`    bigint            DEFAULT NULL COMMENT '被访人ID',
    `interviewee`       varchar(255)      DEFAULT NULL COMMENT '被访人姓名',
    `interviewee_phone` varchar(255)      DEFAULT NULL COMMENT '被访人电话',
    `reg_date`          datetime          DEFAULT NULL COMMENT '登记日期',
    `effective_date`    datetime          DEFAULT NULL COMMENT '有效日期',
    `departure_date`    datetime          DEFAULT NULL COMMENT '离场日期',
    `card_num`          varchar(50)       DEFAULT NULL COMMENT '出入证卡号',
    `car_card_num`      varchar(50)       DEFAULT NULL COMMENT '车辆通行证号',
    `dining_num`        varchar(50)       DEFAULT NULL COMMENT '就餐卡号',
    `remarks`           varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`           varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                 `idx_phone` (`phone`),
    KEY                 `idx_id_num` (`id_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='访客信息表';

-- 来访日志表
CREATE TABLE `dim_visit_log`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `visitor_id`  bigint   NOT NULL COMMENT '访客ID',
    `status`      tinyint           DEFAULT 0 COMMENT '状态（字典：dim_visit_status）：0来访,1离场',
    `visit_date`  datetime          DEFAULT NULL COMMENT '来访时间',
    `leave_date`  datetime          DEFAULT NULL COMMENT '离场时间',
    `purpose`     varchar(500)      DEFAULT NULL COMMENT '来访事由',
    `remarks`     varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_visitor_id` (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='来访日志表';

-- 预约访客表
CREATE TABLE `dim_reservation_visitor`
(
    `id`                   bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`                 tinyint           DEFAULT NULL COMMENT '人员类型（字典：dim_visitor_type）',
    `name`                 varchar(50)       DEFAULT NULL COMMENT '姓名',
    `gender`               varchar(10)       DEFAULT NULL COMMENT '性别',
    `birthday`             date              DEFAULT NULL COMMENT '出生日期',
    `phone`                varchar(20)       DEFAULT NULL COMMENT '联系电话',
    `unit_name`            varchar(255)      DEFAULT NULL COMMENT '单位名称',
    `id_num`               varchar(50)       DEFAULT NULL COMMENT '证件号码',
    `img_info`             varchar(255)      DEFAULT NULL COMMENT '人像信息(路径)',
    `car_info`             varchar(255)      DEFAULT NULL COMMENT '车辆信息',
    `visitor_num`          int               DEFAULT 1 COMMENT '随行人数',
    `visiting_unit`        varchar(255)      DEFAULT NULL COMMENT '被访单位',
    `interviewee_id`       bigint            DEFAULT NULL COMMENT '被访人ID',
    `interviewee`          varchar(255)      DEFAULT NULL COMMENT '被访人姓名',
    `interviewee_phone`    varchar(255)      DEFAULT NULL COMMENT '被访人电话',
    `start_effective_date` datetime          DEFAULT NULL COMMENT '有效期开始时间',
    `end_effective_date`   datetime          DEFAULT NULL COMMENT '有效期结束时间',
    `purpose`              varchar(500)      DEFAULT NULL COMMENT '来访事由',
    `status`               tinyint           DEFAULT 0 COMMENT '状态（字典：dim_reservation_status）：0待来访,1已来访,2已过期,3已取消',
    `card_num`             varchar(50)       DEFAULT NULL COMMENT '出入证卡号',
    `car_card_num`         varchar(50)       DEFAULT NULL COMMENT '车辆通行证号',
    `dining_num`           varchar(50)       DEFAULT NULL COMMENT '就餐卡号',
    `remarks`              varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`              varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`              varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`              bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                    `idx_phone` (`phone`),
    KEY                    `idx_id_num` (`id_num`),
    KEY                    `idx_status` (`status`),
    KEY                    `idx_effective_date` (`start_effective_date`, `end_effective_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预约访客表';

-- 访客区域表
CREATE TABLE `dim_visitor_area`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `area_name`   varchar(100) NOT NULL COMMENT '区域名称',
    `area_ip`     varchar(50)           DEFAULT NULL COMMENT '区域IP',
    `remarks`     varchar(500)          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint       NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='访客区域表';

-- 访客卡关联表
CREATE TABLE `dim_visitor_card`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `visitor_id`  bigint   NOT NULL COMMENT '访客ID',
    `card_id`     varchar(100)      DEFAULT NULL COMMENT '卡号',
    `qr_code`     varchar(255)      DEFAULT NULL COMMENT '二维码',
    `area_id`     bigint            DEFAULT NULL COMMENT '区域ID',
    `creator`     varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint   NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY           `idx_visitor_id` (`visitor_id`),
    KEY           `idx_card_id` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='访客卡关联表';

-- ========== 2. 住宿管理 ==========

-- 楼栋管理表
CREATE TABLE `dim_building`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(100) NOT NULL COMMENT '楼栋名称',
    `code`        varchar(50)           DEFAULT NULL COMMENT '楼栋编号',
    `floor_count` int                   DEFAULT 0 COMMENT '楼层数',
    `address`     varchar(255)          DEFAULT NULL COMMENT '地址',
    `status`      tinyint               DEFAULT 0 COMMENT '状态：0正常,1停用',
    `sort`        int                   DEFAULT 0 COMMENT '排序',
    `remarks`     varchar(500)          DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楼栋管理表';

-- 楼层管理表
CREATE TABLE `dim_floor`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `building_id`  bigint       NOT NULL COMMENT '楼栋ID',
    `name`         varchar(100) NOT NULL COMMENT '楼层名称',
    `floor_number` int                   DEFAULT 1 COMMENT '楼层编号',
    `room_count`   int                   DEFAULT 0 COMMENT '房间数量',
    `sort`         int                   DEFAULT 0 COMMENT '排序',
    `status`       tinyint               DEFAULT 0 COMMENT '状态：0正常,1停用',
    `remarks`      varchar(500)          DEFAULT NULL COMMENT '备注',
    `creator`      varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY            `idx_building_id` (`building_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楼层管理表';

-- 房间管理表
CREATE TABLE `dim_room`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `floor_id`    bigint      NOT NULL COMMENT '楼层ID',
    `room_number` varchar(20) NOT NULL COMMENT '房间号',
    `room_type`   tinyint              DEFAULT 0 COMMENT '房间类型：0单人间,1双人间,2多人间',
    `bed_count`   int                  DEFAULT 1 COMMENT '床位数',
    `area`        decimal(10, 2)       DEFAULT NULL COMMENT '面积(平方米)',
    `price`       decimal(10, 2)       DEFAULT 0.00 COMMENT '单价（元/天）',
    `status`      tinyint              DEFAULT 0 COMMENT '状态：0空闲,1入住,2维修,3预留',
    `facilities`  varchar(500)         DEFAULT NULL COMMENT '设施配置(JSON格式)',
    `remarks`     varchar(500)         DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)          DEFAULT '' COMMENT '创建者',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)          DEFAULT '' COMMENT '更新者',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_floor_id` (`floor_id`),
    UNIQUE KEY    `uk_room_number` (`room_number`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房间管理表';

-- 住宿人员表
CREATE TABLE `dim_room_guest`
(
    `id`                      bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `room_id`                 bigint   NOT NULL COMMENT '房间ID',
    `visitor_id`              bigint            DEFAULT NULL COMMENT '访客ID',
    `guest_name`              varchar(50)       DEFAULT NULL COMMENT '住客姓名',
    `id_type`                 tinyint           DEFAULT 0 COMMENT '证件类型：0身份证,1军官证,2护照,3其他',
    `id_number`               varchar(50)       DEFAULT NULL COMMENT '证件号码',
    `phone`                   varchar(20)       DEFAULT NULL COMMENT '联系电话',
    `dept_name`               varchar(100)      DEFAULT NULL COMMENT '单位/部门',
    `check_in_date`           datetime NOT NULL COMMENT '入住日期',
    `expected_check_out_date` datetime          DEFAULT NULL COMMENT '预计离店日期',
    `actual_check_out_date`   datetime          DEFAULT NULL COMMENT '实际离店日期',
    `status`                  tinyint           DEFAULT 0 COMMENT '状态：0在住,1已退房,2预约中,3已取消',
    `remarks`                 varchar(500)      DEFAULT NULL COMMENT '备注',
    `primary_guest_id`        bigint            DEFAULT NULL COMMENT '主住客ID（同住人用，主住客为null）',
    `is_primary`              tinyint(1)        DEFAULT 1 COMMENT '是否主住客（1=主住客，0=同住人）',
    `creator`                 varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`             datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                 varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`             datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                 bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`               bigint   NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY                       `idx_room_id` (`room_id`),
    KEY                       `idx_visitor_id` (`visitor_id`),
    KEY                       `idx_primary_guest_id` (`primary_guest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住宿人员表';

-- 住宿费用结算表
CREATE TABLE `dim_stay_settlement`
(
    `id`             bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `guest_id`       bigint         NOT NULL COMMENT '住客记录ID',
    `room_id`        bigint         NOT NULL COMMENT '房间ID',
    `check_in_date`  datetime       NOT NULL COMMENT '入住日期',
    `check_out_date` datetime       NOT NULL COMMENT '退房日期',
    `stay_days`      int            NOT NULL DEFAULT 1 COMMENT '住宿天数',
    `room_price`     decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '房间单价',
    `total_amount`   decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '总金额',
    `payment_status` tinyint        NOT NULL DEFAULT 0 COMMENT '支付状态（0-未结算 1-已结算）',
    `payment_time`   datetime                DEFAULT NULL COMMENT '结算时间',
    `invoice_status` tinyint        NOT NULL DEFAULT 0 COMMENT '开票状态（0-未开票 1-已开票）',
    `invoice_time`   datetime                DEFAULT NULL COMMENT '开票时间',
    `remarks`        varchar(500)            DEFAULT NULL COMMENT '备注',
    `creator`        varchar(64)             DEFAULT '' COMMENT '创建者',
    `create_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64)             DEFAULT '' COMMENT '更新者',
    `update_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint         NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY              `idx_guest_id` (`guest_id`),
    KEY              `idx_room_id` (`room_id`),
    KEY              `idx_check_out_date` (`check_out_date`),
    KEY              `idx_payment_status` (`payment_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住宿费用结算表';

-- 住宿日志表
CREATE TABLE `dim_stay_log`
(
    `id`            bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `room_id`       bigint   NOT NULL COMMENT '房间ID',
    `visitor_id`    bigint   NOT NULL COMMENT '访客ID',
    `type`          tinyint           DEFAULT 0 COMMENT '类型：0住宿费,1营具租用',
    `price`         decimal(10, 3)    DEFAULT 0.00 COMMENT '单价',
    `stay_days`     int               DEFAULT 0 COMMENT '天数',
    `amount`        decimal(10, 3)    DEFAULT 0.00 COMMENT '金额',
    `fee_status`    tinyint           DEFAULT 0 COMMENT '费用状态：0未结算,1挂账,9已结算',
    `checkin_date`  datetime          DEFAULT NULL COMMENT '入住日期',
    `checkout_date` datetime          DEFAULT NULL COMMENT '退房日期',
    `remarks`       varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`       varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY             `idx_room_id` (`room_id`),
    KEY             `idx_visitor_id` (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住宿日志表';

-- ========== 3. 餐饮管理 ==========

-- 餐饮设置表
CREATE TABLE `dim_dining`
(
    `id`          bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(50)    NOT NULL COMMENT '餐类名称',
    `meal_type`   tinyint        NOT NULL COMMENT '餐类类型（字典：dim_meal_type）：0早餐,1午餐,2晚餐',
    `price`       decimal(10, 2) DEFAULT NULL COMMENT '单价(元)',
    `start_time`  time           DEFAULT NULL COMMENT '开始时间',
    `end_time`    time           DEFAULT NULL COMMENT '结束时间',
    `status`      tinyint        NOT NULL DEFAULT 0 COMMENT '状态：0启用,1停用',
    `remark`      varchar(500)   DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)    DEFAULT '' COMMENT '创建者',
    `create_time` datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)    DEFAULT '' COMMENT '更新者',
    `update_time` datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='餐饮设置表';

-- 就餐记录表（匹配 DiningRecordDO）
CREATE TABLE `dim_dining_record`
(
    `id`              bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`         bigint                  DEFAULT NULL COMMENT '就餐人ID',
    `user_name`       varchar(100)            DEFAULT NULL COMMENT '就餐人姓名',
    `dept_name`       varchar(100)            DEFAULT NULL COMMENT '就餐人部门',
    `dining_date`     date           NOT NULL COMMENT '就餐日期',
    `meal_type`       tinyint                 DEFAULT NULL COMMENT '餐别（0-早餐 1-午餐 2-晚餐）',
    `pay_type`        tinyint                 DEFAULT NULL COMMENT '就餐类型（0-刷卡 1-现金 2-记账）',
    `amount`          decimal(10, 2)          DEFAULT NULL COMMENT '金额',
    `remarks`         varchar(500)            DEFAULT NULL COMMENT '备注',
    `person_type`     tinyint                 DEFAULT NULL COMMENT '人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）',
    `card_id`         varchar(64)             DEFAULT NULL COMMENT '餐卡编号',
    `dining_class`    tinyint                 DEFAULT NULL COMMENT '分类（0-客餐 1-桌餐）',
    `registration_id` bigint                  DEFAULT NULL COMMENT '关联报餐登记ID',
    `settlement_id`   bigint                  DEFAULT NULL COMMENT '关联结算单ID',
    `creator`         varchar(64)             DEFAULT '' COMMENT '创建者',
    `create_time`     datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64)             DEFAULT '' COMMENT '更新者',
    `update_time`     datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`       bigint         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY               `idx_user_id` (`user_id`),
    KEY               `idx_dining_date` (`dining_date`),
    KEY               `idx_person_type` (`person_type`),
    KEY               `idx_card_id` (`card_id`),
    KEY               `idx_dining_class` (`dining_class`),
    KEY               `idx_registration_id` (`registration_id`),
    KEY               `idx_settlement_id` (`settlement_id`),
    KEY               `idx_card_date_meal` (`card_id`, `dining_date`, `meal_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='就餐记录表';

-- 报餐登记表（匹配 DiningRegistrationDO）
CREATE TABLE `dim_dining_registration`
(
    `id`                bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`           bigint                  DEFAULT NULL COMMENT '报餐人ID',
    `user_name`         varchar(100)            DEFAULT NULL COMMENT '报餐人姓名',
    `dept_name`         varchar(100)            DEFAULT NULL COMMENT '报餐人部门',
    `registration_date` date           NOT NULL COMMENT '报餐日期',
    `meal_type`         tinyint                 DEFAULT NULL COMMENT '餐别（0-早餐 1-午餐 2-晚餐）',
    `guest_count`       int                     DEFAULT 1 COMMENT '人数',
    `status`            tinyint                 DEFAULT 0 COMMENT '状态（0-待确认 1-已确认 2-已取消）',
    `remarks`           varchar(500)            DEFAULT NULL COMMENT '备注',
    `card_id`           varchar(64)             DEFAULT NULL COMMENT '餐卡编号',
    `used`              tinyint                 DEFAULT 0 COMMENT '是否已就餐（0-未就餐 1-已就餐）',
    `dining_class`      tinyint                 DEFAULT NULL COMMENT '分类（0-客餐 1-桌餐）',
    `settlement_id`     bigint                  DEFAULT NULL COMMENT '关联结算单ID',
    `is_paid`           varchar(10)             DEFAULT 'N' COMMENT '是否已支付',
    `is_invoiced`       varchar(10)             DEFAULT 'N' COMMENT '是否已开票',
    `receptionist`      varchar(100)            DEFAULT NULL COMMENT '接待人',
    `person_type`       tinyint                 DEFAULT NULL COMMENT '人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）',
    `creator`           varchar(64)             DEFAULT '' COMMENT '创建者',
    `create_time`       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64)             DEFAULT '' COMMENT '更新者',
    `update_time`       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY                 `idx_user_id` (`user_id`),
    KEY                 `idx_registration_date` (`registration_date`),
    KEY                 `idx_card_id` (`card_id`),
    KEY                 `idx_used` (`used`),
    KEY                 `idx_dining_class` (`dining_class`),
    KEY                 `idx_settlement_id` (`settlement_id`),
    KEY                 `idx_is_paid` (`is_paid`),
    KEY                 `idx_is_invoiced` (`is_invoiced`),
    KEY                 `idx_person_type` (`person_type`),
    KEY                 `idx_card_date_meal` (`card_id`, `registration_date`, `meal_type`),
    KEY                 `idx_settle_condition` (`used`, `settlement_id`, `registration_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报餐登记表';

-- 餐饮结算表（匹配 DiningSettlementDO）
CREATE TABLE `dim_dining_settlement`
(
    `id`              bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`         bigint                  DEFAULT NULL COMMENT '结算人ID',
    `user_name`       varchar(100)            DEFAULT NULL COMMENT '结算人姓名',
    `dept_name`       varchar(100)            DEFAULT NULL COMMENT '结算人部门',
    `start_date`      date           NOT NULL COMMENT '结算开始日期',
    `end_date`        date           NOT NULL COMMENT '结算结束日期',
    `breakfast_count` int                     DEFAULT 0 COMMENT '早餐次数',
    `lunch_count`     int                     DEFAULT 0 COMMENT '午餐次数',
    `dinner_count`    int                     DEFAULT 0 COMMENT '晚餐次数',
    `total_amount`    decimal(10, 2)          DEFAULT 0.00 COMMENT '总金额',
    `paid_amount`     decimal(10, 2)          DEFAULT 0.00 COMMENT '已付金额',
    `status`          tinyint                 DEFAULT 0 COMMENT '状态（0-未结算 1-已结算）',
    `remarks`         varchar(500)            DEFAULT NULL COMMENT '备注',
    `is_paid`         varchar(10)             DEFAULT 'N' COMMENT '是否已支付',
    `is_invoiced`     varchar(10)             DEFAULT 'N' COMMENT '是否已开票',
    `creator`         varchar(64)             DEFAULT '' COMMENT '创建者',
    `create_time`     datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64)             DEFAULT '' COMMENT '更新者',
    `update_time`     datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`       bigint         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY               `idx_user_id` (`user_id`),
    KEY               `idx_is_paid` (`is_paid`),
    KEY               `idx_is_invoiced` (`is_invoiced`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='餐饮结算表';

-- 餐饮价格配置表
CREATE TABLE `dim_dining_price`
(
    `id`          bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `person_type` tinyint        NOT NULL COMMENT '人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）',
    `meal_type`   tinyint        NOT NULL COMMENT '餐别（0-早餐 1-午餐 2-晚餐）',
    `dining_class` tinyint       NOT NULL COMMENT '分类（0-客餐 1-桌餐）',
    `price`       decimal(10, 2) NOT NULL DEFAULT '0.00' COMMENT '单价(元)',
    `status`      tinyint        NOT NULL DEFAULT '0' COMMENT '状态（0-启用 1-停用）',
    `remarks`     varchar(500)            DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)             DEFAULT '' COMMENT '创建者',
    `create_time` datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)             DEFAULT '' COMMENT '更新者',
    `update_time` datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`   bigint         NOT NULL DEFAULT '0' COMMENT '租户编号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_person_meal_class` (`person_type`, `meal_type`, `dining_class`, `deleted`, `tenant_id`) COMMENT '人员类型+餐别+分类唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='餐饮价格配置表';

-- ========== 4. 物资管理 ==========

-- 库存分类表
CREATE TABLE `dim_inventory_category`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`   bigint                DEFAULT 0 COMMENT '父级ID',
    `name`        varchar(100) NOT NULL COMMENT '分类名称',
    `type`        tinyint               DEFAULT 0 COMMENT '分类类型：0-通用 1-物资 2-耗材',
    `sort`        int                   DEFAULT 0 COMMENT '排序',
    `status`      tinyint               DEFAULT 0 COMMENT '状态：0-正常 1-停用',
    `creator`     varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_parent_id` (`parent_id`),
    KEY           `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存分类表';

-- 库存物品表
CREATE TABLE `dim_inventory_item`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`            varchar(50)           DEFAULT NULL COMMENT '物品编码',
    `name`            varchar(100) NOT NULL COMMENT '物品名称',
    `type`            tinyint      NOT NULL COMMENT '物品类型：1-物资 2-耗材',
    `category_id`     bigint                DEFAULT NULL COMMENT '分类ID',
    `warehouse_id`    bigint                DEFAULT NULL COMMENT '仓库ID',
    `spec`            varchar(100)          DEFAULT NULL COMMENT '规格型号',
    `unit`            varchar(20)           DEFAULT NULL COMMENT '计量单位',
    `brand`           varchar(100)          DEFAULT NULL COMMENT '品牌',
    `manufacturer`    varchar(200)          DEFAULT NULL COMMENT '生产厂家',
    `quantity`        decimal(12, 2)        DEFAULT 0.00 COMMENT '当前库存量',
    `locked_quantity` decimal(12, 2)        DEFAULT 0.00 COMMENT '锁定库存量',
    `min_quantity`    decimal(12, 2)        DEFAULT 0.00 COMMENT '最小库存预警值',
    `max_quantity`    decimal(12, 2)        DEFAULT 0.00 COMMENT '最大库存预警值',
    `unit_price`      decimal(12, 3)        DEFAULT NULL COMMENT '单价',
    `total_amount`    decimal(12, 3)        DEFAULT NULL COMMENT '库存金额',
    `status`          tinyint               DEFAULT 0 COMMENT '状态：0-正常 1-停用',
    `remark`          varchar(500)          DEFAULT NULL COMMENT '备注',
    `image_url`       varchar(500)          DEFAULT NULL COMMENT '图片URL',
    `creator`         varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY               `idx_category_id` (`category_id`),
    KEY               `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存物品表';

-- 库存出入库日志表
CREATE TABLE `dim_inventory_log`
(
    `id`               bigint         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id`          bigint         NOT NULL COMMENT '物品ID',
    `batch_id`         bigint                  DEFAULT NULL COMMENT '批次ID',
    `warehouse_id`     bigint                  DEFAULT NULL COMMENT '仓库ID',
    `operation_type`   tinyint        NOT NULL COMMENT '操作类型：1-采购入库 2-盘盈入库 3-领用出库 4-盘亏出库 5-调拨入库 6-调拨出库',
    `operation_no`     varchar(50)             DEFAULT NULL COMMENT '操作单号',
    `quantity`         decimal(12, 2) NOT NULL COMMENT '操作数量',
    `unit_price`       decimal(12, 3)          DEFAULT NULL COMMENT '单价（折扣后）',
    `original_price`   decimal(12, 3)          DEFAULT NULL COMMENT '原价（折扣前）',
    `discount_rate`    decimal(5, 2)           DEFAULT NULL COMMENT '折扣率(%)',
    `total_amount`     decimal(12, 3)          DEFAULT NULL COMMENT '金额',
    `before_quantity`  decimal(12, 2)          DEFAULT NULL COMMENT '操作前库存',
    `after_quantity`   decimal(12, 2)          DEFAULT NULL COMMENT '操作后库存',
    `audit_status`     tinyint                 DEFAULT 0 COMMENT '审核状态：0-待审核 1-已通过 2-已驳回',
    `audit_user_id`    bigint                  DEFAULT NULL COMMENT '审核人ID',
    `audit_time`       datetime                DEFAULT NULL COMMENT '审核时间',
    `audit_remark`     varchar(500)            DEFAULT NULL COMMENT '审核备注',
    `applicant_id`     bigint                  DEFAULT NULL COMMENT '申请人ID',
    `applicant_dept_id` bigint                 DEFAULT NULL COMMENT '申请部门ID',
    `purpose`          varchar(500)            DEFAULT NULL COMMENT '用途',
    `usage_location`   varchar(200)            DEFAULT NULL COMMENT '使用地点',
    `remark`           varchar(500)            DEFAULT NULL COMMENT '备注',
    `creator`          varchar(64)             DEFAULT '' COMMENT '创建者',
    `create_time`      datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64)             DEFAULT '' COMMENT '更新者',
    `update_time`      datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                `idx_item_id` (`item_id`),
    KEY                `idx_batch_id` (`batch_id`),
    KEY                `idx_operation_type` (`operation_type`),
    KEY                `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存出入库日志表';

-- 库存月度汇总表
CREATE TABLE `dim_inventory_monthly`
(
    `id`             bigint     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id`        bigint     NOT NULL COMMENT '物品ID',
    `year_month`     varchar(7) NOT NULL COMMENT '年月（YYYY-MM）',
    `opening_qty`    decimal(12, 2)      DEFAULT 0.00 COMMENT '期初数量',
    `opening_amount` decimal(12, 3)      DEFAULT 0.00 COMMENT '期初金额',
    `in_qty`         decimal(12, 2)      DEFAULT 0.00 COMMENT '入库数量',
    `in_amount`      decimal(12, 3)      DEFAULT 0.00 COMMENT '入库金额',
    `out_qty`        decimal(12, 2)      DEFAULT 0.00 COMMENT '出库数量',
    `out_amount`     decimal(12, 3)      DEFAULT 0.00 COMMENT '出库金额',
    `closing_qty`    decimal(12, 2)      DEFAULT 0.00 COMMENT '期末数量',
    `closing_amount` decimal(12, 3)      DEFAULT 0.00 COMMENT '期末金额',
    `creator`        varchar(64)         DEFAULT '' COMMENT '创建者',
    `create_time`    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64)         DEFAULT '' COMMENT '更新者',
    `update_time`    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)     NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_item_month` (`item_id`, `year_month`, `deleted`),
    KEY              `idx_year_month` (`year_month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存月度汇总表';
-- 库存批次表 (用于FIFO算法)
CREATE TABLE `dim_inventory_batch`
(
    `id`              bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id`         bigint   NOT NULL COMMENT '物品ID',
    `batch_no`        varchar(50)       DEFAULT NULL COMMENT '批次号',
    `quantity`        decimal(12, 2)    DEFAULT 0.00 COMMENT '批次库存量',
    `unit_price`      decimal(12, 3)    DEFAULT NULL COMMENT '入库单价',
    `production_date` date              DEFAULT NULL COMMENT '生产日期',
    `expiry_date`     date              DEFAULT NULL COMMENT '有效期至',
    `supplier`        varchar(255)      DEFAULT NULL COMMENT '供应商',
    `status`          tinyint           DEFAULT 0 COMMENT '状态：0-正常 1-已清空',
    `creator`         varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY               `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存批次表';
-- ========== 5. 资产管理 ==========

-- 资产分类表
CREATE TABLE `dim_asset_category`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`   bigint                DEFAULT 0 COMMENT '父级ID',
    `name`        varchar(100) NOT NULL COMMENT '分类名称',
    `sort`        int                   DEFAULT 0 COMMENT '排序',
    `status`      tinyint               DEFAULT 1 COMMENT '状态：0禁用,1启用',
    `creator`     varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产分类表';

-- 资产信息表
CREATE TABLE `dim_asset`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id`      bigint                DEFAULT NULL COMMENT '分类ID',
    `name`             varchar(100) NOT NULL COMMENT '资产名称',
    `code`             varchar(100)          DEFAULT NULL COMMENT '资产编号',
    `measurement_unit` varchar(20)           DEFAULT NULL COMMENT '计量单位',
    `inventory`        int                   DEFAULT 0 COMMENT '库存数量',
    `price`            decimal(12, 3)        DEFAULT NULL COMMENT '单价',
    `purchase_date`    date                  DEFAULT NULL COMMENT '购置日期',
    `status`           tinyint               DEFAULT 1 COMMENT '状态（字典：dim_asset_status）：0禁用,1启用',
    `creator`          varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                `idx_category_id` (`category_id`),
    KEY                `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产信息表';

-- 资产持有表
CREATE TABLE `dim_asset_holder`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `asset_id`    bigint   NOT NULL COMMENT '资产ID',
    `quantity`    int               DEFAULT 1 COMMENT '持有数量',
    `holder_id`   bigint            DEFAULT NULL COMMENT '持有人ID（关联访客）',
    `holder_name` varchar(100)      DEFAULT NULL COMMENT '持有人姓名',
    `dept_id`     bigint            DEFAULT NULL COMMENT '部门ID',
    `building_no` int               DEFAULT NULL COMMENT '楼号',
    `floor`       int               DEFAULT NULL COMMENT '楼层',
    `position`    varchar(255)      DEFAULT NULL COMMENT '具体位置',
    `remarks`     varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_asset_id` (`asset_id`),
    KEY           `idx_holder_id` (`holder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产持有表';

-- 资产操作日志表
CREATE TABLE `dim_asset_log`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `asset_id`    bigint   NOT NULL COMMENT '资产ID',
    `type`        tinyint  NOT NULL COMMENT '操作类型：0入库,1出库,2调拨,3报废',
    `quantity`    int               DEFAULT 0 COMMENT '数量',
    `operator_id` bigint            DEFAULT NULL COMMENT '操作人ID',
    `remarks`     varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_asset_id` (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产操作日志表';

-- 资产外借表
CREATE TABLE `dim_asset_borrow`
(
    `id`                   bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `asset_id`             bigint   NOT NULL COMMENT '资产ID',
    `quantity`             int               DEFAULT 1 COMMENT '外借数量',
    `borrower_id`          bigint            DEFAULT NULL COMMENT '借用人ID',
    `borrower_name`        varchar(100)      DEFAULT NULL COMMENT '借用人姓名',
    `borrow_date`          datetime NOT NULL COMMENT '借出日期',
    `expected_return_date` datetime          DEFAULT NULL COMMENT '预计归还日期',
    `return_date`          datetime          DEFAULT NULL COMMENT '实际归还日期',
    `status`               tinyint           DEFAULT 0 COMMENT '状态：0待审核,1已借出,2已归还',
    `remarks`              varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`              varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`              varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`              bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                    `idx_asset_id` (`asset_id`),
    KEY                    `idx_borrower_id` (`borrower_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产外借表';

-- 资产采购表
CREATE TABLE `dim_asset_purchase`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`                varchar(50)           DEFAULT NULL COMMENT '采购单号',
    `title`               varchar(200) NOT NULL COMMENT '采购标题',
    `content`             text                  COMMENT '采购内容/说明',
    `total_amount`        decimal(12, 2)        DEFAULT NULL COMMENT '采购总金额',
    `status`              tinyint      NOT NULL DEFAULT 0 COMMENT '状态：0待提交,1待审核,2已通过,3已拒绝,4已入库',
    `applicant_id`        bigint                DEFAULT NULL COMMENT '申请人ID',
    `applicant_name`      varchar(100)          DEFAULT NULL COMMENT '申请人姓名',
    `applicant_dept_id`   bigint                DEFAULT NULL COMMENT '申请部门ID',
    `apply_time`          datetime              DEFAULT NULL COMMENT '申请时间',
    `auditor_id`          bigint                DEFAULT NULL COMMENT '审核人ID',
    `auditor_name`        varchar(100)          DEFAULT NULL COMMENT '审核人姓名',
    `audit_time`          datetime              DEFAULT NULL COMMENT '审核时间',
    `audit_remark`        varchar(500)          DEFAULT NULL COMMENT '审核备注',
    `process_instance_id` varchar(64)           DEFAULT NULL COMMENT '流程实例ID（预留）',
    `creator`             varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                   `idx_code` (`code`),
    KEY                   `idx_status` (`status`),
    KEY                   `idx_applicant_id` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产采购表';

-- 资产采购明细表
CREATE TABLE `dim_asset_purchase_item`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `purchase_id`      bigint       NOT NULL COMMENT '采购单ID',
    `asset_name`       varchar(100) NOT NULL COMMENT '资产名称',
    `category_id`      bigint                DEFAULT NULL COMMENT '资产分类ID',
    `specification`    varchar(200)          DEFAULT NULL COMMENT '规格型号',
    `measurement_unit` varchar(20)           DEFAULT NULL COMMENT '计量单位',
    `quantity`         int          NOT NULL DEFAULT 1 COMMENT '采购数量',
    `unit_price`       decimal(12, 2)        DEFAULT NULL COMMENT '单价',
    `total_price`      decimal(12, 2)        DEFAULT NULL COMMENT '小计金额',
    `remark`           varchar(500)          DEFAULT NULL COMMENT '备注',
    `asset_id`         bigint                DEFAULT NULL COMMENT '入库后关联的资产ID',
    `warehoused`       bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否已入库',
    `creator`          varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                `idx_purchase_id` (`purchase_id`),
    KEY                `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产采购明细表';

-- ========== 6. 消防设备 ==========

-- 消防设备表
CREATE TABLE `dim_fire_equipment`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `asset_id`          bigint                DEFAULT NULL COMMENT '关联资产ID',
    `name`              varchar(100) NOT NULL COMMENT '设备名称',
    `code`              varchar(100)          DEFAULT NULL COMMENT '设备编号',
    `type`              tinyint               DEFAULT NULL COMMENT '设备类型:1灭火器,2消火栓,3烟感器,4喷淋头,5其他',
    `quantity`          int                   DEFAULT 1 COMMENT '数量',
    `measurement_unit`  varchar(20)           DEFAULT NULL COMMENT '计量单位',
    `building_no`       int                   DEFAULT NULL COMMENT '楼号',
    `floor`             int                   DEFAULT NULL COMMENT '楼层',
    `position`          varchar(255)          DEFAULT NULL COMMENT '具体位置',
    `install_date`      date                  DEFAULT NULL COMMENT '安装日期',
    `expiry_date`       date                  DEFAULT NULL COMMENT '到期日期',
    `check_date`        date                  DEFAULT NULL COMMENT '检查日期',
    `next_check_date`   date                  DEFAULT NULL COMMENT '下次检查日期',
    `maintenance_cycle` int                   DEFAULT NULL COMMENT '维护周期(天)',
    `status`            tinyint               DEFAULT 0 COMMENT '状态:0正常,1待检,2故障,3报废',
    `remarks`           varchar(500)          DEFAULT NULL COMMENT '备注',
    `creator`           varchar(64)           DEFAULT '' COMMENT '创建者',
    `create_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(64)           DEFAULT '' COMMENT '更新者',
    `update_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`         bigint       NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY                 `idx_building_floor` (`building_no`, `floor`),
    KEY                 `idx_asset_id` (`asset_id`),
    KEY                 `idx_type` (`type`),
    KEY                 `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消防设备表';

-- 消防设备巡检日志表
CREATE TABLE `dim_fire_equipment_check_log`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `equipment_id` bigint   NOT NULL COMMENT '设备ID',
    `check_result` tinyint  NOT NULL COMMENT '巡检结果:0正常,1故障',
    `check_remark` varchar(500)      DEFAULT NULL COMMENT '巡检备注',
    `check_time`   datetime NOT NULL COMMENT '巡检时间',
    `checker_id`   bigint            DEFAULT NULL COMMENT '巡检人ID',
    `checker_name` varchar(64)       DEFAULT NULL COMMENT '巡检人姓名',
    `creator`      varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`    bigint   NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY            `idx_equipment_id` (`equipment_id`),
    KEY            `idx_check_time` (`check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消防设备巡检日志表';

-- 楼栋配置表
CREATE TABLE `dim_building_config`
(
    `id`            bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `building_no`   int         NOT NULL COMMENT '楼号',
    `building_name` varchar(50) NOT NULL COMMENT '楼栋名称',
    `floors`        varchar(100)         DEFAULT NULL COMMENT '楼层列表(JSON数组,如:[1,2,3,-1])',
    `sort`          int                  DEFAULT 0 COMMENT '排序',
    `icon_url`      varchar(255)         DEFAULT NULL COMMENT '楼栋图标URL',
    `plan_url`      varchar(255)         DEFAULT NULL COMMENT '平面图URL',
    `status`        tinyint              DEFAULT 1 COMMENT '状态:0禁用,1启用',
    `creator`       varchar(64)          DEFAULT '' COMMENT '创建者',
    `create_time`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(64)          DEFAULT '' COMMENT '更新者',
    `update_time`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`     bigint      NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_building_no` (`building_no`, `deleted`, `tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楼栋配置表';

-- 初始化楼栋数据(复刻旧项目7栋楼配置)
INSERT INTO `dim_building_config` (`building_no`, `building_name`, `floors`, `sort`, `status`) VALUES
(1, '1号楼', '[1,2,3,-1]', 1, 1),
(2, '2号楼', '[1,2,3,4,-1]', 2, 1),
(3, '3号楼', '[1,2,3,4,5,-1]', 3, 1),
(4, '4号楼', '[1,2,3,-1]', 4, 1),
(5, '5号楼', '[1,2,3,4,-1]', 5, 1),
(6, '6号楼', '[1,2,-1]', 6, 1),
(7, '7号楼', '[1,2,-1]', 7, 1);

-- ========== 7. 公务车辆 ==========

-- 公务车辆表
CREATE TABLE `dim_vehicle`
(
    `id`                  bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                varchar(100)         DEFAULT NULL COMMENT '车辆名称',
    `plate_number`        varchar(20) NOT NULL COMMENT '车牌号',
    `brand`               varchar(100)         DEFAULT NULL COMMENT '品牌型号',
    `color`               varchar(20)          DEFAULT NULL COMMENT '颜色',
    `purchase_date`       date                 DEFAULT NULL COMMENT '购置日期',
    `driver`              varchar(50)          DEFAULT NULL COMMENT '驾驶人',
    `driver_phone`        varchar(20)          DEFAULT NULL COMMENT '驾驶人电话',
    `department`          varchar(100)         DEFAULT NULL COMMENT '所属部门',
    `leading_official`    varchar(50)          DEFAULT NULL COMMENT '分管领导',
    `maintenance_period`  varchar(50)          DEFAULT NULL COMMENT '维保周期（如：6个月、5000公里）',
    `maintenance_mileage` int                  DEFAULT NULL COMMENT '维保里程（公里），达到此里程需维保',
    `total_mileage`       int                  DEFAULT 0 COMMENT '累计行驶里程（公里）',
    `type`                tinyint              DEFAULT 0 COMMENT '类型：0普通车,1特种车',
    `status`              tinyint              DEFAULT 0 COMMENT '状态：0空闲,1使用中,2维修中',
    `remarks`             varchar(500)         DEFAULT NULL COMMENT '备注',
    `creator`             varchar(64)          DEFAULT '' COMMENT '创建者',
    `create_time`         datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             varchar(64)          DEFAULT '' COMMENT '更新者',
    `update_time`         datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_plate_number` (`plate_number`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公务车辆表';

-- 用车记录表
CREATE TABLE `dim_vehicle_usage`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vehicle_id`  bigint   NOT NULL COMMENT '车辆ID',
    `user_id`     bigint            DEFAULT NULL COMMENT '用车人ID',
    `user_name`   varchar(100)      DEFAULT NULL COMMENT '用车人姓名',
    `start_time`  datetime NOT NULL COMMENT '开始时间',
    `end_time`    datetime          DEFAULT NULL COMMENT '结束时间',
    `destination` varchar(255)      DEFAULT NULL COMMENT '目的地',
    `purpose`     varchar(500)      DEFAULT NULL COMMENT '用途',
    `mileage`     int               DEFAULT NULL COMMENT '行驶里程（公里）',
    `status`      tinyint           DEFAULT 0 COMMENT '状态：0使用中,1已归还',
    `remarks`     varchar(500)      DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64)       DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64)       DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)   NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           `idx_vehicle_id` (`vehicle_id`),
    KEY           `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用车记录表';

-- =============================================
-- 初始化餐饮设置数据
-- =============================================
INSERT IGNORE INTO `dim_dining` (`name`, `meal_type`, `price`, `start_time`, `end_time`, `status`, `remark`, `creator`, `updater`, `tenant_id`)
VALUES
    ('早餐', 0, 10.00, '07:00:00', '08:30:00', 0, '标准早餐', 'admin', 'admin', 1),
    ('午餐', 1, 15.00, '11:30:00', '13:00:00', 0, '标准午餐', 'admin', 'admin', 1),
    ('晚餐', 2, 15.00, '17:30:00', '19:00:00', 0, '标准晚餐', 'admin', 'admin', 1);

-- =============================================
-- 初始化餐饮价格配置数据
-- =============================================
-- 客餐价格配置（人员类型0-5，餐别0-2，分类0-客餐）
INSERT IGNORE INTO `dim_dining_price` (`person_type`, `meal_type`, `dining_class`, `price`, `status`, `remarks`, `creator`, `updater`, `tenant_id`) VALUES
-- 外协人员 - 客餐
(0, 0, 0, 10.00, 0, '外协-早餐-客餐', 'admin', 'admin', 1),
(0, 1, 0, 20.00, 0, '外协-午餐-客餐', 'admin', 'admin', 1),
(0, 2, 0, 18.00, 0, '外协-晚餐-客餐', 'admin', 'admin', 1),
-- 实验队 - 客餐
(1, 0, 0, 10.00, 0, '实验队-早餐-客餐', 'admin', 'admin', 1),
(1, 1, 0, 20.00, 0, '实验队-午餐-客餐', 'admin', 'admin', 1),
(1, 2, 0, 18.00, 0, '实验队-晚餐-客餐', 'admin', 'admin', 1),
-- 施工队 - 客餐
(2, 0, 0, 10.00, 0, '施工队-早餐-客餐', 'admin', 'admin', 1),
(2, 1, 0, 20.00, 0, '施工队-午餐-客餐', 'admin', 'admin', 1),
(2, 2, 0, 18.00, 0, '施工队-晚餐-客餐', 'admin', 'admin', 1),
-- 物业 - 客餐
(3, 0, 0, 8.00, 0, '物业-早餐-客餐', 'admin', 'admin', 1),
(3, 1, 0, 15.00, 0, '物业-午餐-客餐', 'admin', 'admin', 1),
(3, 2, 0, 12.00, 0, '物业-晚餐-客餐', 'admin', 'admin', 1),
-- 本所 - 客餐
(4, 0, 0, 8.00, 0, '本所-早餐-客餐', 'admin', 'admin', 1),
(4, 1, 0, 15.00, 0, '本所-午餐-客餐', 'admin', 'admin', 1),
(4, 2, 0, 12.00, 0, '本所-晚餐-客餐', 'admin', 'admin', 1),
-- 总部 - 客餐
(5, 0, 0, 8.00, 0, '总部-早餐-客餐', 'admin', 'admin', 1),
(5, 1, 0, 15.00, 0, '总部-午餐-客餐', 'admin', 'admin', 1),
(5, 2, 0, 12.00, 0, '总部-晚餐-客餐', 'admin', 'admin', 1),
-- 桌餐价格配置（人员类型0-5，餐别0-2，分类1-桌餐）
-- 外协人员 - 桌餐
(0, 0, 1, 15.00, 0, '外协-早餐-桌餐', 'admin', 'admin', 1),
(0, 1, 1, 30.00, 0, '外协-午餐-桌餐', 'admin', 'admin', 1),
(0, 2, 1, 25.00, 0, '外协-晚餐-桌餐', 'admin', 'admin', 1),
-- 实验队 - 桌餐
(1, 0, 1, 15.00, 0, '实验队-早餐-桌餐', 'admin', 'admin', 1),
(1, 1, 1, 30.00, 0, '实验队-午餐-桌餐', 'admin', 'admin', 1),
(1, 2, 1, 25.00, 0, '实验队-晚餐-桌餐', 'admin', 'admin', 1),
-- 施工队 - 桌餐
(2, 0, 1, 15.00, 0, '施工队-早餐-桌餐', 'admin', 'admin', 1),
(2, 1, 1, 30.00, 0, '施工队-午餐-桌餐', 'admin', 'admin', 1),
(2, 2, 1, 25.00, 0, '施工队-晚餐-桌餐', 'admin', 'admin', 1),
-- 物业 - 桌餐
(3, 0, 1, 12.00, 0, '物业-早餐-桌餐', 'admin', 'admin', 1),
(3, 1, 1, 25.00, 0, '物业-午餐-桌餐', 'admin', 'admin', 1),
(3, 2, 1, 20.00, 0, '物业-晚餐-桌餐', 'admin', 'admin', 1),
-- 本所 - 桌餐
(4, 0, 1, 12.00, 0, '本所-早餐-桌餐', 'admin', 'admin', 1),
(4, 1, 1, 25.00, 0, '本所-午餐-桌餐', 'admin', 'admin', 1),
(4, 2, 1, 20.00, 0, '本所-晚餐-桌餐', 'admin', 'admin', 1),
-- 总部 - 桌餐
(5, 0, 1, 12.00, 0, '总部-早餐-桌餐', 'admin', 'admin', 1),
(5, 1, 1, 25.00, 0, '总部-午餐-桌餐', 'admin', 'admin', 1),
(5, 2, 1, 20.00, 0, '总部-晚餐-桌餐', 'admin', 'admin', 1);

-- =============================================
-- DIM 模块菜单初始化
-- =============================================
-- 说明：
-- type: 1-目录 2-菜单 3-按钮
-- visible: 0-显示 1-隐藏

-- 获取后勤综合管理菜单ID（如果已存在）
SET @dim_menu_id = (SELECT id FROM system_menu WHERE path = '/dim' AND deleted = 0 LIMIT 1);

-- 如果不存在，则创建一级菜单
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
SELECT '后勤综合管理', '', 1, 100, 0, '/dim', 'ep:office-building', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0
FROM DUAL WHERE @dim_menu_id IS NULL;

SET @dim_menu_id = COALESCE(@dim_menu_id, LAST_INSERT_ID());


-- =============================================
-- 1. 住宿管理模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('住宿管理', '', 1, 2, @dim_menu_id, 'room', 'ep:house', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @room_menu_id = LAST_INSERT_ID();

-- 1.1 楼栋管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('楼栋管理', 'dim:room:building:query', 2, 1, @room_menu_id, 'building', '', 'dim/room/building/index', 'DimRoomBuilding', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @building_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('楼栋新增', 'dim:room:building:create', 3, 1, @building_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('楼栋修改', 'dim:room:building:update', 3, 2, @building_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('楼栋删除', 'dim:room:building:delete', 3, 3, @building_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 1.2 房间管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('房间管理', 'dim:room:room:query', 2, 2, @room_menu_id, 'room', '', 'dim/room/room/index', 'DimRoom', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @room_room_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('房间新增', 'dim:room:room:create', 3, 1, @room_room_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('房间修改', 'dim:room:room:update', 3, 2, @room_room_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('房间删除', 'dim:room:room:delete', 3, 3, @room_room_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('房间导出', 'dim:room:room:export', 3, 4, @room_room_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('房间导入', 'dim:room:room:import', 3, 5, @room_room_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 1.3 住宿记录
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('住宿记录', 'dim:room:stay:query', 2, 3, @room_menu_id, 'stay', '', 'dim/room/stay/index', 'DimRoomStay', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @stay_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('入住登记', 'dim:room:stay:checkin', 3, 1, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('退房办理', 'dim:room:stay:checkout', 3, 2, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('续住', 'dim:room:stay:extend', 3, 3, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约', 'dim:room:stay:reserve', 3, 4, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('取消预约', 'dim:room:stay:cancel-reserve', 3, 5, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('换房', 'dim:room:stay:transfer', 3, 6, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('添加同住人', 'dim:room:stay:add-coguest', 3, 7, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('住宿导出', 'dim:room:stay:export', 3, 8, @stay_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 1.4 费用结算
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('费用结算', 'dim:room:settlement:query', 2, 4, @room_menu_id, 'settlement', '', 'dim/room/settlement/index', 'DimRoomSettlement', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @settlement_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('确认结算', 'dim:room:settlement:pay', 3, 1, @settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('确认开票', 'dim:room:settlement:invoice', 3, 2, @settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('结算导出', 'dim:room:settlement:export', 3, 3, @settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 1.5 住宿统计
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('住宿统计', 'dim:room:statistics:query', 2, 5, @room_menu_id, 'statistics', '', 'dim/room/statistics/index', 'DimRoomStatistics', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @room_statistics_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('统计导出', 'dim:room:statistics:export', 3, 1, @room_statistics_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- 2. 餐饮管理模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('餐饮管理', '', 1, 3, @dim_menu_id, 'dining', 'ep:bowl', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_menu_id = LAST_INSERT_ID();

-- 2.1 餐饮设置
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('餐饮设置', 'dim:dining:setting:query', 2, 1, @dining_menu_id, 'setting', '', 'dim/dining/setting/index', 'DimDiningSetting', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_setting_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设置新增', 'dim:dining:setting:create', 3, 1, @dining_setting_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设置修改', 'dim:dining:setting:update', 3, 2, @dining_setting_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设置删除', 'dim:dining:setting:delete', 3, 3, @dining_setting_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 2.2 就餐记录
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('就餐记录', 'dim:dining:record:query', 2, 2, @dining_menu_id, 'record', '', 'dim/dining/record/index', 'DimDiningRecord', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_record_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('报餐登记', 'dim:dining:record:create', 3, 1, @dining_record_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('记录修改', 'dim:dining:record:update', 3, 2, @dining_record_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('记录删除', 'dim:dining:record:delete', 3, 3, @dining_record_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('记录导出', 'dim:dining:record:export', 3, 4, @dining_record_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 2.3 报餐登记
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('报餐登记', 'dim:dining:registration:query', 2, 3, @dining_menu_id, 'registration', '', 'dim/dining/registration/index', 'DimDiningRegistration', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_registration_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('登记新增', 'dim:dining:registration:create', 3, 1, @dining_registration_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('登记修改', 'dim:dining:registration:update', 3, 2, @dining_registration_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('登记删除', 'dim:dining:registration:delete', 3, 3, @dining_registration_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 2.4 餐饮结算
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('餐饮结算', 'dim:dining:settlement:query', 2, 4, @dining_menu_id, 'settlement', '', 'dim/dining/settlement/index', 'DimDiningSettlement', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_settlement_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('结算创建', 'dim:dining:settlement:create', 3, 1, @dining_settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('确认支付', 'dim:dining:settlement:pay', 3, 2, @dining_settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('确认开票', 'dim:dining:settlement:invoice', 3, 3, @dining_settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('结算导出', 'dim:dining:settlement:export', 3, 4, @dining_settlement_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 2.5 餐饮价格配置
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('餐饮价格配置', 'dim:dining-price:query', 2, 5, @dining_menu_id, 'dining-price', 'money', 'dim/dining/price/index', 'DiningPrice', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_price_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('价格查询', 'dim:dining-price:query', 3, 1, @dining_price_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('价格创建', 'dim:dining-price:create', 3, 2, @dining_price_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('价格更新', 'dim:dining-price:update', 3, 3, @dining_price_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('价格删除', 'dim:dining-price:delete', 3, 4, @dining_price_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('价格导出', 'dim:dining-price:export', 3, 5, @dining_price_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 2.6 用餐统计
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('用餐统计', 'dim:dining:statistics:query', 2, 6, @dining_menu_id, 'statistics', 'chart', 'dim/dining/statistics/index', 'DiningStatistics', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @dining_statistics_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('统计导出', 'dim:dining:statistics:export', 3, 1, @dining_statistics_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- 3. 库存管理模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('库存管理', '', 1, 4, @dim_menu_id, 'inventory', 'ep:box', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @inventory_menu_id = LAST_INSERT_ID();

-- 3.1 分类管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类管理', 'dim:inventory:category:query', 2, 1, @inventory_menu_id, 'category', '', 'dim/inventory/category/index', 'DimInventoryCategory', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @inventory_category_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类新增', 'dim:inventory:category:create', 3, 1, @inventory_category_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类修改', 'dim:inventory:category:update', 3, 2, @inventory_category_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类删除', 'dim:inventory:category:delete', 3, 3, @inventory_category_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 3.2 物品管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('物品管理', 'dim:inventory:item:query', 2, 2, @inventory_menu_id, 'item', '', 'dim/inventory/item/index', 'DimInventoryItem', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @inventory_item_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('物品新增', 'dim:inventory:item:create', 3, 1, @inventory_item_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('物品修改', 'dim:inventory:item:update', 3, 2, @inventory_item_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('物品删除', 'dim:inventory:item:delete', 3, 3, @inventory_item_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('物品导出', 'dim:inventory:item:export', 3, 4, @inventory_item_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 3.3 入库管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('入库管理', 'dim:inventory:inbound:query', 2, 3, @inventory_menu_id, 'inbound', '', 'dim/inventory/inbound/index', 'DimInventoryInbound', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @inventory_inbound_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('入库新增', 'dim:inventory:inbound:create', 3, 1, @inventory_inbound_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('入库审核', 'dim:inventory:audit', 3, 2, @inventory_inbound_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 3.4 出库管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('出库管理', 'dim:inventory:outbound:query', 2, 4, @inventory_menu_id, 'outbound', '', 'dim/inventory/outbound/index', 'DimInventoryOutbound', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @inventory_outbound_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('出库新增', 'dim:inventory:outbound:create', 3, 1, @inventory_outbound_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('出库审核', 'dim:inventory:audit', 3, 2, @inventory_outbound_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 3.5 库存盘点
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('库存盘点', 'dim:inventory:check', 2, 5, @inventory_menu_id, 'check', '', 'dim/inventory/check/index', 'DimInventoryCheck', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 3.6 月度报表
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('月度报表', 'dim:inventory:report:query', 2, 6, @inventory_menu_id, 'report', '', 'dim/inventory/report/MonthlyReport', 'DimInventoryReport', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @inventory_report_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('报表导出', 'dim:inventory:report:export', 3, 1, @inventory_report_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 3.7 统计分析
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('统计分析', 'dim:inventory:statistics:query', 2, 7, @inventory_menu_id, 'statistics', '', 'dim/inventory/statistics/index', 'DimInventoryStatistics', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- 4. 资产管理模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产管理', '', 1, 5, @dim_menu_id, 'asset', 'ep:money', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @asset_menu_id = LAST_INSERT_ID();

-- 4.1 资产分类
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产分类', 'dim:asset:category:query', 2, 1, @asset_menu_id, 'category', '', 'dim/asset/category/index', 'DimAssetCategory', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @asset_category_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类新增', 'dim:asset:category:create', 3, 1, @asset_category_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类修改', 'dim:asset:category:update', 3, 2, @asset_category_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('分类删除', 'dim:asset:category:delete', 3, 3, @asset_category_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 4.2 资产列表
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产列表', 'dim:asset:query', 2, 2, @asset_menu_id, 'list', '', 'dim/asset/asset/index', 'DimAsset', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @asset_list_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产新增', 'dim:asset:create', 3, 1, @asset_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产修改', 'dim:asset:update', 3, 2, @asset_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产删除', 'dim:asset:delete', 3, 3, @asset_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('资产导出', 'dim:asset:export', 3, 4, @asset_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- 5. 访客管理模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客管理', '', 1, 6, @dim_menu_id, 'visitor', 'ep:user', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @visitor_menu_id = LAST_INSERT_ID();

-- 5.1 访客登记
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客登记', 'dim:visitor:query', 2, 1, @visitor_menu_id, 'list', '', 'dim/visitor/index', 'DimVisitor', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @visitor_list_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('来访登记', 'dim:visitor:create', 3, 1, @visitor_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客修改', 'dim:visitor:update', 3, 2, @visitor_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客离开', 'dim:visitor:leave', 3, 3, @visitor_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客删除', 'dim:visitor:delete', 3, 4, @visitor_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客导出', 'dim:visitor:export', 3, 5, @visitor_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 5.2 预约访客
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约访客', 'dim:reservation-visitor:query', 2, 2, @visitor_menu_id, 'reservation', '', 'dim/visitor/reservation/index', 'DimReservationVisitor', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @reservation_visitor_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约新增', 'dim:reservation-visitor:create', 3, 1, @reservation_visitor_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约修改', 'dim:reservation-visitor:update', 3, 2, @reservation_visitor_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约删除', 'dim:reservation-visitor:delete', 3, 3, @reservation_visitor_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约来访', 'dim:reservation-visitor:update', 3, 4, @reservation_visitor_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约导出', 'dim:reservation-visitor:export', 3, 5, @reservation_visitor_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('预约导入', 'dim:reservation-visitor:import', 3, 6, @reservation_visitor_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 5.3 来访日志
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('来访日志', 'dim:visit-log:query', 2, 3, @visitor_menu_id, 'log', '', 'dim/visitor/log/index', 'DimVisitLog', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @visit_log_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('日志新增', 'dim:visit-log:create', 3, 1, @visit_log_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('日志修改', 'dim:visit-log:update', 3, 2, @visit_log_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('日志删除', 'dim:visit-log:delete', 3, 3, @visit_log_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客离场', 'dim:visit-log:leave', 3, 4, @visit_log_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 5.4 访客统计
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客统计', 'dim:visitor:statistics', 2, 4, @visitor_menu_id, 'statistics', 'ep:data-analysis', 'dim/visitor/statistics/index', 'DimVisitorStatistics', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 5.5 访客区域
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('访客区域', 'dim:visitor-area:query', 2, 5, @visitor_menu_id, 'area', 'ep:location', 'dim/visitor/area/index', 'DimVisitorArea', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @visitor_area_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('区域新增', 'dim:visitor-area:create', 3, 1, @visitor_area_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('区域修改', 'dim:visitor-area:update', 3, 2, @visitor_area_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('区域删除', 'dim:visitor-area:delete', 3, 3, @visitor_area_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- 6. 消防设备模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('消防设备', '', 1, 7, @dim_menu_id, 'fire', 'ep:warning-filled', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @fire_menu_id = LAST_INSERT_ID();

-- 6.1 设备管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设备管理', 'dim:fire:equipment:query', 2, 1, @fire_menu_id, 'equipment', '', 'dim/fire/index', 'DimFireEquipment', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @fire_equipment_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设备新增', 'dim:fire:equipment:create', 3, 1, @fire_equipment_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设备修改', 'dim:fire:equipment:update', 3, 2, @fire_equipment_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设备删除', 'dim:fire:equipment:delete', 3, 3, @fire_equipment_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设备巡检', 'dim:fire:equipment:check', 3, 4, @fire_equipment_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('设备导出', 'dim:fire:equipment:export', 3, 5, @fire_equipment_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 6.2 场地平面图
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('场地平面图', 'dim:fire:equipment:query', 2, 2, @fire_menu_id, 'site-plan', 'ep:map-location', 'dim/fire/plan/SitePlan', 'FireSitePlan', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 6.3 楼栋平面图
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('楼栋平面图', 'dim:fire:equipment:query', 2, 3, @fire_menu_id, 'building-plan', 'ep:office-building', 'dim/fire/plan/BuildingPlan', 'FireBuildingPlan', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- 7. 公务车辆模块
-- =============================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('公务车辆', '', 1, 8, @dim_menu_id, 'vehicle', 'ep:van', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @vehicle_menu_id = LAST_INSERT_ID();

-- 7.1 车辆管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('车辆管理', 'dim:vehicle:query', 2, 1, @vehicle_menu_id, 'list', '', 'dim/vehicle/index', 'DimVehicle', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);

SET @vehicle_list_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('车辆新增', 'dim:vehicle:create', 3, 1, @vehicle_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('车辆修改', 'dim:vehicle:update', 3, 2, @vehicle_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('车辆删除', 'dim:vehicle:delete', 3, 3, @vehicle_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES ('车辆导出', 'dim:vehicle:export', 3, 4, @vehicle_list_menu_id, '', '', '', '', 0, 1, 1, 1, '1', NOW(), '1', NOW(), 0);


-- =============================================
-- DIM 模块字典数据初始化
-- =============================================

-- 1. 房间类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('房间类型', 'dim_room_type', 0, 'DIM住宿模块-房间类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '标间', '0', 'dim_room_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '单间', '1', 'dim_room_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '套房', '2', 'dim_room_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '小院', '3', 'dim_room_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 2. 房间状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('房间状态', 'dim_room_status', 0, 'DIM住宿模块-房间状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '空闲', '0', 'dim_room_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '入住', '1', 'dim_room_status', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '维修', '2', 'dim_room_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '已预定', '3', 'dim_room_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 3. 餐别类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('餐别类型', 'dim_meal_type', 0, 'DIM餐饮模块-餐别类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '早餐', '0', 'dim_meal_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '午餐', '1', 'dim_meal_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '晚餐', '2', 'dim_meal_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 4. 访客类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('访客类型', 'dim_visitor_type', 0, 'DIM访客模块-访客类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '外协', '0', 'dim_visitor_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '实验队', '1', 'dim_visitor_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '施工队', '2', 'dim_visitor_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '物业', '3', 'dim_visitor_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(4, '本所', '4', 'dim_visitor_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(5, '总部', '5', 'dim_visitor_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(9, '贵宾', '9', 'dim_visitor_type', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 4.1 预约访客状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('预约访客状态', 'dim_reservation_status', 0, 'DIM访客模块-预约访客状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '待来访', '0', 'dim_reservation_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '已来访', '1', 'dim_reservation_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '已过期', '2', 'dim_reservation_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '已取消', '3', 'dim_reservation_status', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 5. 物资日志类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('物资日志类型', 'dim_inventory_log_type', 0, 'DIM物资模块-日志类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '直发', '0', 'dim_inventory_log_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '入库', '1', 'dim_inventory_log_type', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '出库', '2', 'dim_inventory_log_type', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 6. 审核状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('审核状态', 'dim_audit_status', 0, 'DIM通用-审核状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '待审核', '0', 'dim_audit_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '审核通过', '1', 'dim_audit_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '审核不通过', '-1', 'dim_audit_status', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 7. 资产状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('资产状态', 'dim_asset_status', 0, 'DIM资产模块-资产状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '正常', '0', 'dim_asset_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '维修', '1', 'dim_asset_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '报废', '2', 'dim_asset_status', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '外借', '3', 'dim_asset_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 8. 费用状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('费用状态', 'dim_fee_status', 0, 'DIM通用-费用状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '未结算', '0', 'dim_fee_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '挂账', '1', 'dim_fee_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(9, '已结算', '9', 'dim_fee_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 9. 车辆状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('车辆状态', 'dim_vehicle_status', 0, 'DIM车辆模块-车辆状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '空闲', '0', 'dim_vehicle_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '使用中', '1', 'dim_vehicle_status', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '维修中', '2', 'dim_vehicle_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 10. 来访状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('来访状态', 'dim_visit_status', 0, 'DIM访客模块-来访状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '来访', '0', 'dim_visit_status', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '离场', '1', 'dim_visit_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 11. 库存物品类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('库存物品类型', 'dim_inventory_type', 0, 'DIM库存模块-物品类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(1, '物资', '1', 'dim_inventory_type', 0, 'primary', '', '办公物资', 'admin', NOW(), 'admin', NOW(), 0),
(2, '耗材', '2', 'dim_inventory_type', 0, 'success', '', '后厨耗材', 'admin', NOW(), 'admin', NOW(), 0);

-- 12. 库存操作类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('库存操作类型', 'dim_inventory_operation_type', 0, 'DIM库存模块-操作类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(1, '采购入库', '1', 'dim_inventory_operation_type', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '盘盈入库', '2', 'dim_inventory_operation_type', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '领用出库', '3', 'dim_inventory_operation_type', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(4, '盘亏出库', '4', 'dim_inventory_operation_type', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(5, '调拨入库', '5', 'dim_inventory_operation_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(6, '调拨出库', '6', 'dim_inventory_operation_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 13. 住宿状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('住宿状态', 'dim_stay_status', 0, 'DIM住宿模块-住宿状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '在住', '0', 'dim_stay_status', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '已退房', '1', 'dim_stay_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '预约中', '2', 'dim_stay_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '已取消', '3', 'dim_stay_status', 0, 'default', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 14. 住宿操作类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('住宿操作类型', 'dim_stay_operation_type', 0, 'DIM住宿模块-操作类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '入住', '0', 'dim_stay_operation_type', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '退房', '1', 'dim_stay_operation_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '续住', '2', 'dim_stay_operation_type', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '换房', '3', 'dim_stay_operation_type', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(4, '预约', '4', 'dim_stay_operation_type', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(5, '取消预约', '5', 'dim_stay_operation_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 15. 住宿支付状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('住宿支付状态', 'dim_payment_status', 0, '住宿费用结算的支付状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '未结算', '0', 'dim_payment_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '已结算', '1', 'dim_payment_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 16. 住宿开票状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('住宿开票状态', 'dim_invoice_status', 0, '住宿费用结算的开票状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '未开票', '0', 'dim_invoice_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '已开票', '1', 'dim_invoice_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 17. 人员类型（餐饮）
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('人员类型', 'dim_person_type', 0, 'DIM餐饮模块-人员类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '外协', '0', 'dim_person_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '实验队', '1', 'dim_person_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '施工队', '2', 'dim_person_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '物业', '3', 'dim_person_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(4, '本所', '4', 'dim_person_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(5, '总部', '5', 'dim_person_type', 0, '', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 18. 餐类分类
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('餐类分类', 'dim_dining_class', 0, 'DIM餐饮模块-餐类分类', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '客餐', '0', 'dim_dining_class', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '桌餐', '1', 'dim_dining_class', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 14. 就餐状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('就餐状态', 'dim_dining_status', 0, 'DIM餐饮模块-就餐状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '已报餐', '0', 'dim_dining_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '已用餐', '1', 'dim_dining_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '已取消', '2', 'dim_dining_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 15. 消防设备类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('消防设备类型', 'dim_fire_equipment_type', 0, 'DIM消防模块-设备类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(1, '灭火器', '1', 'dim_fire_equipment_type', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '消火栓', '2', 'dim_fire_equipment_type', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '烟感器', '3', 'dim_fire_equipment_type', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(4, '喷淋头', '4', 'dim_fire_equipment_type', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(5, '其他', '5', 'dim_fire_equipment_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 16. 消防设备状态
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('消防设备状态', 'dim_fire_equipment_status', 0, 'DIM消防模块-设备状态', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(0, '正常', '0', 'dim_fire_equipment_status', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(1, '待检', '1', 'dim_fire_equipment_status', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, '故障', '2', 'dim_fire_equipment_status', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '报废', '3', 'dim_fire_equipment_status', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0);

-- 17. 车辆类型
INSERT INTO system_dict_type (name, type, status, remark, creator, create_time, updater, update_time, deleted)
VALUES ('车辆类型', 'dim_vehicle_type', 0, 'DIM车辆模块-车辆类型', 'admin', NOW(), 'admin', NOW(), 0);

INSERT INTO system_dict_data (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES
(1, '轿车', '1', 'dim_vehicle_type', 0, 'primary', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(2, 'SUV', '2', 'dim_vehicle_type', 0, 'success', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(3, '商务车', '3', 'dim_vehicle_type', 0, 'warning', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(4, '客车', '4', 'dim_vehicle_type', 0, 'info', '', '', 'admin', NOW(), 'admin', NOW(), 0),
(5, '货车', '5', 'dim_vehicle_type', 0, 'danger', '', '', 'admin', NOW(), 'admin', NOW(), 0);


-- =============================================
-- 初始化完成
-- =============================================
-- 验证表数量：
-- SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name LIKE 'dim_%';
-- 预期：约 24+ 张业务表
--
-- 验证菜单数量：
-- SELECT COUNT(*) FROM system_menu WHERE path LIKE '%dim%' OR path LIKE '/dim%';
-- 预期：约 80+ 条菜单记录
--
-- 验证字典数量：
-- SELECT COUNT(*) FROM system_dict_type WHERE type LIKE 'dim_%';
-- 预期：20 个字典类型
--
-- 合并说明：
-- 此文件包含以下更新脚本的内容：
-- - V1.0.1__add_usage_location.sql (库存日志使用地点字段)
-- - V1.0.2__add_discount_fields.sql (库存日志折扣字段)
-- - V1.0.3__add_inventory_menus.sql (库存管理菜单)
-- - V1.0.4__room_status_extension.sql (房间状态扩展)
-- - V1.0.5__stay_settlement_and_coguest.sql (住宿结算与同住人)
-- - V1.0.6__room_menus_extension.sql (住宿菜单扩展)
-- - V1.0.7__dining_enhancement.sql (餐饮增强)
-- - V1.0.8__dining_enhancement_phase2.sql (餐饮增强阶段二)
