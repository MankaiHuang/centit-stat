drop table "Q_LIST_Condition" cascade constraints;

drop table "Q_LIST_Model" cascade constraints;

drop table "Q_List_Column" cascade constraints;

drop table "Q_List_OPERATOR" cascade constraints;

drop table "Q_Query_Column" cascade constraints;

drop table "Q_Query_Condition" cascade constraints;

drop table "Q_Query_Model" cascade constraints;

drop table Q_REPORT_MODEL cascade constraints;

drop table Q_REPORT_SQL cascade constraints;

drop table "Q_REPORT_SQL_Column" cascade constraints;

create table "Q_LIST_Condition" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   "cond_Name"          VARCHAR2(64)         not null,
   "cond_Label"         VARCHAR2(200)        not null,
   "cond_Display_Style" CHAR(1)              not null,
   "param_Type"         VARCHAR2(64),
   "param_Reference_Type" CHAR(1),
   "param_Reference_Data" VARCHAR2(1000),
   "param_Validate_Regex" VARCHAR2(200),
   "param_Validate_Info" VARCHAR2(200),
   "param_Default_Value" VARCHAR2(200),
   "cond_Order"         NUMBER(2,0),
   constraint PK_Q_LIST_CONDITION primary key ("Model_Name", "cond_Name")
);

comment on column "Q_LIST_Condition"."cond_Display_Style" is
'N:普通 nomal H 隐藏 hide R 只读 readonly';

comment on column "Q_LIST_Condition"."param_Type" is
'S:文本 N数字  D：日期 T：时间戳（datetime)  ';

comment on column "Q_LIST_Condition"."param_Reference_Type" is
'0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份';

comment on column "Q_LIST_Condition"."param_Reference_Data" is
'根据paramReferenceType类型（1,2,3）填写对应值';

comment on column "Q_LIST_Condition"."param_Validate_Regex" is
'regex表达式';

comment on column "Q_LIST_Condition"."param_Validate_Info" is
'约束不通过提示信息';

comment on column "Q_LIST_Condition"."param_Default_Value" is
'参数默认值';

create table "Q_LIST_Model" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   model_title_Format VARCHAR2(256),
   "Database_Code"      VARCHAR2(32),
   "Owner_Type"         CHAR(1),
   "Owner_Code"         VARCHAR2(64),
   QUERY_SQL            VARCHAR2(4000),
   "Query_DESC"         VARCHAR2(512),
   "page_type"          CHAR(1),
   "page_size"          NUMBER(4,0),
   "creator"            VARCHAR2(32),
   "create_time"         DATE,
   constraint PK_Q_LIST_MODEL primary key ("Model_Name")
);

comment on table "Q_LIST_Model" is
'这个制作 列表查询，没有统计语句';

comment on column "Q_LIST_Model"."Owner_Type" is
'机构或者个人';

comment on column "Q_LIST_Model"."page_type" is
'T/F';

create table "Q_List_Column" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   "col_Name"           VARCHAR2(64)         not null,
   "col_Type"           CHAR(1),
   "col_title"          VARCHAR2(120),
   COL_ORDER            NUMBER(2,0),
   SHOW_TYPE            CHAR(1),
   COL_FORMAT           VARCHAR2(64),
   LINK_TYPE            CHAR(1),
   LINK_URL_FORMAT      VARCHAR2(500),
   "catalog_code"       VARCHAR2(64),
   "catalog_value_code" VARCHAR2(64),
   "catalog_value_title" VARCHAR2(120),
   constraint PK_Q_LIST_COLUMN primary key ("Model_Name", "col_Name")
);

comment on column "Q_List_Column".SHOW_TYPE is
'H:不显示，S：显示； V：显示数据字段对应值；D：和数据字典对应值一起显示';

comment on column "Q_List_Column".LINK_TYPE is
'T/F';

comment on column "Q_List_Column".LINK_UTL_FORMAT is
'连接url模板，需要根据值转换';

comment on column "Q_List_Column"."catalog_value_code" is
'数据字典值对应的 代码';

comment on column "Q_List_Column"."catalog_value_title" is
'数据字典值对应的 表头';

create table "Q_List_OPERATOR" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   "opt_Name"           VARCHAR2(64)         not null,
   "opt_Type"           CHAR(1),
   "opt_hint"           VARCHAR2(200),
   "opt_ORDER"          NUMBER(2,0),
   OPT_URL_FORMAT       VARCHAR2(500),
   constraint PK_Q_LIST_OPERATOR primary key ("Model_Name", "opt_Name")
);

comment on table "Q_List_OPERATOR" is
'列表操作描述';

comment on column "Q_List_OPERATOR"."opt_Type" is
'更改、查看、警告 用来控制样式';

comment on column "Q_List_OPERATOR".OPT_UTL_FORMAT is
'连接url模板，需要根据值转换';

create table "Q_Query_Column" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   "col_Name"           VARCHAR2(64)         not null,
   "show_Type"          CHAR(1),
   "opt_Type"           CHAR(1),
   "draw_Chart"         CHAR(1),
   "col_Type"           CHAR(1),
   "col_Logic"          VARCHAR2(120),
   COL_ORDER            NUMBER(2,0),
   IS_SHOW              CHAR(1),
   COL_FORMAT           VARCHAR2(64),
   LINK_TYPE            VARCHAR2(32),
   DEFAULT_VALUE        VARCHAR2(64),
   "catalog_code"       VARCHAR2(64),
   "css_style"          VARCHAR2(120),
   constraint PK_Q_Query_Column primary key ("Model_Name", "col_Name")
);

