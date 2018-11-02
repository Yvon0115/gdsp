/*
Navicat MySQL Data Transfer

Source Server         : 内网
Source Server Version : 50631
Source Host           : 192.9.145.165:3306
Source Database       : gdsp-version0.01

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2017-04-07 15:18:52
*/
-- ----------------------------
-- Records of ac_common_dir
-- ----------------------------
BEGIN;
INSERT INTO `ac_common_dir` (`ID`, `DIR_NAME`, `PARENT_ID`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `CATEGORY`, `DEF1`, `DEF2`, `DEF3`) VALUES ('100001', '页面资源', '', '0', '', null, '', null, null, 'pageres', '', '', '');
INSERT INTO `ac_common_dir` (`ID`, `DIR_NAME`, `PARENT_ID`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `CATEGORY`, `DEF1`, `DEF2`, `DEF3`) VALUES ('100002', '公共资源', '', '1', '', null, '1b85da1f28834a7a93965073cc77be3a', '1483695793', '14', 'widgetmeta', '', '', '');
INSERT INTO `ac_common_dir` (`ID`, `DIR_NAME`, `PARENT_ID`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `CATEGORY`, `DEF1`, `DEF2`, `DEF3`) VALUES ('100003', '外部资源', '', '2', '', null, '1b85da1f28834a7a93965073cc77be3a', '1483695803', '9', 'widgetmeta', '', '', '');
COMMIT;

-- ----------------------------
-- Records of ac_kpi_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_layout_column
-- ----------------------------
BEGIN;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('1', 'np_layout1', 'np_column1', '8', '1')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('10', 'np_layout5', 'np_column10', '6', '10')    ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('11', 'np_layout6', 'np_column11', '4', '11')    ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('12', 'np_layout6', 'np_column12', '4', '12')    ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('13', 'np_layout6', 'np_column13', '4', '13')    ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('14', 'np_layout_tabs', 'np_column14', '12', '0');
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('2', 'np_layout1', 'np_column2', '4', '2')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('3', 'np_layout2', 'np_column3', '3', '3')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('4', 'np_layout2', 'np_column4', '6', '4')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('5', 'np_layout2', 'np_column5', '3', '5')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('6', 'np_layout3', 'np_column6', '4', '6')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('7', 'np_layout3', 'np_column7', '8', '7')       ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('8', 'np_layout4', 'np_column8', '12', '8')      ;
INSERT INTO `ac_layout_column` (`ID`, `LAYOUT_ID`, `COLUMN_ID`, `COLSPAN`, `SORTNUM`) VALUES ('9', 'np_layout5', 'np_column9', '6', '9')       ;
COMMIT;

