/*
Navicat MySQL Data Transfer

Source Server         : 内网
Source Server Version : 50631
Source Host           : 192.9.145.165:3306
Source Database       : gdsp-version0.01

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2017-04-07 15:16:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ac_common_dir
-- ----------------------------
DROP TABLE IF EXISTS `ac_common_dir`;
CREATE TABLE `ac_common_dir` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `DIR_NAME` varchar(128) DEFAULT NULL COMMENT '目录名称',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '上级id',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `CATEGORY` varchar(128) DEFAULT NULL COMMENT '类别',
  `DEF1` varchar(128) DEFAULT NULL COMMENT '变量1',
  `DEF2` varchar(128) DEFAULT NULL COMMENT '变量2',
  `DEF3` varchar(128) DEFAULT NULL COMMENT '变量3',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_kpi_relation
-- ----------------------------
DROP TABLE IF EXISTS `ac_kpi_relation`;
CREATE TABLE `ac_kpi_relation` (
  `id` char(32) DEFAULT NULL,
  `kpi_id` char(32) DEFAULT NULL,
  `report_id` char(32) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `createTime` int(11) DEFAULT NULL COMMENT '创建时间',
  `lastModifyBy` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `lastModifyTime` int(11) DEFAULT NULL COMMENT '上次修改时间',
  `version` int(11) DEFAULT NULL COMMENT '版本'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_layout_column
-- ----------------------------
DROP TABLE IF EXISTS `ac_layout_column`;
CREATE TABLE `ac_layout_column` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `LAYOUT_ID` varchar(50) DEFAULT NULL COMMENT '页面布局id',
  `COLUMN_ID` varchar(50) DEFAULT NULL COMMENT '每列id',
  `COLSPAN` int(11) DEFAULT NULL COMMENT '合并单元格',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_page
-- ----------------------------
DROP TABLE IF EXISTS `ac_page`;
CREATE TABLE `ac_page` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `PAGE_NAME` varchar(128) DEFAULT NULL COMMENT '页面名称',
  `PAGE_DESC` varchar(500) DEFAULT NULL COMMENT '页面描述',
  `LAYOUT_ID` varchar(50) DEFAULT NULL COMMENT '布局id',
  `DIR_ID` varchar(50) DEFAULT NULL COMMENT '目录id',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_page_widget
-- ----------------------------
DROP TABLE IF EXISTS `ac_page_widget`;
CREATE TABLE `ac_page_widget` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `COLUMN_ID` varchar(50) DEFAULT NULL COMMENT '列id',
  `WIDGET_TYPE` varchar(50) DEFAULT NULL COMMENT '资源类别',
  `WIDGET_STYLE` varchar(50) DEFAULT NULL COMMENT '资源风格',
  `WIDGET_ID` char(32) DEFAULT NULL COMMENT '资源id',
  `PAGE_ID` char(32) DEFAULT NULL COMMENT '页面id',
  `TITLE` varchar(128) DEFAULT NULL COMMENT '标题',
  `TITLE_SHOW` char(1) DEFAULT NULL COMMENT '是否展示标题',
  `WIDTH` varchar(50) DEFAULT NULL COMMENT '宽度',
  `HEIGHT` int(11) DEFAULT NULL COMMENT '高度',
  `AUTO_HEIGHT` char(1) DEFAULT NULL COMMENT '自动高度',
  `FRAMESKIN` smallint(6) DEFAULT NULL COMMENT '框架皮肤',
  `OPENSTATE` char(1) DEFAULT NULL COMMENT '开放状态',
  `TOOLBAR` char(1) DEFAULT NULL COMMENT '工具栏',
  `SHORTCUTBAR` char(1) DEFAULT NULL COMMENT '短切条',
  `SIMPLETABLE` char(1) DEFAULT NULL COMMENT '简单表',
  `COLSPAN` int(11) DEFAULT NULL COMMENT '合并单元格',
  `ACTIONS` varchar(256) DEFAULT NULL COMMENT '动作',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_param
-- ----------------------------
DROP TABLE IF EXISTS `ac_param`;
CREATE TABLE `ac_param` (
  `id` char(32) NOT NULL,
  `display_name` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `view_type` varchar(20) DEFAULT NULL,
  `pid` char(32) DEFAULT NULL,
  `cube_text_format` varchar(128) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `lastModifyBy` varchar(32) DEFAULT NULL,
  `lastModifyTime` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `data_from` text,
  `data_from_type` varchar(20) DEFAULT NULL,
  `default_value` varchar(200) DEFAULT NULL,
  `mustput` char(1) DEFAULT NULL,
  `colspan` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数定义';

-- ----------------------------
-- Table structure for ac_param_library
-- ----------------------------
DROP TABLE IF EXISTS `ac_param_library`;
CREATE TABLE `ac_param_library` (
  `id` char(32) NOT NULL COMMENT 'id',
  `name` varchar(128) DEFAULT NULL COMMENT '资源动作名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '资源动作说明',
  `pid` char(32) DEFAULT NULL COMMENT '上级id',
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `lastModifyBy` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `lastModifyTime` int(20) DEFAULT NULL COMMENT '上次修改时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数库';

-- ----------------------------
-- Table structure for ac_param_relation
-- ----------------------------
DROP TABLE IF EXISTS `ac_param_relation`;
CREATE TABLE `ac_param_relation` (
  `id` char(32) NOT NULL,
  `param_id` char(32) DEFAULT NULL,
  `report_id` char(32) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `lastModifyBy` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `lastModifyTime` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_report_node
-- ----------------------------
DROP TABLE IF EXISTS `ac_report_node`;
CREATE TABLE `ac_report_node` (
  `id` char(32) NOT NULL,
  `resource_id` varchar(100) DEFAULT NULL,
  `report_name` varchar(256) DEFAULT NULL,
  `parent_path` varchar(2048) DEFAULT NULL,
  `report_path` varchar(2048) DEFAULT NULL,
  `leaf` char(1) DEFAULT '',
  `cube_flag` char(32) DEFAULT NULL,
  `data_source` char(32) DEFAULT NULL,
  `param_type` varchar(50) DEFAULT NULL,
  `param_template_path` varchar(2048) DEFAULT NULL,
  `comments` text,
  `createBy` varchar(32) DEFAULT NULL,
  `createTime` int(11) DEFAULT NULL,
  `lastModifyBy` varchar(32) DEFAULT NULL,
  `lastModifyTime` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `report_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_widget_action
-- ----------------------------
DROP TABLE IF EXISTS `ac_widget_action`;
CREATE TABLE `ac_widget_action` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `CODE` varchar(50) DEFAULT NULL COMMENT '编码',
  `NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `TEMPLATE` text COMMENT '模板',
  `WIDGETTYPE` varchar(50) DEFAULT NULL COMMENT '组件类型',
  `WIDGETID` varchar(50) DEFAULT NULL COMMENT '资源id',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '内容',
  `IS_PRESET` char(1) DEFAULT NULL COMMENT '预置',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ac_widget_meta
-- ----------------------------
DROP TABLE IF EXISTS `ac_widget_meta`;
CREATE TABLE `ac_widget_meta` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `STYLE` varchar(128) DEFAULT NULL COMMENT '风格',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '类型',
  `PREFERENCE` text COMMENT '优先权',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修过',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `MEMO` varchar(500) DEFAULT NULL COMMENT '内容',
  `DIRID` varchar(2048) DEFAULT NULL COMMENT '目录id',
  `LOADURL` varchar(128) DEFAULT NULL COMMENT '负载网址',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bp_idxdimrel
-- ----------------------------
DROP TABLE IF EXISTS `bp_idxdimrel`;
CREATE TABLE `bp_idxdimrel` (
  `ID` char(32) NOT NULL,
  `indexid` varchar(32) DEFAULT NULL,
  `dimid` varchar(32) DEFAULT NULL,
  `createby` varchar(32) DEFAULT NULL,
  `createtime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标关联维度表';

-- ----------------------------
-- Table structure for bp_indexinfo
-- ----------------------------
DROP TABLE IF EXISTS `bp_indexinfo`;
CREATE TABLE `bp_indexinfo` (
  `id` char(32) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `indexCode` varchar(32) NOT NULL,
  `indexName` varchar(128) DEFAULT NULL,
  `indexCodeName` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
  `indexColumnName` varchar(18) DEFAULT NULL,
  `indexTableId` varchar(50) DEFAULT NULL,
  `indexTableName` varchar(50) DEFAULT NULL,
  `statfreq` varchar(10) DEFAULT NULL,
  `datasource` varchar(50) DEFAULT NULL,
  `comedepart` varchar(32) DEFAULT NULL,
  `businessbore` text,
  `techbore` text,
  `peicision` int(11) DEFAULT NULL,
  `meterunit` varchar(10) DEFAULT NULL,
  `onlyflexiablequery` char(1) DEFAULT NULL,
  `remark` text CHARACTER SET utf8mb4,
  `createby` varchar(32) DEFAULT NULL,
  `createtime` bigint(20) DEFAULT NULL,
  `lastmodifyby` varchar(32) DEFAULT NULL,
  `lastmodifytime` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标信息表';

-- ----------------------------
-- Table structure for bp_indgroups
-- ----------------------------
DROP TABLE IF EXISTS `bp_indgroups`;
CREATE TABLE `bp_indgroups` (
  `pk_fatherid` varchar(32) DEFAULT NULL,
  `ID` char(32) NOT NULL,
  `groupName` varchar(128) DEFAULT NULL,
  `groupCode` varchar(128) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `innercode` varchar(50) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `lastModifyBy` varchar(32) DEFAULT NULL,
  `lastModifyTime` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标组表';

-- ----------------------------
-- Table structure for bp_indgroup_rlt
-- ----------------------------
DROP TABLE IF EXISTS `bp_indgroup_rlt`;
CREATE TABLE `bp_indgroup_rlt` (
  `ID` char(32) NOT NULL,
  `pk_index` varchar(50) DEFAULT NULL,
  `pk_indexgroup` varchar(50) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标组关联指标表';

-- ----------------------------
-- Table structure for cp_datadic
-- ----------------------------
DROP TABLE IF EXISTS `cp_datadic`;
CREATE TABLE `cp_datadic` (
  `Id` char(32) NOT NULL COMMENT '主键',
  `dic_name` varchar(128) NOT NULL COMMENT '数据字典名称',
  `dic_code` varchar(50) NOT NULL COMMENT '数据字典编码',
  `dic_desc` varchar(256) DEFAULT NULL COMMENT '数据字典描述',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  `createBy` varchar(32) NOT NULL COMMENT '创建人',
  `lastModifyBy` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `lastModifyTime` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `version` int(11) NOT NULL COMMENT '记录行版本',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `dic_codeUnique` (`dic_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_datadicval 数据字典维值表
-- ----------------------------
DROP TABLE IF EXISTS `cp_datadicval`;
CREATE TABLE `cp_datadicval` (
  `id` char(32) NOT NULL COMMENT '主键',
  `dimvl_name` varchar(128) NOT NULL COMMENT '维值名称',
  `dimvl_code` varchar(50) NOT NULL COMMENT '维值编码',
  `dimvl_desc` varchar(256) DEFAULT NULL COMMENT '维值描述',
  `pk_fatherId` varchar(32) DEFAULT NULL COMMENT '上级维值id',
  `pk_dicId` varchar(32) NOT NULL COMMENT '数据字典id',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  `createBy` varchar(32) NOT NULL COMMENT '创建人',
  `lastModifyBy` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `lastModifyTime` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `version` int(11) NOT NULL COMMENT '记录行版本',
  PRIMARY KEY (`id`)
-- UNIQUE KEY `dimvl_codeUnique` (`dimvl_code`)  -- 去除维度值的唯一性约束(指标编码可以重复)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_defdoc
-- ----------------------------
DROP TABLE IF EXISTS `cp_defdoc`;
CREATE TABLE `cp_defdoc` (
  `ID` varchar(50) NOT NULL COMMENT 'id',
  `TYPE_ID` varchar(32) DEFAULT NULL COMMENT '类型id',
  `DOC_CODE` varchar(128) DEFAULT NULL COMMENT '编码',
  `DOC_NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `DOC_DESC` varchar(500) DEFAULT NULL COMMENT '描述',
  `IS_PRESET` char(1) DEFAULT NULL COMMENT '预置',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `PK_FATHERID` varchar(32) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` int(11) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后 修改',
  `LASTMODIFYTIME` int(11) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_defdoclist
-- ----------------------------
DROP TABLE IF EXISTS `cp_defdoclist`;
CREATE TABLE `cp_defdoclist` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `TYPE_CODE` varchar(50) DEFAULT NULL COMMENT '类型编码',
  `TYPE_NAME` varchar(128) DEFAULT NULL COMMENT '类型名称',
  `TYPE_DESC` varchar(500) DEFAULT NULL COMMENT '类型描述',
  `IS_PRESET` char(1) DEFAULT NULL COMMENT '预置',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` int(11) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` int(11) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `DEF_TYPE` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_favorites
-- ----------------------------
DROP TABLE IF EXISTS `cp_favorites`;
CREATE TABLE `cp_favorites` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `NAME` varchar(128) DEFAULT NULL,
  `PID` varchar(32) DEFAULT NULL,
  `URL` varchar(256) DEFAULT NULL,
  `FILE_TYPE` tinyint(4) DEFAULT NULL COMMENT '1 : 目录 ;  2 : 收藏点',
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_loginlog
-- ----------------------------
DROP TABLE IF EXISTS `cp_loginlog`;
CREATE TABLE `cp_loginlog` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `LOGIN_ACCOUNT` varchar(50) DEFAULT NULL,
  `IP_ADDR` varchar(20) DEFAULT NULL,
  `MAC_ADDR` varchar(20) DEFAULT NULL,
  `LOGIN_STATUS` char(1) DEFAULT NULL COMMENT '1 : 目录 ;  2 : 收藏点',
  `LOGIN_TIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_log_content
-- ----------------------------
DROP TABLE IF EXISTS `cp_log_content`;
CREATE TABLE `cp_log_content` (
  `id` char(32) DEFAULT NULL,
  `log_id` char(32) DEFAULT NULL,
  `col_name` varchar(18) DEFAULT NULL,
  `col_desc` varchar(256) DEFAULT NULL,
  `new_data` text,
  `old_data` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_log_op
-- ----------------------------
DROP TABLE IF EXISTS `cp_log_op`;
CREATE TABLE `cp_log_op` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `TABLE_NAME` varchar(18) DEFAULT NULL,
  `TABLE_DESC` varchar(256) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '1 : 目录 ;  2 : 收藏点',
  `TYPE` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_power_dic
-- ----------------------------
DROP TABLE IF EXISTS `cp_power_dic`;
CREATE TABLE `cp_power_dic` (
  `id` char(32) NOT NULL,
  `pk_dataSource` varchar(32) NOT NULL,
  `pk_dataDicId` varchar(32) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `createBy` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_role_datadic
-- ----------------------------
DROP TABLE IF EXISTS `cp_role_datadic`;
CREATE TABLE `cp_role_datadic` (
  `ID` char(32) NOT NULL,
  `pk_role` varchar(32) DEFAULT NULL,
  `pk_dic` varchar(32) DEFAULT NULL,
  `pk_dicval` varchar(32) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cp_sys_conf_ext
-- ----------------------------
DROP TABLE IF EXISTS `cp_sys_conf_ext`;
CREATE TABLE `cp_sys_conf_ext` (
  `ID` char(32) NOT NULL,
  `CONF_NAME` varchar(128) DEFAULT NULL,
  `CONF_CODE` varchar(128) DEFAULT NULL,
  `CONF_VALUE` varchar(128) DEFAULT NULL,
  `VALUE_TYPE` tinyint(4) DEFAULT NULL COMMENT '0:Integer,1:Double,2:逻辑型,3:日期型,4:字符',
  `CATEGORY_NAME` varchar(128) DEFAULT NULL,
  `CATEGORY_CODE` varchar(128) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` int(11) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `LASTMODIFYTIME` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: CP_DATASOURCE_REF                                     */
