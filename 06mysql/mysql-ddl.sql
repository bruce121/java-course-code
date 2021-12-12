CREATE TABLE `user`
(
    `id`                 varchar(64)     NOT NULL COMMENT '主键id',
    `phone`              VARCHAR(20)              DEFAULT NULL COMMENT '手机号码',
    `status`             enum ('1','-1') NOT NULL DEFAULT '1' COMMENT '账号状态(是否禁用)',
    `created_time`       timestamp       NULL     DEFAULT NULL,
    `last_modified_time` timestamp       NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '用户表';

CREATE TABLE `product`
(
    `id`                 varchar(64)                             NOT NULL COMMENT '主键id',
    `name`               varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品标题',
    `picture_url`        varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '封面图',
    `description`        text                                         DEFAULT NULL COMMENT '商品描述',
    `price`              decimal(8, 2)                           NOT NULL COMMENT '商品价格',
    `market_price`       decimal(8, 2)                           NOT NULL COMMENT '市场价格',
    `created_time`       timestamp                               NULL DEFAULT NULL,
    `last_modified_time` timestamp                               NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '商品表';

CREATE TABLE `product_property`
(
    `id`   varchar(64)                             NOT NULL COMMENT '主键id',
    `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品属性名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '商品属性表';

insert INTO product_property(`id`, `name`)
VALUES ('EEDDFC90-0FFC-4389-85B5-0045763BC411', '颜色'),
       ('D36AE173-5E05-4B31-9651-EA34A3A189D7', '尺寸');

CREATE TABLE `product_property_option`
(
    `id`                  varchar(64)                             NOT NULL COMMENT '主键id',
    `product_property_id` varchar(64)                             NOT NULL COMMENT '商品属性id',
    `name`                varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品可选属性名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '商品属性可选值表';

insert INTO product_property_option(`id`, `product_property_id`, `name`)
VALUES ('D39F8603-2E2B-47F2-B515-F7A1F743D5F6', 'EEDDFC90-0FFC-4389-85B5-0045763BC411', '红色'),
       ('4FEFB8A0-15A5-4005-BE11-252BE30CAC0F', 'EEDDFC90-0FFC-4389-85B5-0045763BC411', '蓝色'),
       ('4D7044B3-343F-47B5-915A-6E0DAF4BEE01', 'D36AE173-5E05-4B31-9651-EA34A3A189D7', 'S'),
       ('70E49725-EBA4-422A-BB00-C881EEDAC652', 'D36AE173-5E05-4B31-9651-EA34A3A189D7', 'M'),
       ('B4701AC5-40C9-43E7-B910-50CDD050D21D', 'D36AE173-5E05-4B31-9651-EA34A3A189D7', 'L');

CREATE TABLE `product_sku`
(
    `id`                  varchar(64)    NOT NULL COMMENT '商品库存表',
    `product_id`          varchar(64)    NOT NULL COMMENT '商品id',
    `property_option_str` varchar(1000)           DEFAULT NULL COMMENT '属性串（由不同可选属性值的id拼接而成）',
    `name`                varchar(125)   NOT NULL COMMENT 'sku名称',
    `price`               decimal(12, 4) NOT NULL COMMENT '价格',
    `stock`               int(11)        NOT NULL DEFAULT '0' COMMENT '库存',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `order`
(
    `id`                 varchar(64)    NOT NULL COMMENT '订单编号',
    `user_id`            varchar(64)    NOT NULL COMMENT '用户id',
    `order_status`       tinyint(4)     NOT NULL DEFAULT '0' COMMENT '订单状态',
    `order_amount_total` decimal(12, 4) NOT NULL DEFAULT '0.0000' COMMENT '订单总价',
    `created_time`       timestamp      NULL     DEFAULT NULL,
    `last_modified_time` timestamp      NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '订单表';


CREATE TABLE `order_detail`
(
    `id`                 varchar(64)    NOT NULL,
    `order_id`           varchar(64)    NOT NULL COMMENT '订单编号',
    `product_sku_id`     varchar(64)    NOT NULL COMMENT '指定商品ID',
    `product_sku_count`  int(11)        NOT NULL DEFAULT '0' COMMENT '指定商品数量',
    `product_sku_amount` decimal(12, 4) NOT NULL COMMENT '指定商品单价',
    `created_time`       timestamp      NULL     DEFAULT NULL,
    `last_modified_time` timestamp      NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '订单详情表';
