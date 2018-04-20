create table Q_QueryModel  (
   ModelName            VARCHAR2(64)                    not null,
   Database_Code        varchar2(32),
   ModelType            CHAR,
   OwnerType            CHAR,
   OwnerCode            VARCHAR2(64),
   QUERYSQL             VARCHAR2(4000),
   QueryDESC            VARCHAR2(512),
   formNameFormat       VARCHAR2(256),
   resultName           VARCHAR2(64),
   rowDrawChart         CHAR,
   drawChartBeginCol    NUMBER(4),
   drawChartEndCol      NUMBER(4),
   additionRow          CHAR,
   rowLogic             VARCHAR2(64),
   rowLogicValue        NUMBER(4),
   LOGICURL             VARCHAR2(512),
   ColumnSql            VARCHAR2(2048),
   ISTREE               varchar2(8),
   HASSUM               varchar2(8),
   WIZARD_NO            varchar2(32),
   constraint PK_Q_QUERYMODEL primary key (ModelName)
);

comment on column Q_QueryModel.ModelType is
'2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表';

comment on column Q_QueryModel.OwnerType is
'机构或者个人';

comment on column Q_QueryModel.rowDrawChart is
'是否按行 T 画统计图 F 不画';

comment on column Q_QueryModel.additionRow is
'0 : 没有  1 合计  2 均值  3 合计 和 均值';

comment on column Q_QueryModel.WIZARD_NO is
'如果不为空说明是通过查询向导生成的';

create table Q_QueryCondition  (
   ModelName            VARCHAR2(64)                    not null,
   condName             VARCHAR2(64)                    not null,
   condLabel            VARCHAR2(200)                   not null,
   condDisplayStyle     CHAR                            not null,
   paramType            VARCHAR2(64),
   compareType          CHAR,
   paramReferenceType   CHAR,
   paramReferenceData   VARCHAR2(1000),
   paramValidateRegex   VARCHAR2(200),
   paramValidateInfo    VARCHAR2(200),
   paramDefaultValue    VARCHAR2(200),
   condOrder            number(2),
   constraint PK_Q_QUERYCONDITION primary key (ModelName, condName)
);

comment on column Q_QueryCondition.condDisplayStyle is
'N:普通 nomal H 隐藏 hide R 只读 readonly';

comment on column Q_QueryCondition.paramType is
'S:文本 N数字  D：日期 T：时间戳（datetime)  ';

comment on column Q_QueryCondition.compareType is
'必需是时间字段， 3 同比分析  4 环比分析 0 其他';

comment on column Q_QueryCondition.paramReferenceType is
'0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份';

comment on column Q_QueryCondition.paramReferenceData is
'根据paramReferenceType类型（1,2,3）填写对应值';

comment on column Q_QueryCondition.paramValidateRegex is
'regex表达式';

comment on column Q_QueryCondition.paramValidateInfo is
'约束不通过提示信息';

comment on column Q_QueryCondition.paramDefaultValue is
'参数默认值';

create table Q_QueryColumn  (
   ModelName            VARCHAR2(64)                    not null,
   colName              VARCHAR2(64)                    not null,
   showType             CHAR,
   optType              CHAR,
   drawChart            CHAR,
   colType              CHAR,
   colLogic             VARCHAR2(120),
   COLORDER             number(2),
   ISSHOW               CHAR,
   COLFORMAT            VARCHAR2(64),
   LINKTYPE             VARCHAR(32),
   DEFAULTVALUE         VARCHAR(64),
   cssstyle VARCHAR2(120),
   CATALOGCODE VARCHAR2(64),
   constraint PK_Q_QUERYCOLUMN primary key (ModelName, colName)
);

comment on column Q_QueryColumn.showType is
'R 行头  C 列头  D 数值';

comment on column Q_QueryColumn.optType is
'0 : 不做 1 求和 2 求平均值 3 求和 求平均值';

comment on column Q_QueryColumn.drawChart is
' T 画统计图 F 不画';