/*==============================================================*/
DROP TABLE IF EXISTS `cp_datasource_ref`;
CREATE TABLE `cp_datasource_ref`
(
   `ID`                   CHAR(32) NOT NULL COMMENT '审计id',
   `PK_DATASOURCE`        CHAR(32) DEFAULT NULL COMMENT '数据源id',
   `RES_ID`               CHAR(32) DEFAULT NULL COMMENT '资源id(调用数据源)',
   `RES_NAME`             VARCHAR(128) DEFAULT NULL COMMENT '资源名称(调用数据源)',
   `MEMO`                 VARCHAR(256) DEFAULT NULL COMMENT '描述',
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DATASOURCE_LIBRARY                                 */
/*==============================================================*/
DROP TABLE IF EXISTS `cp_datasource_library`;
CREATE TABLE CP_DATASOURCE_LIBRARY
(
   ID                   CHAR(32) NOT NULL COMMENT '审计id',
   DS_TYPE              VARCHAR(32) COMMENT '数据源产品类型标识',
   DS_VERSION           VARCHAR(128) COMMENT '数据源版本号',
   QUALIFIEDCLASSNAME   VARCHAR(256) COMMENT '数据源驱动限定名',
   JARPATH              VARCHAR(256) COMMENT '驱动获取路径',
   CREATEBY             VARCHAR(32) COMMENT '创建人',
   CREATETIME           BIGINT(20) COMMENT '创建时间',
   LASTMODIFYBY         VARCHAR(32) COMMENT '最后修改人',
   LASTMODIFYTIME       BIGINT(20) COMMENT '最后更新时间',
   VERSION              INT(11) COMMENT '版本',
   PRIMARY KEY (ID)
);

-- ----------------------------
-- Table structure for pt_change_history
-- ----------------------------
DROP TABLE IF EXISTS `pt_change_history`;
CREATE TABLE `pt_change_history` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `TITLE` varchar(256) DEFAULT NULL COMMENT '标题',
  `COMMENTS` varchar(521) DEFAULT NULL COMMENT '描述',
  `OPTYPE` varchar(32) DEFAULT NULL COMMENT '操作类型',
  `CHANGETIME` varchar(50) DEFAULT NULL COMMENT '变更时间',
  `LINK_ID` varchar(32) DEFAULT NULL COMMENT '链接id',
  `REPORT_NAME` varchar(128) DEFAULT NULL COMMENT '报表名称',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `CHANGENAME` varchar(128) DEFAULT NULL COMMENT '变更申请人',
  `OPERATIONTIME` bigint(20) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_datasource
-- ----------------------------
DROP TABLE IF EXISTS `pt_datasource`;
CREATE TABLE `pt_datasource` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `CODE` varchar(50) DEFAULT NULL COMMENT '编码',
  `NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `PASSWORD` varchar(512) DEFAULT NULL COMMENT '用户密码',
  `SPAN` varchar(50) DEFAULT NULL COMMENT '跨度',
  `PERMISSIONURL` varchar(2048) DEFAULT NULL COMMENT '权限网址',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '类型',
  `IP` varchar(15) DEFAULT NULL COMMENT 'IP',
  `PORT` varchar(5) DEFAULT NULL COMMENT '接口',
  `CONTEXT` varchar(2048) DEFAULT NULL,
  `DEFAULT_FLAG` smallint(6) DEFAULT NULL COMMENT '默认值',
  `URL` varchar(2048) DEFAULT NULL COMMENT 'URL',
  `COMMENTS` varchar(500) DEFAULT NULL COMMENT '说明',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `isDefault` varchar(1) DEFAULT NULL,
  `DRIVER` varchar(128) DEFAULT NULL,
  `PX_URL` varchar(1024) DEFAULT NULL,
  `path` varchar(500) DEFAULT NULL,
  `dirID` varchar(32) DEFAULT NULL,
  `PRODUCT_VERSION` varchar(128) DEFAULT NULL COMMENT '产品版本号',
  `keytabPath` varchar(2048) DEFAULT NULL COMMENT '秘钥文件绝对路径',
  `krbConfPath` varchar(2048) DEFAULT NULL COMMENT 'kerberos的conf文件绝对路径',
  `authentication_model` int(1) DEFAULT NULL COMMENT '认证模式,-1:认证模式缺省值;0:简单认证模式;1:LDAP认证模式;2:kerberos认证模式',
  `classify` varchar(128) DEFAULT NULL COMMENT '数据源分类',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_sqlds
-- ----------------------------
DROP TABLE IF EXISTS `pt_sqlds`;
CREATE TABLE `pt_sqlds` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `CODE` varchar(50) DEFAULT NULL COMMENT '编码',
  `NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `URL` varchar(2048) DEFAULT NULL COMMENT 'URL',
  `COMMENTS` varchar(256) DEFAULT NULL COMMENT '说明',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `DRIVER` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pt_user_defaultpage
-- ----------------------------
DROP TABLE IF EXISTS `pt_user_defaultpage`;
CREATE TABLE `pt_user_defaultpage` (
  `ID` char(32) NOT NULL,
  `PK_USER` char(32) DEFAULT NULL,
  `PK_PAGE` char(32) DEFAULT NULL,
  `PK_MENU` char(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` longblob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(20) NOT NULL,
  `SCHED_TIME` bigint(20) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL COMMENT '把该属性设置为1，quartz会把job持久化到数据库中',
  `IS_NONCONCURRENT` varchar(1) NOT NULL COMMENT '同时执行多个 Job 实例',
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(20) NOT NULL,
  `CHECKIN_INTERVAL` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` int(11) NOT NULL,
  `REPEAT_INTERVAL` int(11) NOT NULL,
  `TIMES_TRIGGERED` int(11) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` int(11) DEFAULT NULL,
  `LONG_PROP_2` int(11) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PRIORITY` bigint(20) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(20) NOT NULL,
  `END_TIME` bigint(20) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(6) DEFAULT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for res_accesslog
-- ----------------------------
DROP TABLE IF EXISTS `res_accesslog`;
CREATE TABLE `res_accesslog` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `RES_ID` varchar(32) DEFAULT NULL COMMENT '资源id',
  `NAME` varchar(100) DEFAULT NULL COMMENT '名称',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '类型',
  `URL` varchar(1028) DEFAULT NULL COMMENT 'URL',
  `MSG` varchar(256) DEFAULT NULL COMMENT '信息',
  `PK_USER` varchar(32) DEFAULT NULL COMMENT '当前用户',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_help
-- ----------------------------
DROP TABLE IF EXISTS `rms_help`;
CREATE TABLE `rms_help` (
  `ID` char(32) NOT NULL,
  `TITLE` varchar(128) DEFAULT NULL,
  `ATTACH_NAME` varchar(128) DEFAULT NULL,
  `ATTACH_PATH` varchar(2048) DEFAULT NULL,
  `MEMO` varchar(256) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_notice_range
-- ----------------------------
DROP TABLE IF EXISTS `rms_notice_range`;
CREATE TABLE `rms_notice_range` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `RANGE_ID` varchar(32) DEFAULT NULL COMMENT '标题',
  `NOTICE_ID` varchar(32) DEFAULT NULL COMMENT '公告id',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_orgs
-- ----------------------------
DROP TABLE IF EXISTS `rms_orgs`;
CREATE TABLE `rms_orgs` (
  `ID` char(32) NOT NULL,
  `ORGCODE` varchar(50) DEFAULT NULL,
  `ORGNAME` varchar(128) DEFAULT NULL,
  `PK_FATHERORG` varchar(32) DEFAULT NULL,
  `INNERCODE` varchar(50) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SHORTNAME` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_power_butn
-- ----------------------------
DROP TABLE IF EXISTS `rms_power_butn`;
CREATE TABLE `rms_power_butn` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PK_ROLE` varchar(32) DEFAULT NULL,
  `RESOURCE_ID` varchar(32) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_power_menu
-- ----------------------------
DROP TABLE IF EXISTS `rms_power_menu`;
CREATE TABLE `rms_power_menu` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PK_ROLE` varchar(32) DEFAULT NULL,
  `RESOURCE_ID` varchar(32) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_power_org
-- ----------------------------
DROP TABLE IF EXISTS `rms_power_org`;
CREATE TABLE `rms_power_org` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PK_ROLE` varchar(32) DEFAULT NULL,
  `RESOURCE_ID` varchar(32) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_power_page
-- ----------------------------
DROP TABLE IF EXISTS `rms_power_page`;
CREATE TABLE `rms_power_page` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PK_ROLE` varchar(32) DEFAULT NULL,
  `RESOURCE_ID` varchar(32) DEFAULT NULL,
  `OBJTYPE` smallint(6) DEFAULT NULL COMMENT '1：用户\r\n            2：角色',
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_role
-- ----------------------------
DROP TABLE IF EXISTS `rms_role`;
CREATE TABLE `rms_role` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `ROLENAME` varchar(128) DEFAULT NULL,
  `PK_ORG` varchar(32) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `agingLimit` char(1) DEFAULT NULL,
  `PERMISSIONAGING` int(11) DEFAULT NULL,
  `AGINGUNIT` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `rms_sys_notice`;
CREATE TABLE `rms_sys_notice` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `CONTENT` varchar(2000) DEFAULT NULL COMMENT '内容',
  `PUBLISH_DATE` bigint(20) DEFAULT NULL COMMENT '发布时间',
  `START_DATE` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `END_DATE` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `VALID_FLAG` char(1) DEFAULT NULL COMMENT '是否发布',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '最后的检查时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_sys_notice_history
-- ----------------------------
DROP TABLE IF EXISTS `rms_sys_notice_history`;
CREATE TABLE `rms_sys_notice_history` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `NOTICE_ID` varchar(32) DEFAULT NULL COMMENT '标题',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '内容',
  `ACCESS_DATE` bigint(20) DEFAULT NULL COMMENT '发布时间',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '最后的检查时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_user
-- ----------------------------
DROP TABLE IF EXISTS `rms_user`;
CREATE TABLE `rms_user` (
  `ID` char(32) NOT NULL,
  `ACCOUNT` varchar(50) DEFAULT NULL,
  `USERNAME` varchar(128) DEFAULT NULL,
  `USERTYPE` smallint(6) DEFAULT NULL COMMENT '0:超级管理员,1:用户',
  `ONLYADMIN` char(1) DEFAULT NULL COMMENT 'Y 只有管理权限，只能查看功能菜单，业务菜单不能看\r\n            ',
  `USER_PASSWORD` varchar(64) DEFAULT NULL,
  `MOBILE` varchar(30) DEFAULT NULL,
  `TEL` varchar(30) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `SEX` smallint(6) DEFAULT NULL COMMENT '0:男,1:女',
  `PK_ORG` varchar(32) DEFAULT NULL,
  `ISRESET` char(1) DEFAULT NULL COMMENT 'Y：重置  N：未重置',
  `ISLOCKED` char(1) DEFAULT NULL COMMENT '锁定功能:Y锁定N表示未锁定(多次登录,密码错误为锁定)',
  `ABLETIME` bigint(20) DEFAULT NULL,
  `DISABLETIME` bigint(20) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ORIGIN` smallint(6) DEFAULT '0' COMMENT '0:本系统; 1:LDAP;',
  `update_pwd_time` int(11) DEFAULT NULL,
  `isDisabled` char(1) DEFAULT 'N' COMMENT '启用停用Y停用N启用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TMP_RMS_USER                                          */
/*==============================================================*/
CREATE TABLE TMP_RMS_USER
(
   ID                   CHAR(32) NOT NULL COMMENT '主键',
   USERID               CHAR(32) DEFAULT NULL COMMENT '用户id',
   ACCOUNT              VARCHAR(50) DEFAULT NULL COMMENT '同步用户的帐号',
   NAME                 VARCHAR(128) DEFAULT NULL COMMENT '同步用户的用户名',
   ISLOCKED             CHAR(1) DEFAULT NULL COMMENT '用户是否被锁定',
   ISDISABLED           CHAR(1) DEFAULT NULL COMMENT '用户是否被停用',
   MOBILE               VARCHAR(30) DEFAULT NULL COMMENT '手机号',
   TEL                  VARCHAR(30) DEFAULT NULL,
   EMAIL                VARCHAR(50) DEFAULT NULL COMMENT '用户邮箱',
   SEX                  SMALLINT(6) DEFAULT NULL,
   MEMO                 VARCHAR(500) DEFAULT NULL,
   USERCREATETIME      BIGINT(20) DEFAULT NULL COMMENT '同步的用户创建时间',
   USERLASTMODIFYTIME  BIGINT(20) DEFAULT NULL COMMENT '同步用户的最后修改时间',
   SYNCSTATE           CHAR(1) DEFAULT NULL COMMENT '用户是否同步到正式表的状态标志',
   CREATETIME           BIGINT(20) DEFAULT NULL COMMENT '临时表数据创建的时间',
   LASTMODIFYTIME       BIGINT(20) DEFAULT NULL COMMENT '临时表数据最后修改时间',
   PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_usergroup
-- ----------------------------
DROP TABLE IF EXISTS `rms_usergroup`;
CREATE TABLE `rms_usergroup` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `GROUPNAME` varchar(128) DEFAULT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `INNERCODE` varchar(50) DEFAULT NULL,
  `PK_ORG` varchar(32) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `IS_PRESET` char(1) DEFAULT NULL COMMENT '是否是系统预置，如为预置，不能删除',
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_usergroup_rlt
-- ----------------------------
DROP TABLE IF EXISTS `rms_usergroup_rlt`;
CREATE TABLE `rms_usergroup_rlt` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PK_USER` varchar(32) DEFAULT NULL,
  `PK_USERGROUP` varchar(32) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rms_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rms_user_role`;
CREATE TABLE `rms_user_role` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PK_ROLE` varchar(32) DEFAULT NULL,
  `PK_USER` varchar(32) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `aging_enddate` bigint(20) DEFAULT NULL,
  `isprompted` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sched_accesslog
-- ----------------------------
DROP TABLE IF EXISTS `sched_accesslog`;
CREATE TABLE `sched_accesslog` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BEGINTIME` bigint(20) DEFAULT NULL,
  `ENDTIME` bigint(20) DEFAULT NULL,
  `ELAPSEDTIME` bigint(20) DEFAULT NULL,
  `RESULT` smallint(6) DEFAULT NULL,
  `MEMO` varchar(256) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `USERNAME` varchar(128) DEFAULT NULL,
  `LOGNAME` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_butnregister
-- ----------------------------
DROP TABLE IF EXISTS `st_butnregister`;
CREATE TABLE `st_butnregister` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `FUNNAME` varchar(128) DEFAULT NULL,
  `FUNCODE` varchar(50) DEFAULT NULL,
  `URL` varchar(2048) DEFAULT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `ISENABLE` char(1) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_conf
-- ----------------------------
DROP TABLE IF EXISTS `st_conf`;
CREATE TABLE `st_conf` (
  `ID` char(32) DEFAULT NULL,
  `SYSHOMESTATE` char(1) DEFAULT NULL,
  `SYSHOMEURL` varchar(2048) DEFAULT NULL,
  `REPORTINTS` varchar(200) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_funcdec
-- ----------------------------
DROP TABLE IF EXISTS `st_funcdec`;
CREATE TABLE `st_funcdec` (
  `ID` char(32) NOT NULL COMMENT 'id',
  `NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `MEMO` LONGTEXT DEFAULT NULL COMMENT '内容',
  `FILEURL` varchar(2048) DEFAULT NULL COMMENT '文件URL',
  `MENUID` varchar(32) DEFAULT NULL COMMENT '菜单id',
  `CREATEBY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `LASTMODIFYBY` varchar(32) DEFAULT NULL COMMENT '最后修改',
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL COMMENT '上次修改时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `SORTNUM` int(11) DEFAULT NULL COMMENT '分类号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_menuregister
-- ----------------------------
DROP TABLE IF EXISTS `st_menuregister`;
CREATE TABLE `st_menuregister` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `FUNNAME` varchar(128) DEFAULT NULL,
  `FUNCODE` varchar(50) DEFAULT NULL,
  `DISPCODE` varchar(50) DEFAULT NULL,
  `PARENTID` varchar(32) DEFAULT NULL,
  `INNERCODE` varchar(50) DEFAULT NULL,
  `FUNTYPE` smallint(6) DEFAULT NULL COMMENT '0:一级分类,1:下级分类,2:管理菜单,3:业务菜单,4:页面菜单',
  `ISPOWER` char(1) DEFAULT NULL,
  `URL` varchar(2048) DEFAULT NULL,
  `HELPNAME` varchar(2048) DEFAULT NULL,
  `HINTINF` varchar(256) DEFAULT NULL,
  `ISENABLE` char(1) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `MENUID` varchar(32) DEFAULT NULL,
  `SAFELEVEL` varchar(32) DEFAULT NULL,
  `IS_SYSTEM_MENU` char(1) DEFAULT NULL,
  `PAGEID` varchar(32) DEFAULT NULL,
  `ISROOTMENU` char(1) DEFAULT NULL,
  `ISREPORT` char(1) DEFAULT NULL,
  `ICONFIELD` varchar(255) DEFAULT NULL COMMENT '自定义图标的路径',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_pageregister
-- ----------------------------
DROP TABLE IF EXISTS `st_pageregister`;
CREATE TABLE `st_pageregister` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `FUNNAME` varchar(128) DEFAULT NULL,
  `DISPCODE` varchar(50) DEFAULT NULL,
  `MENUID` varchar(32) DEFAULT NULL,
  `PAGEID` varchar(32) DEFAULT NULL,
  `URL` varchar(2048) DEFAULT NULL,
  `MEMO` varchar(256) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_param
-- ----------------------------
DROP TABLE IF EXISTS `st_param`;
CREATE TABLE `st_param` (
  `ID` varchar(32) NOT NULL,
  `PARNAME` varchar(128) DEFAULT NULL,
  `PARCODE` varchar(50) DEFAULT NULL,
  `PARGROUPID` varchar(50) DEFAULT NULL,
  `VALUETYPE` smallint(6) DEFAULT NULL,
  `VALUERANGE` varchar(256) DEFAULT NULL,
  `DEFAULTVALUE` varchar(128) DEFAULT NULL,
  `PARVALUE` varchar(128) DEFAULT NULL,
  `MEMO` varchar(256) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_par_def
-- ----------------------------
DROP TABLE IF EXISTS `st_par_def`;
CREATE TABLE `st_par_def` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `PARNAME` varchar(200) DEFAULT NULL,
  `PARCODE` varchar(30) DEFAULT NULL,
  `GROUPNAME` varchar(200) DEFAULT NULL,
  `GROUPCODE` varchar(30) DEFAULT NULL,
  `VALUETYPE` smallint(6) DEFAULT NULL COMMENT '0:整型,1:DOUBLE型,2:逻辑型,3:日期型,4:字符',
  `VALUERANGE` varchar(200) DEFAULT NULL,
  `DEFAULTVALUE` varchar(100) DEFAULT NULL,
  `PARVALUE` varchar(100) DEFAULT NULL,
  `MEMO` varchar(200) DEFAULT NULL,
  `LASTMODIFYTIME` int(11) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_passset
-- ----------------------------
DROP TABLE IF EXISTS `st_passset`;
CREATE TABLE `st_passset` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `MINLEN` int(11) DEFAULT NULL,
  `MAXLEN` int(11) DEFAULT NULL,
  `ISLOWER` char(1) DEFAULT NULL,
  `ISUPPER` char(1) DEFAULT NULL,
  `ISNUMBER` char(1) DEFAULT NULL,
  `ISSPECIAL` char(1) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_resregister
-- ----------------------------
DROP TABLE IF EXISTS `st_resregister`;
CREATE TABLE `st_resregister` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `PARENTTYPE` smallint(6) DEFAULT NULL COMMENT '0:菜单，1:页面',
  `PARENTID` varchar(32) DEFAULT NULL COMMENT '菜单ID/页面ID',
  `URL` varchar(2048) DEFAULT NULL,
  `MEMO` varchar(500) DEFAULT NULL,
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYTIME` bigint(20) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tmp_cp_log_content
-- ----------------------------
DROP TABLE IF EXISTS `tmp_cp_log_content`;
CREATE TABLE `tmp_cp_log_content` (
  `id` char(32) DEFAULT NULL,
  `log_id` char(32) DEFAULT NULL,
  `col_name` varchar(18) DEFAULT NULL,
  `col_desc` varchar(256) DEFAULT NULL,
  `new_data` text,
  `old_data` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tmp_cp_log_op
-- ----------------------------
DROP TABLE IF EXISTS `tmp_cp_log_op`;
CREATE TABLE `tmp_cp_log_op` (
  `id` char(32) DEFAULT NULL,
  `table_name` varchar(18) DEFAULT NULL,
  `table_desc` varchar(256) DEFAULT NULL,
  `createBy` varchar(32) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `service_id` char(32) DEFAULT NULL,
  `data_id` char(32) DEFAULT NULL,
  `type` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tools_message
-- ----------------------------
DROP TABLE IF EXISTS `tools_message`;
CREATE TABLE `tools_message` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `TOUSERID` varchar(32) NOT NULL COMMENT '接受者ID',
  `FROMUSERID` varchar(32) NOT NULL COMMENT '发送者ID',
  `TOUSERACCOUT` varchar(32) NOT NULL COMMENT '接受者账号',
  `FROMUSERACCOUNT` varchar(32) NOT NULL COMMENT '发送者账号',
  `SUBJECT` varchar(128) DEFAULT NULL COMMENT '主题',
  `CONTENT` text COMMENT '内容',
  `IFLOOK` char(1) DEFAULT NULL COMMENT '是否查看',
  `TRANSTIME` bigint(20) DEFAULT NULL COMMENT '发送时间',
  `MSGTYPE` smallint(6) DEFAULT NULL COMMENT '信息 1 mail 2 回复信息',
  `REPLY_BY` varchar(32) DEFAULT NULL COMMENT '回复',
  `ATTACHMENTS` varchar(128) DEFAULT NULL COMMENT '附件',
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: FLOW_BUSINESSFORM                                     */
/*==============================================================*/
DROP TABLE IF EXISTS `flow_businessform`;
CREATE TABLE `flow_businessform` (
  `ID` char(32) NOT NULL,
  `PK_DEPLOYMENTID` varchar(32) DEFAULT NULL COMMENT '流程部署id',
  `FORMID` varchar(32) DEFAULT NULL COMMENT '业务key',
  `PARAMS` varchar(4000) DEFAULT NULL COMMENT '接收到的流程参数',
  `STATUS` smallint(6) DEFAULT NULL COMMENT '流程状态\r\n            0：保存流程\r\n            1：流程开启\r\n            2：流程结束',
  `DOWNLOADURL` varchar(2048) DEFAULT NULL COMMENT '附件下载url',
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` int(11) DEFAULT NULL,
  `LASTMODIFYTIME` int(11) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE FLOW_BUSINESSFORM COMMENT '存储流程业务信息';


/*==============================================================*/
/* Table: FLOW_FORM_PROCEINST_RLT                               */
/*==============================================================*/
DROP TABLE IF EXISTS `flow_form_proceinst_rlt`;
CREATE TABLE `flow_form_proceinst_rlt` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `PK_BUSINESSFORMID` varchar(32) DEFAULT NULL COMMENT '流程业务key',
  `PROCEINSTID` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` int(11) DEFAULT NULL,
  `LASTMODIFYTIME` int(11) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE FLOW_FORM_PROCEINST_RLT COMMENT '业务关联流程实例';

/*==============================================================*/
/* Table: FLOW_NOTIFYEVENTDETAIL                                */
/*==============================================================*/
DROP TABLE IF EXISTS `flow_notifyeventdetail`;
CREATE TABLE `flow_notifyeventdetail` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `PK_DEPLOYMENTID` varchar(32) DEFAULT NULL COMMENT '流程部署ID',
  `PK_NODEINFOID` varchar(32) DEFAULT NULL COMMENT '流程节点ID',
  `PK_EVENTTYPEID` varchar(32) DEFAULT NULL COMMENT '流程事件类型ID',
  `NOTIFYURL` varchar(2048) DEFAULT NULL COMMENT '通知url',
  `CREATEBY` varchar(32) DEFAULT NULL,
  `CREATETIME` int(11) DEFAULT NULL,
  `LASTMODIFYTIME` int(11) DEFAULT NULL,
  `LASTMODIFYBY` varchar(32) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: FLOW_APPRAUTHORITY                                    */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_APPRAUTHORITY;
CREATE TABLE FLOW_APPRAUTHORITY
(
   ID                   VARCHAR(32) NOT NULL,
   NODEINFOID           VARCHAR(32),
   APPRTYPE             VARCHAR(32),
   APPRTYPEID           VARCHAR(32),
   APPRTYPECODE         VARCHAR(128),
   APPRTYPENAME         VARCHAR(128),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_CATEGORY                                         */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_CATEGORY;
CREATE TABLE FLOW_CATEGORY
(
   ID                   VARCHAR(32) NOT NULL,
   CATEGORYCODE         VARCHAR(128),
   CATEGORYNAME         VARCHAR(128),
   MEMO                 VARCHAR(256),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   INNERCODE            VARCHAR(50),
   PK_FATHERCODE        VARCHAR(32),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_DELEGATE                                         */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_DELEGATE;
CREATE TABLE FLOW_DELEGATE
(
   ID                   VARCHAR(32) NOT NULL,
   STARTTIME            INT,
   ENDTIME              INT,
   USERID               VARCHAR(32),
   ACCEPTID             VARCHAR(32),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_DELEGATE_DETAIL                                  */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_DELEGATE_DETAIL;
CREATE TABLE FLOW_DELEGATE_DETAIL
(
   ID                   VARCHAR(32) NOT NULL,
   KID                  VARCHAR(32),
   DEPLOYMENTID         VARCHAR(64),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_DEPLOYMENT                                       */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_DEPLOYMENT;
CREATE TABLE FLOW_DEPLOYMENT
(
   ID                   VARCHAR(32) NOT NULL,
   DEPLOYMENTCODE       VARCHAR(128),
   DEPLOYMENTNAME       VARCHAR(256),
   DEPLOYID             VARCHAR(64),
   PROCDEFID            VARCHAR(64),
   STATE                SMALLINT,
   FORMTYPEID           VARCHAR(32),
   CATEGORYID           VARCHAR(32),
   METAINFO             LONGTEXT,
   MEMO                 VARCHAR(256),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_EVENT                                            */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_EVENT;
CREATE TABLE FLOW_EVENT
(
   ID                   VARCHAR(32) NOT NULL,
   EVENTCODE            VARCHAR(128),
   EVENTNAME            VARCHAR(128),
   EVENTTYPE            VARCHAR(32),
   FORMTYPEID           VARCHAR(32),
   IMPLEMENT            VARCHAR(256),
   NOTE                 VARCHAR(256),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_EVENTTYPE                                        */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_EVENTTYPE;
CREATE TABLE FLOW_EVENTTYPE
(
   ID                   VARCHAR(32) NOT NULL,
   EVENTTYPECODE        VARCHAR(128),
   EVENTTYPENAME        VARCHAR(128),
   EVENTINTERFACE       VARCHAR(256),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);



/*==============================================================*/
/* Table: FLOW_FORMTYPE                                         */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_FORMTYPE;
CREATE TABLE FLOW_FORMTYPE
(
   ID                   CHAR(32) NOT NULL,
   FORMCODE             VARCHAR(128),
   FORMNAME             VARCHAR(128),
   FORMURL              VARCHAR(256),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PK_CATEGORYID        VARCHAR(32) COMMENT '对应流程类型外键',
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_FORMVARIABLE                                     */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_FORMVARIABLE;
CREATE TABLE FLOW_FORMVARIABLE
(
   ID                   VARCHAR(32) NOT NULL,
   FORMTYPEID           VARCHAR(32),
   VARIABLENAME         VARCHAR(128),
   DISPLAYNAME          VARCHAR(128),
   MEMO                 VARCHAR(128),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   COLUMN_11            CHAR(10),
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_NODEINFO                                         */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_NODEINFO;
CREATE TABLE FLOW_NODEINFO
(
   ID                   VARCHAR(32) NOT NULL,
   PROCDEFKEY           VARCHAR(128),
   DEPLOYMENTID         VARCHAR(32),
   ACTID                VARCHAR(64),
   ACTNAME              VARCHAR(128),
   FORMTYPEID           VARCHAR(32),
   TIMERTASKID          VARCHAR(32),
   NOTICE               SMALLINT,
   MULTIINSTYPE         VARCHAR(32),
   MULTIINSVALUE        INT,
   EVENTTYPEID          VARCHAR(32),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_PROCESSHISTORY                                   */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_PROCESSHISTORY;
CREATE TABLE FLOW_PROCESSHISTORY
(
   ID                   VARCHAR(32) NOT NULL,
   DEPLOYMENTID         VARCHAR(32),
   PROCESSINSID         VARCHAR(64),
   ACTID                VARCHAR(64),
   TASKID               VARCHAR(64),
   ACTNAME              VARCHAR(255),
   OPTIONS              VARCHAR(255),
   USERID               VARCHAR(32),
   RESULT               SMALLINT,
   FORMID               VARCHAR(64),
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FLOW_TIMERTASK                                        */
/*==============================================================*/
DROP TABLE IF EXISTS FLOW_TIMERTASK;
CREATE TABLE FLOW_TIMERTASK
(
   ID                   VARCHAR(32) NOT NULL,
   DEPLOYMENTID         VARCHAR(32),
   EXECTYPE             INT,
   FORMTYPEID           VARCHAR(32),
   ACTID                VARCHAR(64),
   LENGTH               INT,
   UNIT                 VARCHAR(10),
   ISWORKDAYS           SMALLINT,
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: FORM_LEAVE                                            */
/*==============================================================*/
DROP TABLE IF EXISTS FORM_LEAVE;
CREATE TABLE FORM_LEAVE
(
   ID                   VARCHAR(32) NOT NULL,
   STARTTIME            INT,
   ENDTIME              INT,
   PHONE                VARCHAR(32),
   LEAVETYPE            VARCHAR(32),
   REASON               VARCHAR(256),
   LEAVEDAY             INT,
   CREATEBY             VARCHAR(32),
   CREATETIME           INT,
   LASTMODIFYTIME       INT,
   LASTMODIFYBY         VARCHAR(32),
   VERSION              INT,
   PRIMARY KEY (ID)
);

ALTER TABLE FORM_LEAVE COMMENT '测试用的单据';

/*==============================================================*/
/* Table: MAIL_REGISTRY                                            */
/*==============================================================*/
CREATE TABLE `cp_mail_registry` (
  `id` char(32) NOT NULL,
  `appkey` varchar(32) NOT NULL,
  `host` varchar(20) NOT NULL,
  `port` varchar(6) NOT NULL,
  `fromAddress` varchar(100) NOT NULL,
  `userName` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `validate` char(1) NOT NULL,
  `createby` varchar(32) DEFAULT NULL,
  `createtime` bigint(20) DEFAULT NULL,
  `lastmodifytime` bigint(20) DEFAULT NULL,
  `lastmodifyby` varchar(32) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

