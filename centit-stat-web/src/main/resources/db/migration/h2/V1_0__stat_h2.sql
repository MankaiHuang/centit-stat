drop table if exists Q_LIST_Condition;

drop table if exists Q_LIST_Model;

drop table if exists Q_List_Column;

drop table if exists Q_List_OPERATOR;

drop table if exists Q_Query_Column;

drop table if exists Q_Query_Condition;

drop table if exists Q_Query_Model;

drop table if exists Q_REPORT_MODEL;

drop table if exists Q_REPORT_SQL;

drop table if exists Q_REPORT_SQL_Column;

create table Q_LIST_Condition
(
   Model_Name           varchar(64) not null,
   cond_Name            varchar(64) not null,
   cond_Label           varchar(200) not null,
   cond_Display_Style   char(1) not null comment 'N:普通 nomal H 隐藏 hide R 只读 readonly',
   param_Type           varchar(64) comment 'S:文本 N数字  D：日期 T：时间戳（datetime)  ',
   param_Reference_Type char(1) comment '0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份',
   param_Reference_Data varchar(1000) comment '根据paramReferenceType类型（1,2,3）填写对应值',
   param_Validate_Regex varchar(200) comment 'regex表达式',
   param_Validate_Info  varchar(200) comment '约束不通过提示信息',
   param_Default_Value  varchar(200) comment '参数默认值',
   cond_Order           numeric(2,0),
   primary key (Model_Name, cond_Name)
);

create table Q_LIST_Model
(
   Model_Name           varchar(64) not null,
   model_title_Format varchar(256),
   Database_Code        varchar(32),
   Owner_Type           char(1) comment '机构或者个人',
   Owner_Code           varchar(64),
   QUERY_SQL            varchar(4000),
   Query_DESC           varchar(512),
   page_type            char(1) comment 'T/F',
   page_size            numeric(4,0),
   creator              varchar(32),
   create_time           datetime,
   primary key (Model_Name)
);

alter table Q_LIST_Model comment '这个制作 列表查询，没有统计语句';

create table Q_List_Column
(
   Model_Name           varchar(64) not null,
   col_Name             varchar(64) not null,
   col_Type             char(1),
   col_title            varchar(120),
   COL_ORDER            numeric(2,0),
   SHOW_TYPE            char(1) comment 'H:不显示，S：显示； V：显示数据字段对应值；D：和数据字典对应值一起显示',
   COL_FORMAT           varchar(64),
   LINK_TYPE            char(1) comment 'T/F',
   LINK_URL_FORMAT      varchar(500) comment '连接url模板，需要根据值转换',
   catalog_code         varchar(64),
   catalog_value_code   varchar(64) comment '数据字典值对应的 代码',
   catalog_value_title  varchar(120) comment '数据字典值对应的 表头',
   primary key (Model_Name, col_Name)
);

create table Q_List_OPERATOR
(
   Model_Name           varchar(64) not null,
   opt_Name             varchar(64) not null,
   opt_Type             char(1) comment '更改、查看、警告 用来控制样式',
   opt_hint             varchar(200),
   opt_ORDER            numeric(2,0),
   OPT_URL_FORMAT       varchar(500) comment '连接url模板，需要根据值转换',
   primary key (Model_Name, opt_Name)
);

alter table Q_List_OPERATOR comment '列表操作描述';

create table Q_Query_Column
(
   Model_Name           varchar(64) not null,
   col_Name             varchar(64) not null,
   show_Type            char(1) comment 'R 行头  C 列头  D 数值',
   opt_Type             char(1) comment '0 : 不做 1 求和 2 求平均值 3 求和 求平均值',
   draw_Chart           char(1) comment ' T 画统计图 F 不画',
   col_Type             char(1),
   col_Logic            varchar(120),
   COL_ORDER            numeric(2,0),
   IS_SHOW              char(1),
   COL_FORMAT           varchar(64),
   LINK_TYPE            varchar(32),
   DEFAULT_VALUE        varchar(64),
   catalog_code         varchar(64),
   css_style            varchar(120),
   primary key (Model_Name, col_Name)
);

create table Q_Query_Condition
(
   Model_Name           varchar(64) not null,
   cond_Name            varchar(64) not null,
   cond_Label           varchar(200) not null,
   cond_Display_Style   char(1) not null comment 'N:普通 nomal H 隐藏 hide R 只读 readonly',
   param_Type           varchar(64) comment 'S:文本 N数字  D：日期 T：时间戳（datetime)  ',
   compare_Type         char(1) comment '必需是时间字段， 3 同比分析  4 环比分析 0 其他',
   param_Reference_Type char(1) comment '0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份',
   param_Reference_Data varchar(1000) comment '根据paramReferenceType类型（1,2,3）填写对应值',
   param_Validate_Regex varchar(200) comment 'regex表达式',
   param_Validate_Info  varchar(200) comment '约束不通过提示信息',
   param_Default_Value  varchar(200) comment '参数默认值',
   cond_Order           numeric(2,0),
   primary key (Model_Name, cond_Name)
);

create table Q_Query_Model
(
   Model_Name           varchar(64) not null,
   Database_Code        varchar(32),
   Model_Type           char(1) comment '2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表',
   Owner_Type           char(1) comment '机构或者个人',
   Owner_Code           varchar(64),
   QUERY_SQL            varchar(4000),
   Query_DESC           varchar(512),
   form_Name_Format     varchar(256),
   result_Name          varchar(64),
   row_Draw_Chart       char(1) comment '是否按行 T 画统计图 F 不画',
   draw_Chart_Begin_Col numeric(4,0),
   draw_Chart_End_Col   numeric(4,0),
   addition_Row         char(1) comment '0 : 没有  1 合计  2 均值  3 合计 和 均值',
   row_Logic            varchar(64),
   row_Logic_Value      numeric(4,0),
   LOGIC_URL            varchar(512),
   Column_Sql           varchar(2048),
   IS_TREE              varchar(8),
   HAS_SUM              varchar(8),
   WIZARD_NO            varchar(32) comment '如果不为空说明是通过查询向导生成的',
   primary key (Model_Name)
);

create table Q_REPORT_MODEL
(
   Model_Name           varchar(64) not null,
   model_title_Format varchar(256),
   Owner_Type           char(1) comment '机构或者个人',
   Owner_Code           varchar(64),
   MODEL_DESC           varchar(512),
   report_doc_fileid    varchar(64) comment 'DOCX文档模板，指向文件ID',
   primary key (Model_Name)
);

alter table Q_REPORT_MODEL comment '这个制作 列表查询，没有统计语句';

create table Q_REPORT_SQL
(
   Model_Name           varchar(64) not null,
   SQL_ID               varchar(32) not null,
   property_NAME        varchar(64),
   Database_Code        varchar(32),
   QUERY_SQL            varchar(4000),
   Query_DESC           varchar(512),
   query_type           char(1) comment 'S: 只有一个值 V：向量只有一行 T 表格',
   creator              varchar(32),
   create_time           datetime,
   primary key (SQL_ID)
);

alter table Q_REPORT_SQL comment '这个制作 列表查询，没有统计语句';

create table Q_REPORT_SQL_Column
(
   SQL_ID               varchar(32) not null,
   col_Name             varchar(64) not null,
   col_Type             char(1),
   primary key (SQL_ID, col_Name)
);

alter table Q_REPORT_SQL_Column comment '这个表不需要维护，它 通过sql 语句自动生成。作用是作为 维护模板是做参考';

