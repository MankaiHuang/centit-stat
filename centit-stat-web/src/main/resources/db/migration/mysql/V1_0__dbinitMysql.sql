create table Q_QueryModel  (
   ModelName            VARCHAR(64)                    not null,
   Database_Code        VARCHAR(32),
   ModelType            CHAR,
   OwnerType            CHAR,
   OwnerCode            VARCHAR(64),
   QUERYSQL             VARCHAR(4000),
   QueryDESC            VARCHAR(512),
   formNameFormat       VARCHAR(256),
   resultName           VARCHAR(64),
   rowDrawChart         CHAR,
   drawChartBeginCol    decimal(4),
   drawChartEndCol      decimal(4),
   additionRow          CHAR,
   rowLogic             VARCHAR(64),
   rowLogicValue        decimal(4),
   LOGICURL             VARCHAR(512),
   ColumnSql            VARCHAR(2048),
   ISTREE               VARCHAR(8),
   HASSUM               VARCHAR(8),
   WIZARD_NO            VARCHAR(32),
   constraint PK_Q_QUERYMODEL primary key (ModelName)
);



create table Q_QueryCondition  (
   ModelName            VARCHAR(64)                    not null,
   condName             VARCHAR(64)                    not null,
   condLabel            VARCHAR(200)                   not null,
   condDisplayStyle     CHAR                            not null,
   paramType            VARCHAR(64),
   compareType          CHAR,
   paramReferenceType   CHAR,
   paramReferenceData   VARCHAR(1000),
   paramValidateRegex   VARCHAR(200),
   paramValidateInfo    VARCHAR(200),
   paramDefaultValue    VARCHAR(200),
   condOrder            decimal(2),
   constraint PK_Q_QUERYCONDITION primary key (ModelName, condName)
);



create table Q_QueryColumn  (
   ModelName            VARCHAR(64)                    not null,
   colName              VARCHAR(64)                    not null,
   showType             CHAR,
   optType              CHAR,
   drawChart            CHAR,
   colType              CHAR,
   colLogic             VARCHAR(120),
   COLORDER             decimal(2),
   ISSHOW               CHAR,
   COLFORMAT            VARCHAR(64),
   LINKTYPE             VARCHAR(32),
   DEFAULTVALUE         VARCHAR(64),
   cssstyle VARCHAR(120),
   CATALOGCODE VARCHAR(64),
   constraint PK_Q_QUERYCOLUMN primary key (ModelName, colName)
);

