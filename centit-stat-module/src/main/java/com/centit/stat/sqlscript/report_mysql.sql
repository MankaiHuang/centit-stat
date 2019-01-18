drop table if exists Q_REPORT_MODEL;

drop table if exists Q_REPORT_SQL;

drop table if exists Q_REPORT_SQL_Column;

drop table if exists Q_REPORT_SQL_PARAM;

create table Q_REPORT_MODEL
(
   Model_Name           varchar(64) not null,
   MODEL_TITLE_FORMAT varchar(256),
   Owner_Type           char(1) comment '机构或者个人',
   Owner_Code           varchar(64),
   MODEL_DESC           varchar(512),
   REPORT_DOC_FILEID    varchar(64) comment 'DOCX文档模板，指向文件ID',
   primary key (Model_Name)
);

alter table Q_REPORT_MODEL comment '这个制作 列表查询，没有统计语句';

create table Q_REPORT_SQL
(
   SQL_ID               varchar(32) not null,
   Model_Name           varchar(64) not null,
   Parent_SQL_ID        varchar(32),
   property_NAME        varchar(64),
   Database_Code        varchar(32),
   QUERY_SQL            varchar(4000),
   Query_DESC           varchar(512),
   query_type           char(1) comment 'S: 只有一个值 V：向量只有一行 T 表格',
   creator              varchar(32),
   creat_time           datetime,
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

alter table Q_REPORT_SQL_Column comment '这个表不需要维护，它 通过sql 语句自动生成 作用是作为 维护模板是做参考';

create table Q_REPORT_SQL_PARAM
(
   SQL_ID               varchar(32) not null,
   PARAM_Name           varchar(64) not null,
   MAP_PARENT_FIELD     varchar(64) comment '只有这个查询是子查询才有效',
   param_Default_Value  varchar(200) comment '参数默认值',
   primary key (PARAM_Name, SQL_ID)
);