-- ----------------------------
-- Records of ac_page
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_page_widget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_param_library
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_param_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_report_node
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_widget_action
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of ac_widget_meta
-- ----------------------------
BEGIN;
INSERT INTO `ac_widget_meta` (`ID`, `NAME`, `STYLE`, `TYPE`, `PREFERENCE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `MEMO`, `DIRID`, `LOADURL`) VALUES ('0ff7af1cfc3d413495be2c5e2044a7e2', '个人消息', NULL, '公共资源', '', '1b85da1f28834a7a93965073cc77be3a', '1498641494', NULL, NULL, '1', '', '100002', '/tools/message/simpleMessageDlg.d');
INSERT INTO `ac_widget_meta` (`ID`, `NAME`, `STYLE`, `TYPE`, `PREFERENCE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `MEMO`, `DIRID`, `LOADURL`) VALUES ('4155e20a57ea446eb213d6c9f87ba82c', '公告', NULL, '公共资源', '', '1b85da1f28834a7a93965073cc77be3a', '1498641439', NULL, NULL, '1', '', '100002', '/sysnotice/simpleList.d');
INSERT INTO `ac_widget_meta` (`ID`, `NAME`, `STYLE`, `TYPE`, `PREFERENCE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `MEMO`, `DIRID`, `LOADURL`) VALUES ('4e11678b64ae4c59bb7930e1fa7796e7', '最近访问', NULL, '公共资源', '', '1b85da1f28834a7a93965073cc77be3a', '1498459832', NULL, NULL, '1', '', '100002', '/systools/log/recentVisitTop10.d');

COMMIT;

-- ----------------------------
-- Records of bp_idxdimrel
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of bp_indexinfo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of bp_indgroup_rlt
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of bp_indgroups
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_datadic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_datadicval
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_defdoc
-- ----------------------------
BEGIN;
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('03da85591e774e1c872c08bdcfa52ef2', 'ac_datasource_type', 'runqian', 'RAQSoft（润乾）', '', null, '281', '', '1b85da1f28834a7a93965073cc77be3a', '1470807150', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('0534e80b5ab649518edfb14bf317ea61', 'frequency', 'S', '季', '', null, '11', null, '1b85da1f28834a7a93965073cc77be3a', '1458610646', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('086c718d59524d9280e71f234d4eaa5e', '24f0e71e9ed04cf2a424f161bf9c3250', '01', 'nsSMSView.QRY_KPI_ACCT_A_MKT_VAL_BY_INVST', '账户持A股市值(投资者类别)', null, '211', null, '284abd143ac0407eb29ac4d940254b54', '1462781391', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('0fba0055e1ad40f180e4a410b97674dd', '24f0e71e9ed04cf2a424f161bf9c3250', '14', 'nsSMSView.QRY_KPI_PTC_BOND_TRAD_ACCT_VOL', '参与债券交易账户数', null, '224', null, '284abd143ac0407eb29ac4d940254b54', '1462781579', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('10d1e7ddfa28434090278e90823a7a32', 'ac_comp_type', 'year', '年', '', null, '289', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471585247', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('2b671e3563b643dbae7a25aa992ef857', 'object_001', '2', '专业机构35', '专业机构', null, '3', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('2b671e3563b643dbae7a25aa992ef858', 'object_001', '3', '一般机构35', '一般机构', null, '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('2b671e3563b643dbae7a25aa992ef859', 'object_001', '-1', '全部投资者35', '全部投资者', null, '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('2f31777fe64e46c8970ffa468e0b4101', 'ac_comp_type', 'day', '天', '', null, '287', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471585262', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('3041098aaedf426b9eb745948cafffa1', '24f0e71e9ed04cf2a424f161bf9c3250', '18', 'nsSMSView.QRY_KPI_STK_TRAD_BY_INVST_IDSTR', '股票成交情况(投资者及行业)', null, '228', null, '284abd143ac0407eb29ac4d940254b54', '1462781635', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('3373a970b786449592969eb6fbf56e53', 'ac_res_type', 'birt', 'birt', null, '', '291', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471834097', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('34aafc6ca3404f459667f261d29e30bf', '24f0e71e9ed04cf2a424f161bf9c3250', '19', 'nsSMSView.QRY_KPI_STK_TRAD_BY_PPDM', '股票成交情况(辖区)', null, '229', null, '284abd143ac0407eb29ac4d940254b54', '1462781646', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('3af409df0d154561b24ba7a430073a89', 'ef810bc74b92408e8e8c989013ade604', 'NSSMSVIEW.QRY_DTL_COMP_FINA_IDX_INFO', '上市公司财务数据明细查询', '', null, '279', null, 'f7611d4e6fa84340bb2fdb6901e49de0', '1463550512', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('3d0dd60a20f84a9ba2df23534c89b3bd', 'ac_datasource_type', 'birt', 'birt', '', null, '292', '', '88b29d3313ce4f9389f40e22e8e5a0e3', '1471834119', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('3f655a2c67c045b3a4f6fdba4ec166c3', '24f0e71e9ed04cf2a424f161bf9c3250', '06', 'nsSMSView.QRY_KPI_COMP_CNT_CAP_MKT_VAL', '公司家数及股本市值(股本板块)', null, '216', null, '284abd143ac0407eb29ac4d940254b54', '1462781465', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('458476248dc3444ea8b22bf2824bbc73', '24f0e71e9ed04cf2a424f161bf9c3250', '07', 'nsSMSView.QRY_KPI_COMP_CNT_CAP_MKT_VAL_ST', '公司家数及股本市值(风险警示)', null, '217', null, '284abd143ac0407eb29ac4d940254b54', '1462781486', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('53ed75a3c7fe4da9b2754342823be44a', 'ac_datasource_type', 'smartbi', 'smartbi', null, null, '293', null, '1b85da1f28834a7a93965073cc77be3a', '1488868087', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('56133a98dd1f4c7a8c9098f3de1c2db9', '24f0e71e9ed04cf2a424f161bf9c3250', '13', 'nsSMSView.QRY_KPI_PE_PB', '市盈率/市净率', null, '223', null, '284abd143ac0407eb29ac4d940254b54', '1462781566', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('582044cd199d4f438996b6f6e10acc83', 'ac_data_type', 'date', '日期控件', '', null, '286', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471585099', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('58c59f85e61544228218094f4790fc61', 'ac_res_type', 'cognos', 'cognos', '', null, '283', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1470902455', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('5b1ce2bd8e984d38b47962f2bf3722d5', 'ac_cognos_rpt_type', 'fix_report', '原生报表', '', null, '285', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1470907883', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('68355b817b2f4173b2a1e6ae1860e539', 'ac_datasource_type', 'cognos', 'cognos', '', null, '280', '', '1b85da1f28834a7a93965073cc77be3a', '1470807092', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('690d3659ba854f8494839192fee2a092', '24f0e71e9ed04cf2a424f161bf9c3250', '02', 'nsSMSView.QRY_KPI_ACCT_A_MKT_V_BY_PPDM_IDSTR', '账户持A股市值(投资者开户辖区及行业)', null, '212', null, '284abd143ac0407eb29ac4d940254b54', '1462781405', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('73461be43630402295305837a965db70', '24f0e71e9ed04cf2a424f161bf9c3250', '16', 'nsSMSView.QRY_KPI_SEC_OPN_ACCT_VOL', '证券开户账户数', null, '226', null, '284abd143ac0407eb29ac4d940254b54', '1462781616', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('78a8f369d9ef4edb8452936cfe4fb35f', '24f0e71e9ed04cf2a424f161bf9c3250', '10', 'nsSMSView.QRY_KPI_IPO_ISS_IDX', '新股发行指标', null, '220', null, '284abd143ac0407eb29ac4d940254b54', '1462781527', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('799259b5c8244338aeb23325a915a3ae', '24f0e71e9ed04cf2a424f161bf9c3250', '12', 'nsSMSView.QRY_KPI_OAP_ACCT_VOL', '一码通账户数', null, '222', null, '284abd143ac0407eb29ac4d940254b54', '1462781558', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('8814eceb054f4c9cb4498b433fd879ec', '24f0e71e9ed04cf2a424f161bf9c3250', '15', 'nsSMSView.QRY_KPI_SC_IDX', '证券公司指标', null, '225', null, '284abd143ac0407eb29ac4d940254b54', '1462781592', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('8c58d7edc5444854a70a48760b8dc160', '24f0e71e9ed04cf2a424f161bf9c3250', '08', 'nsSMSView.QRY_KPI_FC_IDX', '期货公司指标', null, '218', null, '284abd143ac0407eb29ac4d940254b54', '1462781500', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('91d8327dd46143299b25e2c662e1e3ad', 'ac_res_type', 'runqian', 'RAQSoft（润乾）', '', null, null, '', null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('aaa057a81e5341379965d66eabdc3b84', '24f0e71e9ed04cf2a424f161bf9c3250', '17', 'nsSMSView.QRY_KPI_SEC_TRAD_HOLD_ACCT_VOL', '证券交易及持有账户数', null, '227', null, '284abd143ac0407eb29ac4d940254b54', '1462781624', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_cognos_rpt_type_01', 'ac_cognos_rpt_type', 'report', '固定报表', '固定报表', 'y', null, null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_cognos_rpt_type_02', 'ac_cognos_rpt_type', 'query', '查询报表', '查询报表', 'y', '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_cognos_rpt_type_03', 'ac_cognos_rpt_type', 'analysis', '分析报表', '分析报表', 'y', '3', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_comp_type_01', 'ac_comp_type', 'text', '文本输入', '文本输入', 'y', '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_comp_type_02', 'ac_comp_type', 'select', '下拉单选', '下拉单选', 'y', '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_comp_type_03', 'ac_comp_type', 'radio', '单选', '单选', 'y', '3', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_comp_type_04', 'ac_comp_type', 'checkbox', '多选', '多选', 'y', '4', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_data_type_01', 'ac_data_type', 'char', '字符串型', '字符串型', 'y', '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_data_type_02', 'ac_data_type', 'int', '整型', '整型', 'y', '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_data_type_03', 'ac_data_type', 'number', '数值型', '数值型', 'y', '3', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_da_type_04', 'edf59da2b8d84a56a93c895dd12d11a1', 'doclist', '自定义档案', '自定义档案', 'y', '4', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_ds_type_01', 'edf59da2b8d84a56a93c895dd12d11a1', 'manual', '手工录入', '手工录入', 'y', '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_param_type_00', 'ac_param_type', 'no', '无条件', '无条件报表', 'y', '0', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_param_type_01', 'ac_param_type', 'free', '自由定义', '需要进一步设置详细参数内容', 'y', '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_param_type_07', 'ac_param_type', 'template', '报表模板', 'template', 'y', '7', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac_res_type_06', 'ac_res_type', 'smartbi', 'smartbi', null, '', '292', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1470902470', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ba2a743d8ac84688a45f2eb7812fb5cd', 'ac_comp_type', 'month', '月', '', null, '288', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471585272', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('cb5b5f4b9f25441c8629e38001b50a06', '24f0e71e9ed04cf2a424f161bf9c3250', '09', 'nsSMSView.QRY_KPI_HOLD_BOND_ACCT_VOL', '持债券账户数', null, '219', null, '284abd143ac0407eb29ac4d940254b54', '1462781515', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('cff4b5213c1b482d9f1effdbf20534de', '9109cdb28c8040c597f7c05a487b7336', '2004', '2004', '', null, '290', null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471585420', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('d2f7b6d23e0a4e258c36ab01e3c5672b', '24f0e71e9ed04cf2a424f161bf9c3250', '03', 'nsSMSView.QRY_KPI_A_MKT_VAL_TNOV_RATE', 'A股市值换手率', null, '213', null, '284abd143ac0407eb29ac4d940254b54', '1462781416', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('d3682c3b35eb425e82cc842cc6f52c58', '24f0e71e9ed04cf2a424f161bf9c3250', '05', 'nsSMSView.QRY_KPI_BOND_ISS_IDX', '债券发行指标', null, '215', null, '284abd143ac0407eb29ac4d940254b54', '1462781442', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('db18d35c1f104107a75e1afa67dbf127', 'ef810bc74b92408e8e8c989013ade604', 'NSSMSVIEW.QRY_DTL_BOND_ISS_VW', '债券明细明细灵活', '', null, '278', null, 'f7611d4e6fa84340bb2fdb6901e49de0', '1463550502', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('e14e6dde9468485abbdcdb3dbee90da9', '24f0e71e9ed04cf2a424f161bf9c3250', '04', 'nsSMSView.QRY_KPI_A_TRAD_AMT', 'A股买卖额', null, '214', null, '284abd143ac0407eb29ac4d940254b54', '1462781427', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('efb6fbdcbbfe4ee1bac264bce9755f7f', 'frequency', 'Y', '年', '', null, '14', '', '1b85da1f28834a7a93965073cc77be3a', '1458610657', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('febf36595b794b87b39bd21edc4a109e', '24f0e71e9ed04cf2a424f161bf9c3250', '11', 'nsSMSView.QRY_KPI_LIST_COMP_FINA_IDX', '上市公司财务指标', null, '221', null, '284abd143ac0407eb29ac4d940254b54', '1462781547', null, null, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('frequency_01', 'frequency', 'D', '日', '', null, '2', '', null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('frequency_02', 'frequency', 'W', '周', '', null, '5', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('frequency_03', 'frequency', 'M', '月', '', null, '8', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('menu_safelevel1', 'menu_safelevel', '001', '安全级别1', '安全级别1', 'n', '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('menu_safelevel2', 'menu_safelevel', '002', '安全级别2', '安全级别2', 'n', '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout1', 'np_layout', '001', '两列-左宽右窄', '两列-左宽右窄', 'y', '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout2', 'np_layout', '002', '三列-边窄中宽', '三列-边窄中宽', 'y', '5', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout3', 'np_layout', '003', '两列-左窄右宽', '两列-左窄右宽', 'y', '3', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout4', 'np_layout', '004', '单列', '单列', 'y', '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout5', 'np_layout', '005', '两列-左右等分', '两列-左右等分', 'y', '4', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout6', 'np_layout', '006', '三列-左中右等分', '三列-左中右等分', 'y', '6', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('np_layout_tabs', 'np_layout', '007', '多页签布局', '多页签布局', 'y', '7', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('queryObject_01', 'queryObject', '002', '规模概况', null, null, '2', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('queryObject_02', 'queryObject', '001', '上市模块', null, null, '1', null, null, null, null, null, null);
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('628a262e3b8c40118754489536ff78a7', 'ac_res_type', 'JDBC', 'JDBC', null, null, null, null, '88b29d3313ce4f9389f40e22e8e5a0e3', '1471834097', null, null, '0');


-- 数据源管理模块依赖码表数据项  wqh 2017-8-31 15:42:40
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('2f6673a97d3a4fad96e7cff572cdf8c3', '9bc42aef881d4c81938eadcae57cb23d', 'BIType', '报表工具类型', '报表数据源', NULL, '21', '', '1b85da1f28834a7a93965073cc77be3a', '1503395353', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ed9a739b25b44c578861fc6ad08e0d04', '9bc42aef881d4c81938eadcae57cb23d', 'DBType', '数据库类型', '数据库类型', NULL, '22', '', '1b85da1f28834a7a93965073cc77be3a', '1503395372', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('a4a1c7fe04074d7ea79ba5a3f00e8fe9', '9bc42aef881d4c81938eadcae57cb23d', 'BigDataDBType', '大数据型数据库类型', '大数据型数据库类型', NULL, '294', '', 'ea7797faed8e4838a6b091a6dfde6e7f', '1510733173', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('2d94b88ef45849f0868519b18bb6057d', '9bc42aef881d4c81938eadcae57cb23d', 'smartbi', 'smartbi', '', NULL, '23', '2f6673a97d3a4fad96e7cff572cdf8c3', '1b85da1f28834a7a93965073cc77be3a', '1503476160', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('08c1230f8202482bbfed5df3b421312d', '9bc42aef881d4c81938eadcae57cb23d', 'runqian', 'RAQSoft（润乾）', '', NULL, '24', '2f6673a97d3a4fad96e7cff572cdf8c3', '1b85da1f28834a7a93965073cc77be3a', '1503476146', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('8da0cb20d80048c3b947e4c69ba851c9', '9bc42aef881d4c81938eadcae57cb23d', 'cognos', 'cognos', '', NULL, '25', '2f6673a97d3a4fad96e7cff572cdf8c3', '1b85da1f28834a7a93965073cc77be3a', '1503476132', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('361a6ebb9af940edbcdc6d0c7ffc14fe', '9bc42aef881d4c81938eadcae57cb23d', 'birt', 'birt', '', NULL, '26', '2f6673a97d3a4fad96e7cff572cdf8c3', '1b85da1f28834a7a93965073cc77be3a', '1503475821', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('61cfe73de60c4791ba1294e65c37204f', '9bc42aef881d4c81938eadcae57cb23d', 'MySQL', 'MySQL', 'Mysql官方名称及编码', NULL, '27', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1503395867', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ae702f84fd4e4ad0b628f694c5d87b2d', '9bc42aef881d4c81938eadcae57cb23d', 'Oracle', 'Oracle', 'orcale官方名称及编码', NULL, '28', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1503395898', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('b526f5cbad314c3c9ea35620042ed27e', '9bc42aef881d4c81938eadcae57cb23d', 'Microsoft SQL Server', 'Microsoft SQL Server', 'Microsoft SQL Server官方编码及名称', NULL, '29', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1504080989', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('ac7fadb4af2e4768add209963c46e36c', '9bc42aef881d4c81938eadcae57cb23d', 'DB2', 'DB2', 'DB2官方编码及名称', NULL, '30', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1504081024', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('1541e709308e4480a762abdd44d8f1ee', '9bc42aef881d4c81938eadcae57cb23d', 'Teradata', 'Teradata', 'Teradata官方编码及名称', NULL, '31', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1504081063', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('c13f62396f254db48387202820f7421e', '9bc42aef881d4c81938eadcae57cb23d', 'GreenPlum', 'GreenPlum', 'GreenPlum官方编码及名称', NULL, '32', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1504081088', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('1b3cfa39ebed4ac28b77744a281f5b23', '9bc42aef881d4c81938eadcae57cb23d', 'Netezza', 'Netezza', 'Netezza官方编码及名称', NULL, '33', 'ed9a739b25b44c578861fc6ad08e0d04', '1b85da1f28834a7a93965073cc77be3a', '1504081178', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('d12dff276cb741e596c7200f844ed186', '9bc42aef881d4c81938eadcae57cb23d', 'HuaweiMpp', 'HuaweiMpp', '华为Mpp大数据类型', NULL, '296', 'a4a1c7fe04074d7ea79ba5a3f00e8fe9', 'ea7797faed8e4838a6b091a6dfde6e7f', '1510733262', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('d8575bfe91e54e409bfb5a0103beea36', '9bc42aef881d4c81938eadcae57cb23d', 'XinghuanInceptor', 'XinghuanInceptor', '星环Inceptor大数据类型', NULL, '297', 'a4a1c7fe04074d7ea79ba5a3f00e8fe9', 'ea7797faed8e4838a6b091a6dfde6e7f', '1510733392', NULL, NULL, '0');
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('837340e8c1224d4db1cda5d7c8149486', '9bc42aef881d4c81938eadcae57cb23d', 'HuaweiHive', 'HuaweiHive', '华为Hive大数据类型', NULL, '295', 'a4a1c7fe04074d7ea79ba5a3f00e8fe9', 'ea7797faed8e4838a6b091a6dfde6e7f', '1510733227', NULL, NULL, '0');

-- 数据源分类默认的公共分类字段 lyf 2018-1-12 13:39:47
INSERT INTO `cp_defdoc` (`ID`, `TYPE_ID`, `DOC_CODE`, `DOC_NAME`, `DOC_DESC`, `IS_PRESET`, `SORTNUM`, `PK_FATHERID`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('1e08785651e14613bec80512e97e11a4', '07ed24e0a1f24dbeb59de3858c1f86d6', 'common', '通用数据源', '', NULL, '298', '', '1b85da1f28834a7a93965073cc77be3a', '1513736957', NULL, NULL, '0');

COMMIT;

-- ----------------------------
-- Records of cp_defdoclist
-- ----------------------------
BEGIN;
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('24f0e71e9ed04cf2a424f161bf9c3250', 'bp_indexanddim', '指标表', '指标库管理中表选择', 'Y', '21', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553715', '3', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ac_cognos_rpt_type', 'ac_rpt_type', '报表类型', '报表资源中cognos修改中报表类型', 'y', '9', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553558', '2', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ac_comp_type', 'ac_comp_type', '页面组件类型', '报表参数中显示类型', 'y', '8', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553530', '1', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ac_datasource_type', 'bi_type', '数据源类型', '系统配置', 'y', '7', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484719065', '2', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ac_data_type', 'ac_data_type', '数据类型', '参数管理中参数类型', 'y', '6', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553504', '1', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ac_param_type', 'ac_param_type', '报表参数类型', '报表资源中cognos资源修改时可以选择参数类型', 'y', '4', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553439', '2', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ac_res_type', 'ac_res_type', '资源类型', '组件Action控制类、数据源管理', 'y', '3', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553349', '1', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('edf59da2b8d84a56a93c895dd12d11a1', 'param_source', '参数数据来源类型', '报表参数中数据来源类型', null, '38', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484719015', '6', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('ef810bc74b92408e8e8c989013ade604', 'zt', '明细灵活查询专题', '用于明细灵活查询专题', null, '37', 'f7611d4e6fa84340bb2fdb6901e49de0', '1463547113', null, null, '0', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('frequency', 'frequency', '时间频度', '指标库管理', null, null, null, null, '1b85da1f28834a7a93965073cc77be3a', '1484553290', '6', null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('menu_safelevel', 'menu_safelevel', '安全级别', '应用在菜单管理中', 'y', '11', null, null, null, null, null, null);
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('np_layout', 'np_layout', '界面布局', '页面定制', 'y', '1', null, null, '1b85da1f28834a7a93965073cc77be3a', '1484554479', '1', null);

-- 数据源管理模块依赖码表数据项  wqh 2017-8-31 15:42:40
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('9bc42aef881d4c81938eadcae57cb23d', 'DatasourceType', '数据源类型', '数据来源类型，目前两大类：报表和数据库。', 'Y', '39', '1b85da1f28834a7a93965073cc77be3a', '1503395321', NULL, NULL, '0', NULL);
-- 数据源分类 lyf 2018-1-12 13:37:56
INSERT INTO `cp_defdoclist` (`ID`, `TYPE_CODE`, `TYPE_NAME`, `TYPE_DESC`, `IS_PRESET`, `SORTNUM`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`, `DEF_TYPE`) VALUES ('07ed24e0a1f24dbeb59de3858c1f86d6', 'DatasourceClassify', '数据源分类', '根据不同的分类，可以查询到的数据源也不一样，默认是public，其他分类需要开发工程中自定义拓展。', NULL, '41', NULL, NULL, '1b85da1f28834a7a93965073cc77be3a', '1513736998', '3', NULL);

