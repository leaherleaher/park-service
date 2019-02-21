/*
Navicat MySQL Data Transfer

Source Server         : local-db
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : intelligent_park

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-02-20 10:27:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for market_template
-- ----------------------------
DROP TABLE IF EXISTS `market_template`;
CREATE TABLE `market_template` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `template_id` varchar(32) NOT NULL COMMENT '模板类型id',
  `temp_category_code` varchar(32) NOT NULL COMMENT '模板类型代码',
  `template_no` varchar(128) NOT NULL COMMENT '模板编号',
  `template_name` varchar(256) NOT NULL COMMENT '模板名称',
  `template_url` varchar(1000) NOT NULL COMMENT '模板渲染url',
  `template_img` varchar(256) NOT NULL COMMENT '模板图片',
  `template_status` tinyint(1) NOT NULL COMMENT '模板状态 0-禁用 1-启用',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_updated_by` varchar(64) DEFAULT NULL COMMENT '最后更新人',
  `last_updated_time` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `note` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `market_template_N1` (`template_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for merchant_basic_setting
-- ----------------------------
DROP TABLE IF EXISTS `merchant_basic_setting`;
CREATE TABLE `merchant_basic_setting` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `mer_id` varchar(64) NOT NULL COMMENT '商户id',
  `mer_logo` varchar(256) NOT NULL COMMENT '商户logo',
  `countdown_time` int(11) NOT NULL COMMENT '倒计时时长（分）',
  `attr1_url` varchar(256) NOT NULL COMMENT 'url1',
  `attr2_url` varchar(256) NOT NULL COMMENT 'url2',
  `attr3_url` varchar(256) NOT NULL COMMENT 'url3',
  `attr4_url` varchar(256) DEFAULT NULL COMMENT 'url4',
  `attr5_url` varchar(256) DEFAULT NULL COMMENT 'url5',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_updated_by` varchar(64) DEFAULT NULL COMMENT '最后更新人',
  `last_updated_time` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `merchant_basic_setting_N1` (`mer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `mer_id` varchar(64) NOT NULL COMMENT '商户id',
  `open_id` varchar(64) DEFAULT NULL COMMENT 'openId',
  `nick_name` varchar(256) DEFAULT NULL COMMENT '昵称',
  `plate_no` varchar(32) NOT NULL COMMENT '车牌号',
  `park_id` int(11) NOT NULL COMMENT '车场id',
  `order_no` varchar(128) NOT NULL COMMENT '订单号',
  `park_name` varchar(256) NOT NULL COMMENT '停车场名称',
  `entry_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入场时间',
  `out_time` timestamp NULL DEFAULT NULL COMMENT '出场时间',
  `pay_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '查询停车费用时间',
  `elapsed_time` int(11) NOT NULL COMMENT '停车时长（分）',
  `img` varchar(256) DEFAULT NULL COMMENT '停车入场图片',
  `payable` int(11) NOT NULL COMMENT '应付金额',
  `delay_time` int(11) NOT NULL COMMENT '缴费后允许的延迟出场时间',
  `order_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `pay_status` tinyint(1) NOT NULL COMMENT '支付状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pay_order_U1` (`order_no`),
  KEY `pay_order_N2` (`mer_id`),
  KEY `pay_order_N3` (`plate_no`),
  KEY `pay_order_N4` (`park_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for p_c_order
-- ----------------------------
DROP TABLE IF EXISTS `p_c_order`;
CREATE TABLE `p_c_order` (
  `id` varchar(32) NOT NULL COMMENT '系统标识',
  `order_id` varchar(64) NOT NULL COMMENT '订单号',
  `order_amt` int(11) NOT NULL COMMENT '缴费金额',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `pay_id` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付方式类型',
  `p_order_id` varchar(128) DEFAULT NULL COMMENT '平台订单号',
  `scode` varchar(128) NOT NULL COMMENT '身份标识',
  `sync_status` tinyint(1) NOT NULL COMMENT '缴费成功同步状态',
  `mer_id` varchar(64) NOT NULL COMMENT '商户标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_updated_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`) USING BTREE,
  UNIQUE KEY `p_c_order_U1` (`order_id`),
  KEY `pco_c1` (`order_id`,`order_status`) USING BTREE,
  KEY `pco_c2` (`pay_id`) USING BTREE,
  KEY `pco_c4` (`pay_time`,`order_status`,`order_id`) USING BTREE,
  KEY `pco_i5` (`mer_id`,`order_id`) USING BTREE,
  KEY `pco_i6` (`mer_id`,`order_status`,`order_id`) USING BTREE,
  KEY `p_c_order_N1` (`scode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='缴费订单表';

-- ----------------------------
-- Table structure for p_sys_branch
-- ----------------------------
DROP TABLE IF EXISTS `p_sys_branch`;
CREATE TABLE `p_sys_branch` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `branch_id` varchar(64) NOT NULL COMMENT '机构标识',
  `branch_name` varchar(1024) NOT NULL COMMENT '机构名称',
  `branch_desc` varchar(1024) DEFAULT NULL COMMENT '机构描述',
  `par_branch_id` varchar(64) NOT NULL DEFAULT '-1' COMMENT '上级机构标识',
  `branch_level` int(11) NOT NULL DEFAULT '1' COMMENT '机构级别',
  `branch_order` int(11) NOT NULL DEFAULT '1' COMMENT '机构顺序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `pcb_c2` (`branch_id`,`par_branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for p_sys_merchant
-- ----------------------------
DROP TABLE IF EXISTS `p_sys_merchant`;
CREATE TABLE `p_sys_merchant` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `mer_id` varchar(64) NOT NULL COMMENT '商户号',
  `mer_name` varchar(128) NOT NULL COMMENT '商户名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '商户状态',
  `branch_id` varchar(64) NOT NULL COMMENT '机构标识',
  `shop_id` varchar(32) DEFAULT NULL COMMENT '门店编号',
  `logo` varchar(256) DEFAULT NULL COMMENT '商户LOGO',
  `city_id` varchar(64) DEFAULT NULL COMMENT '所属城市',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mer_id` (`mer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户基础信息表';

-- ----------------------------
-- Table structure for p_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `p_sys_user`;
CREATE TABLE `p_sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `user_id` varchar(64) NOT NULL COMMENT '用户标识',
  `user_name` varchar(64) NOT NULL COMMENT '用户名称',
  `password` varchar(128) NOT NULL COMMENT '用户密码',
  `branch_id` varchar(64) NOT NULL COMMENT '机构标识',
  `user_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态；1：正常；0：锁定',
  `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型；1：商户；0：银行管理;2:客户经理',
  `user_relation` varchar(64) NOT NULL COMMENT '所属机构或商户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for p_tlinx_auth
-- ----------------------------
DROP TABLE IF EXISTS `p_tlinx_auth`;
CREATE TABLE `p_tlinx_auth` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `app_id` varchar(50) DEFAULT NULL COMMENT '应用ID',
  `app_token` varchar(100) DEFAULT NULL COMMENT '用户标识令牌',
  `exchange_token` varchar(100) DEFAULT NULL COMMENT '交换令牌',
  `token` varchar(100) DEFAULT NULL COMMENT '令牌',
  `aes_key` varchar(100) DEFAULT NULL COMMENT 'AES加密密钥',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `isvalid` char(1) DEFAULT NULL COMMENT '是否有效(1是0否)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='T-Linx应用授权信息表';

-- ----------------------------
-- Table structure for p_tlinx_security
-- ----------------------------
DROP TABLE IF EXISTS `p_tlinx_security`;
CREATE TABLE `p_tlinx_security` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `shop_id` int(11) NOT NULL COMMENT '应用ID',
  `open_id` varchar(100) DEFAULT NULL COMMENT '应用open_id',
  `open_key` varchar(100) DEFAULT NULL COMMENT '应用open_key',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用,1：可用，0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shop_id` (`shop_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='T-Linx商户令牌信息表';

-- ----------------------------
-- Table structure for p_tlinx_token
-- ----------------------------
DROP TABLE IF EXISTS `p_tlinx_token`;
CREATE TABLE `p_tlinx_token` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `app_id` varchar(50) DEFAULT NULL COMMENT '应用ID',
  `token` varchar(100) DEFAULT NULL COMMENT '应用令牌',
  `aes_key` varchar(100) DEFAULT NULL COMMENT 'AES加密密钥',
  `expired_time` int(11) DEFAULT NULL COMMENT '过期时长(秒)',
  `expired_date` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='T-Linx应用令牌信息表';

-- ----------------------------
-- Table structure for template_category
-- ----------------------------
DROP TABLE IF EXISTS `template_category`;
CREATE TABLE `template_category` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `temp_category_code` varchar(32) NOT NULL COMMENT '模板类型代码',
  `temp_category_desc` varchar(256) NOT NULL COMMENT '模板名称',
  `template_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '模板状态 0-禁用 1-使用',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_updated_by` varchar(64) DEFAULT NULL COMMENT '最后更新人',
  `last_updated_time` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