comment on column "Q_Query_Column"."show_Type" is
'R 行头  C 列头  D 数值';

comment on column "Q_Query_Column"."opt_Type" is
'0 : 不做 1 求和 2 求平均值 3 求和 求平均值';

comment on column "Q_Query_Column"."draw_Chart" is
' T 画统计图 F 不画';

create table "Q_Query_Condition" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   "cond_Name"          VARCHAR2(64)         not null,
   "cond_Label"         VARCHAR2(200)        not null,
   "cond_Display_Style" CHAR(1)              not null,
   "param_Type"         VARCHAR2(64),
   "compare_Type"       CHAR(1),
   "param_Reference_Type" CHAR(1),
   "param_Reference_Data" VARCHAR2(1000),
   "param_Validate_Regex" VARCHAR2(200),
   "param_Validate_Info" VARCHAR2(200),
   "param_Default_Value" VARCHAR2(200),
   "cond_Order"         NUMBER(2,0),
   constraint PK_Q_Query_Condition primary key ("Model_Name", "cond_Name")
);

comment on column "Q_Query_Condition"."cond_Display_Style" is
'N:普通 nomal H 隐藏 hide R 只读 readonly';

comment on column "Q_Query_Condition"."param_Type" is
'S:文本 N数字  D：日期 T：时间戳（datetime)  ';

comment on column "Q_Query_Condition"."compare_Type" is
'必需是时间字段， 3 同比分析  4 环比分析 0 其他';

comment on column "Q_Query_Condition"."param_Reference_Type" is
'0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份';

comment on column "Q_Query_Condition"."param_Reference_Data" is
'根据paramReferenceType类型（1,2,3）填写对应值';

comment on column "Q_Query_Condition"."param_Validate_Regex" is
'regex表达式';

comment on column "Q_Query_Condition"."param_Validate_Info" is
'约束不通过提示信息';

comment on column "Q_Query_Condition"."param_Default_Value" is
'参数默认值';

create table "Q_Query_Model" 
(
   "Model_Name"         VARCHAR2(64)         not null,
   "Database_Code"      VARCHAR2(32),
   "Model_Type"         CHAR(1),
   "Owner_Type"         CHAR(1),
   "Owner_Code"         VARCHAR2(64),
   QUERY_SQL            VARCHAR2(4000),
   "Query_DESC"         VARCHAR2(512),
   "form_Name_Format"   VARCHAR2(256),
   "result_Name"        VARCHAR2(64),
   "row_Draw_Chart"     CHAR(1),
   "draw_Chart_Begin_Col" NUMBER(4,0),
   "draw_Chart_End_Col" NUMBER(4,0),
   "addition_Row"       CHAR(1),
   "row_Logic"          VARCHAR2(64),
   "row_Logic_Value"    NUMBER(4,0),
   LOGIC_URL            VARCHAR2(512),
   "Column_Sql"         VARCHAR2(2048),
   IS_TREE              VARCHAR2(8),
   HAS_SUM              VARCHAR2(8),
   WIZARD_NO            VARCHAR2(32),
   constraint PK_Q_Query_Model primary key ("Model_Name")
);

comment on column "Q_Query_Model"."Model_Type" is
'2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表';

comment on column "Q_Query_Model"."Owner_Type" is
'机构或者个人';

comment on column "Q_Query_Model"."row_Draw_Chart" is
'是否按行 T 画统计图 F 不画';

comment on column "Q_Query_Model"."addition_Row" is
'0 : 没有  1 合计  2 均值  3 合计 和 均值';

comment on column "Q_Query_Model".WIZARD_NO is
'如果不为空说明是通过查询向导生成的';

create table Q_REPORT_MODEL 
(
   "Model_Name"         VARCHAR2(64)         not null,
   model_title_Format VARCHAR2(256),
   "Owner_Type"         CHAR(1),
   "Owner_Code"         VARCHAR2(64),
   MODEL_DESC           VARCHAR2(512),
   "report_doc_fileid"  VARCHAR2(64),
   constraint PK_Q_REPORT_MODEL primary key ("Model_Name")
);

comment on table Q_REPORT_MODEL is
'这个制作 列表查询，没有统计语句';

comment on column Q_REPORT_MODEL."Owner_Type" is
'机构或者个人';

comment on column Q_REPORT_MODEL."report_doc_fileid" is
'DOCX文档模板，指向文件ID';

create table Q_REPORT_SQL 
(
   "Model_Name"         VARCHAR2(64)         not null,
   SQL_ID               VARCHAR2(32)         not null,
   PARENT_SQL_ID        VARCHAR2(32),
   "property_NAME"      VARCHAR2(64),
   "Database_Code"      VARCHAR2(32),
   QUERY_SQL            VARCHAR2(4000),
   "Query_DESC"         VARCHAR2(512),
   "query_type"         CHAR(1),
   "creator"            VARCHAR2(32),
   "create_time"         DATE,
   constraint PK_Q_REPORT_SQL primary key (SQL_ID)
);

comment on table Q_REPORT_SQL is
'这个制作 列表查询，没有统计语句';

comment on column Q_REPORT_SQL."query_type" is
'S: 只有一个值 V：向量只有一行 T 表格';

create table "Q_REPORT_SQL_Column" 
(
   SQL_ID               VARCHAR2(32)         not null,
   "col_Name"           VARCHAR2(64)         not null,
   "col_Type"           CHAR(1),
   constraint PK_Q_REPORT_SQL_COLUMN primary key (SQL_ID, "col_Name")
);

comment on table "Q_REPORT_SQL_Column" is
'这个表不需要维护，它 通过sql 语句自动生成。作用是作为 维护模板是做参考';