COMMIT;

-- ----------------------------
-- Records of cp_datasource_library
-- ----------------------------
BEGIN;
INSERT INTO `cp_datasource_library` (`ID`, `DS_TYPE`, `DS_VERSION`, `QUALIFIEDCLASSNAME`, `JARPATH`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('05f9aeabe80242f68634b5b2a6b7838x', 'MySQL', '5.1.30', 'com.mysql.jdbc.Driver', '', null, null, null, null, null);
INSERT INTO `cp_datasource_library` (`ID`, `DS_TYPE`, `DS_VERSION`, `QUALIFIEDCLASSNAME`, `JARPATH`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('05f9aebbe80242f68634b5b2a6b7838a', 'Oracle', '11.2.0.3.0', 'oracle.jdbc.OracleDriver', null, null, null, null, null, null);
INSERT INTO `cp_datasource_library` (`ID`, `DS_TYPE`, `DS_VERSION`, `QUALIFIEDCLASSNAME`, `JARPATH`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('0018a57b16634ad3b3341b3056c43264', 'XinghuanInceptor', '5.0', 'org.apache.hive.jdbc.HiveDriver', '', '', NULL, '', NULL, NULL);
INSERT INTO `cp_datasource_library` (`ID`, `DS_TYPE`, `DS_VERSION`, `QUALIFIEDCLASSNAME`, `JARPATH`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('0018a57b16634ad3b3341b3056c43265', 'HuaweiHive', '2.0', 'org.apache.hive.jdbc.HiveDriver', '', '', NULL, '', NULL, NULL);
INSERT INTO `cp_datasource_library` (`ID`, `DS_TYPE`, `DS_VERSION`, `QUALIFIEDCLASSNAME`, `JARPATH`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`) VALUES ('0018a57b16634ad3b3341b3056c43266', 'HuaweiMpp', '2.0', 'org.postgresql.Driver', '', '', NULL, '', NULL, NULL);
COMMIT;

-- ----------------------------
-- Records of cp_favorites
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_log_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_log_op
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_loginlog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_power_dic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_role_datadic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of cp_sys_conf_ext
-- ----------------------------
BEGIN;
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext001', '密码时效', 'ext001', '0', '1', '密码安全策略', 'category001', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext002', '密码长度', 'ext002', '1', '1', '密码安全策略', 'category001', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext003', '密码是否含有数字', 'ext003', 'N', '4', '密码安全策略', 'category001', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext004', '密码是否含有特殊字符', 'ext004', 'N', '4', '密码安全策略', 'category001', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext005', '密码是否含有大写英文字母', 'ext005', 'N', '4', '密码安全策略', 'category001', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext006', '权限时效开关', 'ext006', 'Y', '4', '权限时效', 'category002', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext007', '权限时效提醒前置时间', 'ext007', '1', '1', '权限时效', 'category002', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext008', '密码是否含有英文字母', 'ext008', 'N', '4', '密码安全策略', 'category001', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext009', '权限时效默认值', 'ext009', '24', '1', '权限时效', 'category002', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext010', '邮件服务开关', 'ext010', 'Y', '4', '邮件服务配置', 'category003', null, null, null, null, null);
INSERT INTO `cp_sys_conf_ext` (`ID`, `CONF_NAME`, `CONF_CODE`, `CONF_VALUE`, `VALUE_TYPE`, `CATEGORY_NAME`, `CATEGORY_CODE`, `CREATEBY`, `CREATETIME`, `LASTMODIFYBY`, `LASTMODIFYTIME`, `VERSION`)  VALUES ('0190a7e6837147f3a10ad8confext011', '邮件服务器配置读取位置', 'ext011', '0', '1', '邮件服务配置', 'category003', null, null, null, null, null);
COMMIT;

-- ----------------------------
-- Records of pt_change_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of pt_datasource
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of pt_sqlds
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of pt_user_defaultpage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_cron_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`) VALUES ('clusterQuartzScheduler', '时效到期提醒', 'job', '0 0 1 * * ? *\n', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`) VALUES ('clusterQuartzScheduler', '时效过期删除', 'job', '0 0 1 * * ? *\n', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`) VALUES ('clusterQuartzScheduler', '流程超时处理', 'job', '0 0 1 * * ?', 'Asia/Shanghai');

COMMIT;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`) VALUES ('clusterQuartzScheduler', 'GDSP权限时效到期提醒', 'job', '根据GDSP系统权限时效配置的提醒前置提醒时间，对权限时效即将到期的用户发送消息通知（只提醒一次）。', 'com.gdsp.platform.notice.impl.GrantAgingWarningJob', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000F770800000010000000007800);
INSERT INTO `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`) VALUES ('clusterQuartzScheduler', 'GDSP过期权限时效删除', 'job', '删除权限时效已过期的用户与角色关联关系。', 'com.gdsp.platform.notice.impl.GrantAgingExpiredProcessJob', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000F770800000010000000007800);
INSERT INTO `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`) VALUES ('clusterQuartzScheduler', '流程节点超时处理', 'job', '流程设计时启动超时处理机制，当流程超时未处理，将由系统按照设计时选择的处理方式进行处理', 'com.gdsp.platform.workflow.impl.WorkFlowJob', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000F770800000010000000007800);
COMMIT;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_locks` (`SCHED_NAME`, `LOCK_NAME`) VALUES ('clusterQuartzScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` (`SCHED_NAME`, `LOCK_NAME`) VALUES ('clusterQuartzScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_scheduler_state` (`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`) VALUES ('clusterQuartzScheduler', 'USER-20160727ZL1484877376195', '1484878053404', '15000');
COMMIT;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`, `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`) VALUES ('clusterQuartzScheduler', '时效到期提醒', 'job', 'GDSP权限时效到期提醒', 'job', '根据时效和提醒前置时间发送提醒给用户', '1486659632000', '1486609897211', '5', 'WAITING', 'CRON', '1485882032000', '1491012032000', null, '0', '');
INSERT INTO `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`, `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`) VALUES ('clusterQuartzScheduler', '时效过期删除', 'job', 'GDSP过期权限时效删除', 'job', '删除过期的用户与角色关联关系', '1486659658000', '1486609897229', '5', 'WAITING', 'CRON', '1485896758000', '1495073158000', null, '0', '');
COMMIT;

-- ----------------------------
-- Records of res_accesslog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_help
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_notice_range
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_orgs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_power_butn
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_power_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_power_org
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_power_page
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_sys_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_sys_notice_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_user
-- ----------------------------
BEGIN;
INSERT INTO `rms_user` (`ID`, `ACCOUNT`, `USERNAME`, `USERTYPE`, `ONLYADMIN`, `USER_PASSWORD`, `MOBILE`, `TEL`, `EMAIL`, `SEX`, `PK_ORG`, `ISRESET`, `ISLOCKED`, `ABLETIME`, `DISABLETIME`, `MEMO`, `CREATEBY`, `CREATETIME`, `LASTMODIFYTIME`, `LASTMODIFYBY`, `VERSION`, `ORIGIN`, `update_pwd_time`, `isDisabled`) VALUES ('1b85da1f28834a7a93965073cc77be3a', 'admin', '系统管理员', '0', 'N', '3a0a7f07077035a376f855c12b728d60', '', null, null, '0', 'global00000000000000000000000001', null, 'N', null, null, '系统管理员\r\n', null, null, null, null, '0', '0', null, 'N');
COMMIT;

-- ----------------------------
-- Records of rms_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_usergroup
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of rms_usergroup_rlt
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of sched_accesslog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of st_butnregister
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of st_conf
-- ----------------------------
BEGIN;
INSERT INTO `st_conf` (`ID`, `SYSHOMESTATE`, `SYSHOMEURL`, `REPORTINTS`, `CREATEBY`, `CREATETIME`) VALUES ('bb5a99159e56467e9eae3c6660fb7d61', 'Y', 'homepage.d', 'cognos,birt,', '88b29d3313ce4f9389f40e22e8e5a0e3', '1472714193');
COMMIT;

-- ----------------------------
-- Records of st_funcdec
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of st_menuregister
-- ----------------------------
BEGIN;
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('0b0529c528d14e86823205f9c6c398c0', '系统工具', 'f0104', 'f0104', '97bbe8a273444a63a1e59d5be48fdee7', '11300050', 0, 'Y', '', NULL, NULL, 'Y', '', '213db66d3927404fa831b4082b073d4b', 1480577230, NULL, NULL, NULL, NULL, NULL, 'Y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('0b32a23c87a34304904c7734e6c6069c', '指标库分组', 'f010406', 'f010406', '0b0529c528d14e86823205f9c6c398c0', '1130005000o0', 2, 'Y', 'indexanddim/indexgroup/list.d', NULL, NULL, 'Y', '指标分组管理', '1b85da1f28834a7a93965073cc77be3a', 1455778006, 1483516654, '1b85da1f28834a7a93965073cc77be3a', 13, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('0d9a64ee34964c05b31c9b9951897cfa', '待办事务', 'E010101', 'E010101', '13327046923d4371b561a805094f512b', '11f000200000', 2, 'Y', 'workflow/task/myList.d', NULL, NULL, 'Y', '待办事务', 'e9f7f34d5c534776bd16f039fc662127', 1444361515, 1483518222, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'Y', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('13327046923d4371b561a805094f512b', '事务处理', 'E0101', 'E0101', '1e4f8cb2c0cc4fd2b5da013e331fdcbe', '11f00020', 0, 'Y', '', NULL, NULL, 'Y', '工作流-员工办公模块', 'e9f7f34d5c534776bd16f039fc662127', 1444361322, 1483518185, '1b85da1f28834a7a93965073cc77be3a', 8, NULL, 'menu_safelevel1', 'Y', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('14843da7ed06409883177f0638e77ae1', '系统码表', 'f010405', 'f010405', '65f6706b12d94018b12e6c050990ee36', '113000100060', 2, 'y', 'conf/defdoclist/list.d', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1440058995, 1525245643, '1b85da1f28834a7a93965073cc77be3a', 16, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('16028f20ee8c4bc1850a2fa2b10332be', '数据源管理', 'f010403', 'f010403', '0b0529c528d14e86823205f9c6c398c0', '1130005000a0', 2, 'y', 'systools/ds/dslist.d', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1440142073, 1483516599, '1b85da1f28834a7a93965073cc77be3a', 11, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('164886232c144b438eb4ba75153a955e', '访问日志', 'f010203', 'f010203', '76aeba9f264344e1ab22c24f47b329a1', '113000b00040', 2, 'y', 'systools/log/list.d', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1443490102, 1483518545, '1b85da1f28834a7a93965073cc77be3a', 10, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('1abe62218531460286fbe4097479e995', '数据授权', 'f010107', 'f010107', '577aee16550447ad8c0c0c4860b9000e', '113000000060', 2, 'Y', 'power/datalicense/list.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480330955, NULL, NULL, 0, NULL, 'menu_safelevel1', NULL, NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('1c412ed0c6f947658f2ac3169186a4c0', '维度管理', 'B104030', 'B104030', 'aae9c1aee32d41bda78682263768d9ae', '11g000e00020', 3, 'Y', 'indexanddim/dim/list.d', NULL, NULL, 'N', '维度管理', '1b85da1f28834a7a93965073cc77be3a', 1483516339, 1484556455, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, 'menu_safelevel1', NULL, NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('1c9d3b928a14432f8d8bd57966240dcf', '任务日志', 'f010606', 'f010606', '9bab52c4cf924c58b389d69f914c53e2', '1130006000050', 2, 'y', 'schedule/joblog/list.d?schedType=job', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1445503574, 1480329897, '1b85da1f28834a7a93965073cc77be3a', 8, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('1e4f8cb2c0cc4fd2b5da013e331fdcbe', '员工办公', 'E01', 'E01', '', '11f0', 0, 'y', '', NULL, NULL, 'Y', '员工办公', 'e9f7f34d5c534776bd16f039fc662127', 1444360170, 1483518060, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, '', 'y', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('1f8a27b3c38c4fe7bbe9210bc2bd61e0', '成果管理', 'B3040', 'B3040', 'e366525453044f59b9922e3bfc8038a2', '113000c00000030', 3, 'Y', 'achievement/manager/list.d', NULL, NULL, 'N', '成果管理', '1b85da1f28834a7a93965073cc77be3a', 1480330581, 1483518020, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('208d591f1e834b3983d232ac3b4f2276', '预警日志', 'F010603', 'F010603', '9bab52c4cf924c58b389d69f914c53e2', '1130006000020', 2, 'Y', 'schedule/joblog/list.d?schedType=alert', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1445564842, 1480329819, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('29553df2566a4e0bb4b86ffcb2a83db4', '流程定义', 'F010704', 'F010704', '457e8070dcc74fb8a1e62cf937f48ff9', '113000700010', 2, 'Y', 'workflow/process/list.d', NULL, NULL, 'Y', '流程定义', 'e9f7f34d5c534776bd16f039fc662127', 1444361026, 1480330003, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, 'menu_safelevel1', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('2c76495499c54bdead1af5c6d92fc349', '流程历史', 'F010708', 'F010708', '457e8070dcc74fb8a1e62cf937f48ff9', '113000700030', 2, 'Y', 'workflow/history/list.d', NULL, NULL, 'Y', '流程历史', 'e9f7f34d5c534776bd16f039fc662127', 1444361161, 1480330033, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('2f581cb06cfe48a385ffedb1e1ddb2f2', '明细灵活查询', 'B2020', 'B2020', '76c6606ca7774f829fef9f6c22f59a52', '11h00010', 0, 'Y', '', NULL, NULL, 'N', '明细灵活查询', '1b85da1f28834a7a93965073cc77be3a', 1483517518, 1483517535, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('30523d5afdc34687b6b8b18826ccdf56', '委托管理', 'E010103', 'E010103', '13327046923d4371b561a805094f512b', '11f000200010', 2, 'Y', 'workflow/delegate/list.d', NULL, NULL, 'Y', '委托管理', 'e9f7f34d5c534776bd16f039fc662127', 1444361547, 1483518257, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'Y', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('30b6fe72d0db4af5847481ecb7afb3c5', '操作日志', 'f010204', 'f010204', '76aeba9f264344e1ab22c24f47b329a1', '113000b00030', 2, 'Y', 'systools/operationlog/list.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480328823, 1487121183, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('3243a241a36f4dca88975318d1fbb103', '指标维度关联关系', 'B104040', 'B104040', 'aae9c1aee32d41bda78682263768d9ae', '11g000e00030', 3, 'Y', 'indexanddim/idxdimrel/list.d', NULL, NULL, 'N', '指标维度关联关系', '1b85da1f28834a7a93965073cc77be3a', 1483516363, 1484556475, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, 'menu_safelevel1', NULL, NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('32fc753cd40744f1acbf14fdb389e1f4', '参数设置', 'f010301', 'f010301', '65f6706b12d94018b12e6c050990ee36', '113000100040', 2, 'y', 'conf/param/list.d', NULL, NULL, 'N', '', 'e9f7f34d5c534776bd16f039fc662127', 1441008054, 1483516493, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('3488d4eaa4bb4235a5cbd4f7f5f648c8', '系统公告', 'f010401', 'f010401', '0b0529c528d14e86823205f9c6c398c0', '113000500040', 2, 'y', 'sysnotice/list.d', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1440060036, 1483516571, '1b85da1f28834a7a93965073cc77be3a', 6, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('457e8070dcc74fb8a1e62cf937f48ff9', '流程管理', 'f0107', 'f0107', '97bbe8a273444a63a1e59d5be48fdee7', '11300070', 0, 'Y', '', NULL, NULL, 'Y', '流程管理', 'e9f7f34d5c534776bd16f039fc662127', 1444360985, 1480385831, '1b85da1f28834a7a93965073cc77be3a', 6, NULL, 'menu_safelevel1', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('46407b474a5a4e0ab58252ef2d60e999', '流程监控', 'F010706', 'F010706', '457e8070dcc74fb8a1e62cf937f48ff9', '113000700020', 2, 'Y', 'workflow/monitor/list.d', NULL, NULL, 'Y', '流程监控', 'e9f7f34d5c534776bd16f039fc662127', 1444361225, 1480330017, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, 'menu_safelevel1', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('479303f451e0453c9218e5dbd06ff959', '任务类型管理', 'f010604', 'f010604', '9bab52c4cf924c58b389d69f914c53e2', '1130006000030', 2, 'y', 'schedule/jobdef/jobDefList.d', NULL, NULL, 'Y', '', '57da3839f9584e58942c35a6ad90c5d5', 1440124474, 1480329851, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('4c8fc3245ec44b17967caab7c98b9369', '成果查看', 'B3010', 'B3010', 'e366525453044f59b9922e3bfc8038a2', '113000c000000000', 3, 'Y', 'achievement/view/view-list.d', NULL, NULL, 'N', '成果查看', '1b85da1f28834a7a93965073cc77be3a', 1480330438, 1483517652, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('53fea2147a454416b3efb501a1985284', '成果收藏', 'B3030', 'B3030', 'e366525453044f59b9922e3bfc8038a2', '113000c00000020', 3, 'Y', 'achievement/collect/list.d', NULL, NULL, 'N', '成果收藏', '1b85da1f28834a7a93965073cc77be3a', 1480330548, 1483518007, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('54c0118b577c4181b821fe7a2be5dc69', '报表参数', 'f010503', 'f010503', 'b9ef110427fe44ec823db7d75faf40d5', '1130003000n0', 2, 'Y', 'param/paramLibrary/main.d', NULL, NULL, 'N', '', '88b29d3313ce4f9389f40e22e8e5a0e3', 1471504117, 1483516699, '1b85da1f28834a7a93965073cc77be3a', 8, NULL, 'menu_safelevel1', NULL, NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('57297a38a01e443586e8956209ae717b', '用户管理', 'f010102', 'f010102', '577aee16550447ad8c0c0c4860b9000e', '113000000000', 2, 'y', 'grant/user/list.d', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1438241671, 1487121053, '1b85da1f28834a7a93965073cc77be3a', 10, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('577aee16550447ad8c0c0c4860b9000e', '权限管理', 'f0101', 'f0101', '97bbe8a273444a63a1e59d5be48fdee7', '11300000', 0, 'y', NULL, NULL, NULL, 'Y', NULL, 'bab3dcaa4dc24b169a860fa6ca344944', 1438158211, 1449479835, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, NULL, 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('61f121af6a7b4ca885228a339e392215', '功能说明配置', 'f010303', 'f010303', '0b0529c528d14e86823205f9c6c398c0', '1130005000p0', 2, 'Y', 'portal/functionDec/adminList.d', NULL, NULL, 'Y', '功能说明描述  图文并茂', '1b85da1f28834a7a93965073cc77be3a', 1480386868, 1525245453, '1b85da1f28834a7a93965073cc77be3a', 6, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('65f6706b12d94018b12e6c050990ee36', '系统配置', 'f0103', 'f0103', '97bbe8a273444a63a1e59d5be48fdee7', '11300010', 0, 'y', '/', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439778237, 1525245337, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, '', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('6623d9bc448d494b9d60a6902478d62d', '我的成果', 'B3020', 'B3020', 'e366525453044f59b9922e3bfc8038a2', '113000c00000010', 3, 'Y', 'achievement/personal/list.d', NULL, NULL, 'N', '我的成果', '1b85da1f28834a7a93965073cc77be3a', 1480330502, 1483517726, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('681aee9157c84b64b2dc60d25f7c1b7d', '任务监控', 'f010605', 'f010605', '9bab52c4cf924c58b389d69f914c53e2', '1130006000040', 2, 'y', 'schedule/job/jobList.d', NULL, NULL, 'Y', '', '57da3839f9584e58942c35a6ad90c5d5', 1440576501, 1480329875, '1b85da1f28834a7a93965073cc77be3a', 6, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('6ee7db3313914fb0b9e85a49b6a57816', '功能设置', 'f010304', 'f010304', '65f6706b12d94018b12e6c050990ee36', '113000100050', 2, 'Y', 'func/systemconf/list.d', NULL, NULL, 'Y', '用于系统名称、首页、菜单样式、系统启用的BI工具的配置。', '1b85da1f28834a7a93965073cc77be3a', 1472096658, 1525245380, '1b85da1f28834a7a93965073cc77be3a', 6, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('76aeba9f264344e1ab22c24f47b329a1', '审计日志', 'f0102', 'f0102', '97bbe8a273444a63a1e59d5be48fdee7', '113000b0', 0, 'Y', '/', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480328659, 1525245226, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, '', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('76c6606ca7774f829fef9f6c22f59a52', '灵活查询', 'B20', 'B20', '', '11h0', 0, 'Y', '', NULL, NULL, 'N', '灵活查询', '1b85da1f28834a7a93965073cc77be3a', 1483517467, NULL, NULL, 0, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('77719992a9a04b15acf0f2431588f8fc', '单据注册', 'F010702', 'F010702', '457e8070dcc74fb8a1e62cf937f48ff9', '113000700000', 2, 'Y', 'workflow/formdef/list.d', NULL, NULL, 'Y', '单据注册', '88b29d3313ce4f9389f40e22e8e5a0e3', 1473061515, 1480329985, '1b85da1f28834a7a93965073cc77be3a', 8, NULL, 'menu_safelevel1', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('781bc14db6e24272a99d6ccbdaaabdbf', '基础资源', 'f010504', 'f010504', '0b0529c528d14e86823205f9c6c398c0', '1130005000q0', 2, 'y', 'appcfg/resourceManage/list.d', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1439952761, 1525245521, '1b85da1f28834a7a93965073cc77be3a', 12, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('7c78f25038e54d5fb194c2bca8e631ce', '首页授权', 'f010105', 'f010105', '577aee16550447ad8c0c0c4860b9000e', '113000000040', 2, 'y', 'grant/page/mainList.d', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439778159, 1525245189, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('7d6064e07f3643aa83570f94bac4c2eb', '页面定制', 'f0100506', 'f010506', 'b9ef110427fe44ec823db7d75faf40d5', '1130003000r0', 2, 'y', 'appcfg/pagecfg/list.d', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1439953547, 1525246301, '1b85da1f28834a7a93965073cc77be3a', 18, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('86eb5110050849c3a59267f1777093e2', '指标库管理', 'f010407', 'f010407', '0b0529c528d14e86823205f9c6c398c0', '1130005000n0', 2, 'Y', 'index/indexmanager/list.d', NULL, NULL, 'Y', '指标管理', '1b85da1f28834a7a93965073cc77be3a', 1455777921, 1483518670, '1b85da1f28834a7a93965073cc77be3a', 10, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('91341e6010ef4647901550a73c225218', '报表资源', 'f010505', 'f010505', 'b9ef110427fe44ec823db7d75faf40d5', '1130003000m0', 2, 'Y', 'framework/reportentry/list.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1470810709, 1483518728, '1b85da1f28834a7a93965073cc77be3a', 10, NULL, 'menu_safelevel1', NULL, NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('95247272657c4894a275c9412fccbb81', '菜单管理', 'f010302', 'f010302', '65f6706b12d94018b12e6c050990ee36', '113000100000', 3, 'y', 'func/menu/list.d', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439778293, 1483516511, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('97bbe8a273444a63a1e59d5be48fdee7', '系统管理', 'F01', 'F01', '', '1130', 0, 'y', '', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439777646, 1483518336, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, '', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('9bab52c4cf924c58b389d69f914c53e2', '预警及任务', 'f0106', 'f0106', '97bbe8a273444a63a1e59d5be48fdee7', '11300060', 0, 'y', '', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1446602836, 1480329740, '1b85da1f28834a7a93965073cc77be3a', 7, NULL, '', 'n', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('a081d894886e4888ba31337a06a8e7f6', '数据字典', 'f010402', 'f010402', '0b0529c528d14e86823205f9c6c398c0', '1130005000l0', 2, 'Y', 'conf/datadic/list.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480329362, 1525245673, '1b85da1f28834a7a93965073cc77be3a', 10, NULL, 'menu_safelevel1', 'Y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('aae9c1aee32d41bda78682263768d9ae', '指标维度管理', 'B1040', 'B1040', 'c4bd518ae437476bad64c4adb02e07d1', '11g000e0', 0, 'Y', '', NULL, NULL, 'N', '指标维度管理', '1b85da1f28834a7a93965073cc77be3a', 1483516256, NULL, NULL, 0, NULL, 'menu_safelevel1', NULL, NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('b9ef110427fe44ec823db7d75faf40d5', '报表集成', 'f0105', 'f0105', '97bbe8a273444a63a1e59d5be48fdee7', '11300030', 0, 'y', '', NULL, NULL, 'Y', '', 'e9f7f34d5c534776bd16f039fc662127', 1439952038, 1480329500, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, '', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('c4bd518ae437476bad64c4adb02e07d1', '业务功能', 'B10', 'B10', '', '11g0', 0, 'y', '', NULL, NULL, 'Y', '业务功能', '1b85da1f28834a7a93965073cc77be3a', 1449644566, 1483516210, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, '', 'n', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('c8372c11b3f941ee8442e5aa815339f5', '机构管理', 'f010101', 'f010101', '577aee16550447ad8c0c0c4860b9000e', '113000000010', 2, 'y', 'grant/org/list.d', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439777834, 1487120904, '1b85da1f28834a7a93965073cc77be3a', 3, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('cd0c2c1a061b458e8c74e3974cea38ba', '常用工具', 'E0102', 'E0102', '1e4f8cb2c0cc4fd2b5da013e331fdcbe', '11f00030', 0, 'y', '', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1449480887, 1483518198, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, '', 'n', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('d046e1d58d494d8bbe7abcd3d927340d', '权限控制维度', 'f010106', 'f010106', '577aee16550447ad8c0c0c4860b9000e', '113000000050', 2, 'Y', 'datalicense/powerdic/list.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480330926, 1480331597, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, 'menu_safelevel1', '', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('d28635c04aec41afb4f3ebac8bcd9197', '功能说明', 'E010202', 'E010202', 'cd0c2c1a061b458e8c74e3974cea38ba', '11f000300010', 2, 'Y', 'portal/functionDec/list.d', NULL, NULL, 'Y', '', '49eeae04b79c4f1cb82bd903397a9467', 1483013853, 1483518279, '1b85da1f28834a7a93965073cc77be3a', 4, NULL, 'menu_safelevel1', NULL, NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('d3b0b8796b7a433eb8a7bc5cfe346a90', '报表插件', 'F010408', 'F010408', 'b9ef110427fe44ec823db7d75faf40d5', '1130003000q0', 2, 'Y', 'birt/birtplugin/prompt.d', NULL, NULL, 'N', '', '7bd4c8ce19d74694b80cf00bfe7f3872', 1481619448, 1483516808, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, 'menu_safelevel1', NULL, NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('d7a6910870794b5c8f92f949c8c01bb8', '权限查询', 'f010108', 'f010108', '577aee16550447ad8c0c0c4860b9000e', '113000000070', 2, 'Y', 'systools/authoritylog/urlist.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480328723, 1525245302, '1b85da1f28834a7a93965073cc77be3a', 8, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('da064b15a3d8492fafc7249d358abc11', '登录日志', 'f010202', 'f010202', '76aeba9f264344e1ab22c24f47b329a1', '113000b00010', 2, 'Y', 'systools/loginlog/list.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1480328763, 1481014325, '1b85da1f28834a7a93965073cc77be3a', 2, NULL, 'menu_safelevel1', 'y', NULL, 'Y', '', NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('db91036f9d7446c8b4695a5662be71ae', '我的流程', 'E010102', 'E010102', '13327046923d4371b561a805094f512b', '11f000200020', 2, 'Y', 'workflow/task/listAll.d', NULL, NULL, 'Y', '我的流程', 'e9f7f34d5c534776bd16f039fc662127', 1444361577, 1483518240, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'Y', NULL, 'N', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('e10c686bb5b34ae3ab20e9b755e59d76', '预警管理', 'F010602', 'F010602', '9bab52c4cf924c58b389d69f914c53e2', '1130006000010', 2, 'Y', 'schedule/alert/alertList.d', NULL, NULL, 'Y', '', '1b85da1f28834a7a93965073cc77be3a', 1443509963, 1480329803, '1b85da1f28834a7a93965073cc77be3a', 5, NULL, 'menu_safelevel1', 'N', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('e1b4e04b33eb4b268690434b296f49c1', '角色管理', 'f010104', 'f010104', '577aee16550447ad8c0c0c4860b9000e', '113000000030', 2, 'y', 'grant/role/list.d', NULL, NULL, 'Y', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439778096, 1487121002, '1b85da1f28834a7a93965073cc77be3a', 3, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('e2c2e57cd5fc46f59de6510e1af2b352', '汇总灵活查询', 'B2030', 'B2030', '76c6606ca7774f829fef9f6c22f59a52', '11h00020', 3, 'Y', '', NULL, NULL, 'N', '汇总灵活查询', '1b85da1f28834a7a93965073cc77be3a', 1483517594, NULL, NULL, 0, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('e366525453044f59b9922e3bfc8038a2', '成果共享', 'B30', 'B30', '97bbe8a273444a63a1e59d5be48fdee7', '113000c0', 0, 'Y', '', NULL, NULL, 'N', '成果共享', '1b85da1f28834a7a93965073cc77be3a', 1480330365, 1483608657, '1b85da1f28834a7a93965073cc77be3a', 10, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('e6fff2be6ce94b69963928720d4865bf', '部门首页', '090808', '1', '', '1160', 4, 'y', '', NULL, NULL, 'Y', '部门首页', 'bab3dcaa4dc24b169a860fa6ca344944', 1438241827, 1439976375, 'e9f7f34d5c534776bd16f039fc662127', 1, NULL, NULL, 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('ec3950a5fce64a28b8449261cd29581b', '明细灵活查询方案', 'B2010', 'B2010', '76c6606ca7774f829fef9f6c22f59a52', '11h00000', 3, 'Y', '', NULL, NULL, 'N', '明细灵活查询方案', '1b85da1f28834a7a93965073cc77be3a', 1483517496, NULL, NULL, 0, NULL, 'menu_safelevel1', NULL, NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('f7336347132240d699aea06442fb7bd4', '用户组管理', 'f010103', 'f010103', '577aee16550447ad8c0c0c4860b9000e', '113000000020', 2, 'y', 'grant/usergroup/list.d', NULL, NULL, 'N', '', 'bab3dcaa4dc24b169a860fa6ca344944', 1439777967, 1487120993, '1b85da1f28834a7a93965073cc77be3a', 3, NULL, 'menu_safelevel1', 'y', NULL, 'Y', NULL, NULL);
INSERT INTO st_menuregister (ID, FUNNAME, FUNCODE, DISPCODE, PARENTID, INNERCODE, FUNTYPE, ISPOWER, URL, HELPNAME, HINTINF, ISENABLE, MEMO, CREATEBY, CREATETIME, LASTMODIFYTIME, LASTMODIFYBY, VERSION, MENUID, SAFELEVEL, IS_SYSTEM_MENU, PAGEID, ISROOTMENU, ISREPORT, ICONFIELD) VALUES ('f8253aaec331456088eb53aac4181372', '预警类型管理', 'F010601', 'F010601', '9bab52c4cf924c58b389d69f914c53e2', '1130006000000', 2, 'Y', 'schedule/alertdef/alertDefList.d', NULL, NULL, 'Y', '', '57da3839f9584e58942c35a6ad90c5d5', 1442822302, 1480329778, '1b85da1f28834a7a93965073cc77be3a', 6, NULL, 'menu_safelevel1', 'N', NULL, 'Y', NULL, NULL);
COMMIT;

-- ----------------------------
-- Records of st_pageregister
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of st_par_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of st_param
-- ----------------------------
BEGIN;
INSERT INTO `st_param` (`ID`, `PARNAME`, `PARCODE`, `PARGROUPID`, `VALUETYPE`, `VALUERANGE`, `DEFAULTVALUE`, `PARVALUE`, `MEMO`, `CREATETIME`, `CREATEBY`, `LASTMODIFYTIME`, `LASTMODIFYBY`) VALUES ('0190a7e6837147f3a10ad8a392rms001', '是否限制管理员权限', 'RMS001', '0190a7e6837147f3a10ad8a392719621', '2', '', 'false', 'false', '是否限制管理员权限，如果是，在用户上标示\"只有管理权限\"', null, '', null, '');
INSERT INTO `st_param` (`ID`, `PARNAME`, `PARCODE`, `PARGROUPID`, `VALUETYPE`, `VALUERANGE`, `DEFAULTVALUE`, `PARVALUE`, `MEMO`, `CREATETIME`, `CREATEBY`, `LASTMODIFYTIME`, `LASTMODIFYBY`) VALUES ('0190a7e6837147f3a10ad8a392rms002', '是否显示安全级别', 'RMS002', '0190a7e6837147f3a10ad8a392719621', '2', '', 'false', 'false', '是否显示安全级别，如果是则授权菜单后追加安全级别', null, '', null, '');
COMMIT;

-- ----------------------------
-- Records of st_passset
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of st_resregister
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of tmp_cp_log_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of tmp_cp_log_op
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of tools_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Records of flow_eventtype
-- ----------------------------
BEGIN;
INSERT INTO `flow_eventtype`(`ID`, `EVENTTYPECODE`, `EVENTTYPENAME`, `EVENTINTERFACE`, `CREATETIME`,`CREATEBY`, `LASTMODIFYTIME`, `LASTMODIFYBY`,`VERSION`) VALUES ('2c93109422ef78060122ef7a0fa30001', 'NodifyTaskListener', '用户任务扩展事件', 'com.gdsp.platform.workflow.helper.listener.NodifyTaskListener', null, null, null, null, null);
COMMIT;

-- ----------------------------
-- Records of mail_registry
-- ----------------------------
BEGIN;
INSERT INTO `cp_mail_registry` VALUES ('bb5a99159e56467e9eae3c6660fb7d63', '123', 'smtp.163.com', '25', 'gdsp_portal@163.com', 'gdsp_portal@163.com', 'mail123456', '1', NULL, NULL, NULL, NULL, NULL);
COMMIT;