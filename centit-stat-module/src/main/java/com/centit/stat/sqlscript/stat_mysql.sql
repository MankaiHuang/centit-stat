drop table if exists Q_CHART_Model;

drop table if exists Q_CHART_RESOURCE_Column;

drop table if exists Q_form_Column;

drop table if exists Q_form_Model;

drop table if exists Q_form_PARAM;

create table Q_CHART_Model
(
   chart_ID             varchar(64) not null,
   RESOURCE_ID          varchar(64),
   chart_Name_Format    varchar(256),
   chart_type           varchar(16),
   chart_design         text comment 'json格式的图表自定义说明',
   Recorder             varchar(32),
   Record_Date          datetime,
   primary key (chart_ID)
);

create table Q_CHART_RESOURCE_Column
(
   chart_ID             varchar(64) not null,
   column_code          varchar(64) not null,
   primary key (chart_ID, column_code)
);

create table Q_form_Column
(
   form_ID              varchar(64) not null,
   column_code          varchar(64) not null,
   opt_type             char(1) comment '0 无操作 1 合计 2 平均 3 平均合计',
   column_Type          char(1) comment 'R 行头 、 C 列头 、 D 数据',
   mine_url_Format      varchar(512) comment '模板可以引用行数据和 数据包查询参数',
   column_order         numeric(4,0),
   primary key (form_ID, column_code)
);

create table Q_form_Model
(
   form_ID              varchar(64) not null,
   RESOURCE_ID          varchar(64),
   from_Name_Format     varchar(256),
   fromt_type           varchar(16) comment '2 二维表 3 同比分析 4 环比分析 5 交叉制表',
   Recorder             varchar(32),
   RecordDate           datetime,
   primary key (form_ID)
);

create table Q_form_PARAM
(
   form_ID              varchar(64) not null,
   PARAM_Name           varchar(64) not null,
   compare_TYPE         char(1) comment '1 同比 2 环比',
   param_Default_Value  varchar(200) comment '参数默认值',
   primary key (form_ID, PARAM_Name)
);

alter table Q_form_PARAM comment '报表参数 和 数据包一样，这里只是自定那些是 用于 同比 和 环比 的时间参数';

