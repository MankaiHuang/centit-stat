/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.134.7
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.134.7:3306
 Source Schema         : stat

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 11/12/2019 10:12:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for q_chart_model
-- ----------------------------
DROP TABLE IF EXISTS `q_chart_model`;
CREATE TABLE `q_chart_model`  (
  `chart_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PACKET_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `APPLICATION_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Data_Set_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `chart_Name_Format` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `chart_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHART_CATALOG` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'C chart F form R report  default:C',
  `chart_design_json` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json格式的图表自定义说明',
  `REPORT_DOC_FILEID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Recorder` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Record_Date` datetime(0) NULL DEFAULT NULL,
  `has_data_opt` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data_opt_desc_json` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`chart_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_chart_resource_column
-- ----------------------------
DROP TABLE IF EXISTS `q_chart_resource_column`;
CREATE TABLE `q_chart_resource_column`  (
  `chart_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `column_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `show_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`chart_ID`, `column_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_data_packet
-- ----------------------------
DROP TABLE IF EXISTS `q_data_packet`;
CREATE TABLE `q_data_packet`  (
  `PACKET_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `APPLICATION_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Owner_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Owner_Code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PACKET_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PACKET_TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PACKET_DESC` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Recorder` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Record_Date` datetime(0) NULL DEFAULT NULL,
  `has_data_opt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data_opt_desc_json` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `BUFFER_FRESH_PERIOD` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`PACKET_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_data_packet_param
-- ----------------------------
DROP TABLE IF EXISTS `q_data_packet_param`;
CREATE TABLE `q_data_packet_param`  (
  `PACKET_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PARAM_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PARAM_Label` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PARAM_Display_Style` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param_Type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param_Reference_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param_Reference_Data` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param_Validate_Regex` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param_Validate_Info` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param_Default_Value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PARAM_Order` decimal(3, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`PARAM_Name`, `PACKET_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_data_resource
-- ----------------------------
DROP TABLE IF EXISTS `q_data_resource`;
CREATE TABLE `q_data_resource`  (
  `RESOURCE_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DATABASE_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OWNER_TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构或者个人',
  `OWNER_CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_SQL` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_DESC` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RESOURCE_NAME_FORMAT` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '根据参数转换',
  `RECORDER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RECORD_DATE` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`RESOURCE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_data_resource_column
-- ----------------------------
DROP TABLE IF EXISTS `q_data_resource_column`;
CREATE TABLE `q_data_resource_column`  (
  `RESOURCE_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `COLUMN_CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `COLUMN_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_STAT_DATA` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATA_TYPE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CATALOG_CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`RESOURCE_ID`, `COLUMN_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_data_resource_param
-- ----------------------------
DROP TABLE IF EXISTS `q_data_resource_param`;
CREATE TABLE `q_data_resource_param`  (
  `RESOURCE_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PARAM_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PARAM_LABEL` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PARAM_DISPLAY_STYLE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'N:普通 normal H 隐藏 hide R 只读 readonly',
  `PARAM_TYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'S:文本 N数字  D：日期 T：时间戳（datetime)  ',
  `PARAM_REFERENCE_TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份',
  `PARAM_REFERENCE_DATA` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '根据paramReferenceType类型（1,2,3）填写对应值',
  `PARAM_VALIDATE_REGEX` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'regex表达式',
  `PARAM_VALIDATE_INFO` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '约束不通过提示信息',
  `PARAM_DEFAULT_VALUE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数默认值',
  `PARAM_ORDER` decimal(2, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`RESOURCE_ID`, `PARAM_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_dataset_columndesc
-- ----------------------------
DROP TABLE IF EXISTS `q_dataset_columndesc`;
CREATE TABLE `q_dataset_columndesc`  (
  `PACKET_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `column_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `QUERY_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `column_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_stat_data` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data_Type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `catalog_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`column_code`, `PACKET_ID`, `QUERY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_dataset_define
-- ----------------------------
DROP TABLE IF EXISTS `q_dataset_define`;
CREATE TABLE `q_dataset_define`  (
  `QUERY_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PACKET_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Database_Code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_SQL` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Query_DESC` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Recorder` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Record_Date` datetime(0) NULL DEFAULT NULL,
  `SET_TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'D',
  PRIMARY KEY (`QUERY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_form_column
-- ----------------------------
DROP TABLE IF EXISTS `q_form_column`;
CREATE TABLE `q_form_column`  (
  `form_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `column_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `opt_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0 无操作 1 合计 2 平均 3 平均合计',
  `column_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'R 行头 、 C 列头 、 D 数据',
  `mine_url_Format` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板可以引用行数据和 数据包查询参数',
  `column_order` decimal(4, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`form_ID`, `column_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_form_model
-- ----------------------------
DROP TABLE IF EXISTS `q_form_model`;
CREATE TABLE `q_form_model`  (
  `form_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PACKET_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_ID` int(11) NULL DEFAULT NULL,
  `Data_Set_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `from_Name_Format` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `form_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '2 二维表 3 同比分析 4 环比分析 5 交叉制表',
  `Recorder` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Record_DATE` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`form_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_form_param
-- ----------------------------
DROP TABLE IF EXISTS `q_form_param`;
CREATE TABLE `q_form_param`  (
  `form_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PARAM_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `compare_TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1 同比 2 环比',
  `param_Default_Value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数默认值',
  PRIMARY KEY (`form_ID`, `PARAM_Name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表参数 和 数据包一样，这里只是自定那些是 用于 同比 和 环比 的时间参数' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_query_column
-- ----------------------------
DROP TABLE IF EXISTS `q_query_column`;
CREATE TABLE `q_query_column`  (
  `Model_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `col_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `show_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'R 行头  C 列头  D 数值',
  `opt_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0 : 不做 1 求和 2 求平均值 3 求和 求平均值',
  `draw_Chart` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ' T 画统计图 F 不画',
  `col_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `col_Logic` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COL_ORDER` decimal(2, 0) NULL DEFAULT NULL,
  `IS_SHOW` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COL_FORMAT` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LINK_TYPE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DEFAULT_VALUE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `catalog_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `css_style` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Model_Name`, `col_Name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_query_condition
-- ----------------------------
DROP TABLE IF EXISTS `q_query_condition`;
CREATE TABLE `q_query_condition`  (
  `Model_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cond_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cond_Label` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cond_Display_Style` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'N:普通 nomal H 隐藏 hide R 只读 readonly',
  `param_Type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'S:文本 N数字  D：日期 T：时间戳（datetime)  ',
  `compare_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '必需是时间字段， 3 同比分析  4 环比分析 0 其他',
  `param_Reference_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份',
  `param_Reference_Data` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '根据paramReferenceType类型（1,2,3）填写对应值',
  `param_Validate_Regex` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'regex表达式',
  `param_Validate_Info` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '约束不通过提示信息',
  `param_Default_Value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数默认值',
  `cond_Order` decimal(2, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`Model_Name`, `cond_Name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_query_model
-- ----------------------------
DROP TABLE IF EXISTS `q_query_model`;
CREATE TABLE `q_query_model`  (
  `Model_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Database_Code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Model_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表',
  `Owner_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构或者个人',
  `Owner_Code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_SQL` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Query_DESC` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `form_Name_Format` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `row_Draw_Chart` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否按行 T 画统计图 F 不画',
  `draw_Chart_Begin_Col` decimal(4, 0) NULL DEFAULT NULL,
  `draw_Chart_End_Col` decimal(4, 0) NULL DEFAULT NULL,
  `addition_Row` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0 : 没有  1 合计  2 均值  3 合计 和 均值',
  `row_Logic` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `row_Logic_Value` decimal(4, 0) NULL DEFAULT NULL,
  `LOGIC_URL` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Column_Sql` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_TREE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HAS_SUM` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WIZARD_NO` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '如果不为空说明是通过查询向导生成的',
  PRIMARY KEY (`Model_Name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_report_model
-- ----------------------------
DROP TABLE IF EXISTS `q_report_model`;
CREATE TABLE `q_report_model`  (
  `REPORT_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PACKET_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REPORT_NAME_FORMAT` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REPORT_DOC_FILEID` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RECORDER` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RECORD_DATE` datetime(0) NULL DEFAULT NULL,
  `APPLICATION_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PHOTO_JS` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`REPORT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for q_report_sql
-- ----------------------------
DROP TABLE IF EXISTS `q_report_sql`;
CREATE TABLE `q_report_sql`  (
  `SQL_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Model_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Parent_SQL_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `property_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Database_Code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QUERY_SQL` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Query_DESC` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `query_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'S: 只有一个值 V：向量只有一行 T 表格',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creat_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`SQL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '这个制作 列表查询，没有统计语句' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_report_sql_column
-- ----------------------------
DROP TABLE IF EXISTS `q_report_sql_column`;
CREATE TABLE `q_report_sql_column`  (
  `SQL_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `col_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `col_Type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SQL_ID`, `col_Name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '这个表不需要维护，它 通过sql 语句自动生成 作用是作为 维护模板是做参考' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for q_report_sql_param
-- ----------------------------
DROP TABLE IF EXISTS `q_report_sql_param`;
CREATE TABLE `q_report_sql_param`  (
  `SQL_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PARAM_Name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MAP_PARENT_FIELD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '只有这个查询是子查询才有效',
  `param_Default_Value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数默认值',
  PRIMARY KEY (`PARAM_Name`, `SQL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
