/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2017/4/7 星期五 21:52:49                        */
/*==============================================================*/



/*==============================================================*/
/* Table: AC_KPI_RELATION                                       */
/*==============================================================*/
CREATE TABLE AC_KPI_RELATION 
(
   ID                   CHAR(32),
   KPI_ID               CHAR(32),
   REPORT_ID            CHAR(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER
);

COMMENT ON COLUMN AC_KPI_RELATION.CREATEBY IS
'创建人';

COMMENT ON COLUMN AC_KPI_RELATION.CREATETIME IS
'创建时间';

COMMENT ON COLUMN AC_KPI_RELATION.LASTMODIFYBY IS
'最后修改';

COMMENT ON COLUMN AC_KPI_RELATION.LASTMODIFYTIME IS
'上次修改时间';

COMMENT ON COLUMN AC_KPI_RELATION.VERSION IS
'版本';

/*==============================================================*/
/* Table: AC_PARAM                                              */
/*==============================================================*/
CREATE TABLE AC_PARAM 
(
   ID                   CHAR(32)             NOT NULL,
   DISPLAY_NAME         VARCHAR2(128),
   NAME                 VARCHAR2(128),
   TYPE                 VARCHAR2(20),
   VIEW_TYPE            VARCHAR2(20),
   PID                  CHAR(32),
   CUBE_TEXT_FORMAT     VARCHAR2(128),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   DATA_FROM            CLOB,
   DATA_FROM_TYPE       VARCHAR2(20),
   DEFAULT_VALUE        VARCHAR2(200),
   MUSTPUT              CHAR(1),
   COLSPAN              INTEGER,
   CONSTRAINT PK_AC_PARAM PRIMARY KEY (ID)
);

COMMENT ON TABLE AC_PARAM IS
'参数定义';

/*==============================================================*/
/* Table: AC_PARAM_RELATION                                     */
/*==============================================================*/
CREATE TABLE AC_PARAM_RELATION 
(
   ID                   CHAR(32)             NOT NULL,
   PARAM_ID             CHAR(32),
   REPORT_ID            CHAR(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_AC_PARAM_RELATION PRIMARY KEY (ID)
);

COMMENT ON COLUMN AC_PARAM_RELATION.CREATEBY IS
'创建人';

COMMENT ON COLUMN AC_PARAM_RELATION.CREATETIME IS
'创建时间';

COMMENT ON COLUMN AC_PARAM_RELATION.LASTMODIFYBY IS
'最后修改';

COMMENT ON COLUMN AC_PARAM_RELATION.LASTMODIFYTIME IS
'上次修改时间';

COMMENT ON COLUMN AC_PARAM_RELATION.VERSION IS
'版本';

/*==============================================================*/
/* Table: AC_REPORT_NODE                                        */
/*==============================================================*/
CREATE TABLE AC_REPORT_NODE 
(
   ID                   CHAR(32)             NOT NULL,
   REPORT_NAME          VARCHAR2(256),
   PARENT_PATH          VARCHAR2(2048),
   REPORT_PATH          VARCHAR2(2048),
   LEAF                 CHAR(1),
   CUBE_FLAG            CHAR(11),
   DATA_SOURCE          CHAR(32),
   PARAM_TYPE           VARCHAR2(50),
   PARAM_TEMPLATE_PATH  VARCHAR2(2048),
   COMMENTS             CLOB,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   REPORT_TYPE          VARCHAR2(20),
   RESOURCE_ID          VARCHAR2(100),    --原来是char(64)，这在linux环境上出了问题
   CONSTRAINT PK_AC_REPORT_NODE PRIMARY KEY (ID)
);

COMMENT ON TABLE AC_REPORT_NODE IS
'报表资源表';

COMMENT ON COLUMN AC_REPORT_NODE.ID IS
'id';

COMMENT ON COLUMN AC_REPORT_NODE.REPORT_NAME IS
'报表名称';

COMMENT ON COLUMN AC_REPORT_NODE.PARENT_PATH IS
'报表所在目录名称';

COMMENT ON COLUMN AC_REPORT_NODE.REPORT_PATH IS
'报表路径';

COMMENT ON COLUMN AC_REPORT_NODE.LEAF IS
'根标识';

COMMENT ON COLUMN AC_REPORT_NODE.CUBE_FLAG IS
'cube报表标志，如果勾选这种标志，参数需要走特殊cube格式处理';

COMMENT ON COLUMN AC_REPORT_NODE.DATA_SOURCE IS
'数据源';

COMMENT ON COLUMN AC_REPORT_NODE.PARAM_TYPE IS
'参数类型';

COMMENT ON COLUMN AC_REPORT_NODE.CREATEBY IS
'创建人';

COMMENT ON COLUMN AC_REPORT_NODE.CREATETIME IS
'创建时间';

COMMENT ON COLUMN AC_REPORT_NODE.LASTMODIFYBY IS
'最后修改';

COMMENT ON COLUMN AC_REPORT_NODE.LASTMODIFYTIME IS
'上次修改时间';

COMMENT ON COLUMN AC_REPORT_NODE.VERSION IS
'版本';

COMMENT ON COLUMN AC_REPORT_NODE.RESOURCE_ID IS
'主要用于smartbi报表查询使用';

/*==============================================================*/
/* Index: INDEX_PACK_PAGE                                       */
/*==============================================================*/
CREATE INDEX INDEX_PACK_PAGE ON AC_REPORT_NODE (
   PARENT_PATH ASC
);

/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2017/4/7 星期五 21:52:02                        */
/*==============================================================*/


/*==============================================================*/
/* Table: AC_COMMON_DIR                                         */
/*==============================================================*/
CREATE TABLE AC_COMMON_DIR 
(
   ID                   VARCHAR2(50)         NOT NULL,
   DIR_NAME             VARCHAR2(128),
   PARENT_ID            VARCHAR2(50),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CATEGORY             VARCHAR2(128),
   DEF1                 VARCHAR2(128),
   DEF2                 VARCHAR2(128),
   DEF3                 VARCHAR2(128),
   CONSTRAINT PK_AC_COMMON_DIR PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: AC_LAYOUT_COLUMN                                      */
/*==============================================================*/
CREATE TABLE AC_LAYOUT_COLUMN 
(
   ID                   VARCHAR2(50)         NOT NULL,
   LAYOUT_ID            VARCHAR2(50),
   COLUMN_ID            VARCHAR2(50),
   COLSPAN              INTEGER,
   SORTNUM              INTEGER,
   CONSTRAINT PK_AC_LAYOUT_COLUMN PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: AC_PAGE                                               */
/*==============================================================*/
CREATE TABLE AC_PAGE 
(
   ID                   CHAR(32)             NOT NULL,
   PAGE_NAME            VARCHAR2(128),
   PAGE_DESC            VARCHAR2(500),
   LAYOUT_ID            VARCHAR2(50),
   DIR_ID               VARCHAR2(50),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_AC_PAGE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: AC_PAGE_WIDGET                                        */
/*==============================================================*/
CREATE TABLE AC_PAGE_WIDGET 
(
   ID                   CHAR(32)             NOT NULL,
   COLUMN_ID            VARCHAR2(50),
   WIDGET_TYPE          VARCHAR2(50),
   WIDGET_STYLE         VARCHAR2(50),
   WIDGET_ID            CHAR(32),
   PAGE_ID              CHAR(32),
   TITLE                VARCHAR2(128),
   TITLE_SHOW           CHAR(1),
   WIDTH                VARCHAR2(50),
   HEIGHT               INTEGER,
   AUTO_HEIGHT          CHAR(1),
   FRAMESKIN            SMALLINT,
   OPENSTATE            CHAR(1),
   TOOLBAR              CHAR(1),
   SHORTCUTBAR          CHAR(1),
   SIMPLETABLE          CHAR(1),
   COLSPAN              INTEGER,
   ACTIONS              VARCHAR2(256),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_AC_PAGE_WIDGET PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: AC_PARAM_LIBRARY                                      */
/*==============================================================*/
CREATE TABLE AC_PARAM_LIBRARY 
(
   ID                   CHAR(32)             NOT NULL,
   NAME                 VARCHAR2(128),
   COMMENTS             VARCHAR2(500),
   PID                  CHAR(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_AC_PARAM_LIBRARY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: AC_WIDGET_ACTION                                      */
/*==============================================================*/
CREATE TABLE AC_WIDGET_ACTION 
(
   ID                   VARCHAR2(50)         NOT NULL,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(128),
   TEMPLATE             CLOB,
   WIDGETTYPE           VARCHAR2(50),
   WIDGETID             VARCHAR2(50),
   MEMO                 VARCHAR2(500),
   IS_PRESET            CHAR(1),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_AC_WIDGET_ACTION PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: AC_WIDGET_META                                        */
/*==============================================================*/
CREATE TABLE AC_WIDGET_META 
(
   ID                   VARCHAR2(50)         NOT NULL,
   NAME                 VARCHAR2(128),
   STYLE                VARCHAR2(128),
   TYPE                 VARCHAR2(50),
   PREFERENCE           CLOB,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   MEMO                 VARCHAR2(500),
   DIRID                VARCHAR2(2048),
   LOADURL              VARCHAR2(128),
   CONSTRAINT PK_AC_WIDGET_META PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PT_CHANGE_HISTORY                                     */
/*==============================================================*/
CREATE TABLE PT_CHANGE_HISTORY 
(
   ID                   CHAR(32)             NOT NULL,
   TITLE                VARCHAR2(256),
   COMMENTS             VARCHAR2(521),
   OPTYPE               VARCHAR2(32),
   CHANGETIME           VARCHAR2(50),
   LINK_ID              VARCHAR2(32),
   REPORT_NAME          VARCHAR2(128),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CHANGENAME           VARCHAR2(128),
   OPERATIONTIME        INTEGER,
   CONSTRAINT PK_PT_CHANGE_HISTORY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PT_DATASOURCE                                         */
/*==============================================================*/
CREATE TABLE PT_DATASOURCE 
(
   ID                   CHAR(32)             NOT NULL,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(128),
   USERNAME             VARCHAR2(50),
   PASSWORD             VARCHAR2(64),
   SPAN                 VARCHAR2(50),
   PERMISSIONURL        VARCHAR2(2048),
   TYPE                 VARCHAR2(50),
   IP                   VARCHAR2(15),
   PORT                 VARCHAR2(5),
   CONTEXT              VARCHAR2(2048),
   DEFAULT_FLAG         SMALLINT,
   URL                  VARCHAR2(2048),
   COMMENTS             VARCHAR2(500),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   DRIVER               VARCHAR2(128),
   ISDEFAULT            VARCHAR2(1),
   PX_URL               VARCHAR2(1024),
   PATH                 VARCHAR2(500),
   DIRID                VARCHAR2(32),
   PRODUCT_VERSION 		VARCHAR2(128),
   KEYTABPATH			VARCHAR2(2048),
   KRBCONFPATH			VARCHAR2(2048),
   AUTHENTICATION_MODEL INTEGER,
   CLASSIFY				VARCHAR2(128),
   CONSTRAINT PK_PT_DATASOURCE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PT_SQLDS                                              */
/*==============================================================*/
CREATE TABLE PT_SQLDS 
(
   ID                   CHAR(32)             NOT NULL,
   CODE                 VARCHAR2(50),
   NAME                 VARCHAR2(128),
   USERNAME             VARCHAR2(50),
   PASSWORD             VARCHAR2(50),
   URL                  VARCHAR2(2048),
   COMMENTS             VARCHAR2(256),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   DRIVER               VARCHAR2(128),
   CONSTRAINT PK_PT_SQLDS PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RES_ACCESSLOG                                         */
/*==============================================================*/
CREATE TABLE RES_ACCESSLOG 
(
   ID                   CHAR(32)             NOT NULL,
   RES_ID               VARCHAR2(32),
   NAME                 VARCHAR2(100),
   TYPE                 VARCHAR2(50),
   URL                  VARCHAR2(1028),
   MSG                  VARCHAR2(256),
   PK_USER              VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT PK_RES_ACCESSLOG PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_FUNCDEC                                            */
/*==============================================================*/
CREATE TABLE ST_FUNCDEC 
(
   ID                   CHAR(32)             NOT NULL,
   NAME                 VARCHAR2(128),
   MEMO                 CLOB,
   FILEURL              VARCHAR2(2048),
   MENUID               VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   SORTNUM              INTEGER,
   CONSTRAINT PK_ST_FUNCDEC PRIMARY KEY (ID)
);

/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2017/4/7 星期五 21:51:09                        */
/*==============================================================*/


/*==============================================================*/
/* Table: QRTZ_BLOB_TRIGGERS                                    */
/*==============================================================*/
CREATE TABLE QRTZ_BLOB_TRIGGERS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   BLOB_DATA            BLOB,
   CONSTRAINT PK_QRTZ_BLOB_TRIGGERS PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: QRTZ_CALENDARS                                        */
/*==============================================================*/
CREATE TABLE QRTZ_CALENDARS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   CALENDAR_NAME        VARCHAR2(200)        NOT NULL,
   CALENDAR             BLOB                 NOT NULL,
   CONSTRAINT PK_QRTZ_CALENDARS PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

/*==============================================================*/
/* Table: QRTZ_CRON_TRIGGERS                                    */
/*==============================================================*/
CREATE TABLE QRTZ_CRON_TRIGGERS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   CRON_EXPRESSION      VARCHAR2(200)        NOT NULL,
   TIME_ZONE_ID         VARCHAR2(80),
   CONSTRAINT PK_QRTZ_CRON_TRIGGERS PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: QRTZ_FIRED_TRIGGERS                                   */
/*==============================================================*/
CREATE TABLE QRTZ_FIRED_TRIGGERS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   ENTRY_ID             VARCHAR2(95)         NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   INSTANCE_NAME        VARCHAR2(200)        NOT NULL,
   FIRED_TIME           INTEGER              NOT NULL,
   SCHED_TIME           INTEGER              NOT NULL,
   PRIORITY             INTEGER              NOT NULL,
   STATE                VARCHAR2(16)         NOT NULL,
   JOB_NAME             VARCHAR2(200),
   JOB_GROUP            VARCHAR2(200),
   IS_NONCONCURRENT     VARCHAR2(1),
   REQUESTS_RECOVERY    VARCHAR2(1),
   CONSTRAINT PK_QRTZ_FIRED_TRIGGERS PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

/*==============================================================*/
/* Table: QRTZ_JOB_DETAILS                                      */
/*==============================================================*/
CREATE TABLE QRTZ_JOB_DETAILS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   JOB_NAME             VARCHAR2(200)        NOT NULL,
   JOB_GROUP            VARCHAR2(200)        NOT NULL,
   DESCRIPTION          VARCHAR2(250),
   JOB_CLASS_NAME       VARCHAR2(250)        NOT NULL,
   IS_DURABLE           VARCHAR2(1)          NOT NULL,
   IS_NONCONCURRENT     VARCHAR2(1)          NOT NULL,
   IS_UPDATE_DATA       VARCHAR2(1)          NOT NULL,
   REQUESTS_RECOVERY    VARCHAR2(1)          NOT NULL,
   JOB_DATA             BLOB,
   CONSTRAINT PK_QRTZ_JOB_DETAILS PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

/*==============================================================*/
/* Table: QRTZ_LOCKS                                            */
/*==============================================================*/
CREATE TABLE QRTZ_LOCKS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   LOCK_NAME            VARCHAR2(40)         NOT NULL,
   CONSTRAINT PK_QRTZ_LOCKS PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);

/*==============================================================*/
/* Table: QRTZ_PAUSED_TRIGGER_GRPS                              */
/*==============================================================*/
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   CONSTRAINT PK_QRTZ_PAUSED_TRIGGER_GRPS PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: QRTZ_SCHEDULER_STATE                                  */
/*==============================================================*/
CREATE TABLE QRTZ_SCHEDULER_STATE 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   INSTANCE_NAME        VARCHAR2(200)        NOT NULL,
   LAST_CHECKIN_TIME    INTEGER              NOT NULL,
   CHECKIN_INTERVAL     INTEGER              NOT NULL,
   CONSTRAINT PK_QRTZ_SCHEDULER_STATE PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

/*==============================================================*/
/* Table: QRTZ_SIMPLE_TRIGGERS                                  */
/*==============================================================*/
CREATE TABLE QRTZ_SIMPLE_TRIGGERS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   REPEAT_COUNT         INTEGER              NOT NULL,
   REPEAT_INTERVAL      INTEGER              NOT NULL,
   TIMES_TRIGGERED      INTEGER              NOT NULL,
   CONSTRAINT PK_QRTZ_SIMPLE_TRIGGERS PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: QRTZ_SIMPROP_TRIGGERS                                 */
/*==============================================================*/
CREATE TABLE QRTZ_SIMPROP_TRIGGERS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   STR_PROP_1           VARCHAR2(512),
   STR_PROP_2           VARCHAR2(512),
   STR_PROP_3           VARCHAR2(512),
   INT_PROP_1           INTEGER,
   INT_PROP_2           INTEGER,
   LONG_PROP_1          INTEGER,
   LONG_PROP_2          INTEGER,
   DEC_PROP_1           NUMBER(13,4),
   DEC_PROP_2           NUMBER(13,4),
   BOOL_PROP_1          VARCHAR2(1),
   BOOL_PROP_2          VARCHAR2(1),
   CONSTRAINT PK_QRTZ_SIMPROP_TRIGGERS PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: QRTZ_TRIGGERS                                         */
/*==============================================================*/
CREATE TABLE QRTZ_TRIGGERS 
(
   SCHED_NAME           VARCHAR2(120)        NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   JOB_NAME             VARCHAR2(200)        NOT NULL,
   JOB_GROUP            VARCHAR2(200)        NOT NULL,
   DESCRIPTION          VARCHAR2(250),
   NEXT_FIRE_TIME       INTEGER,
   PREV_FIRE_TIME       INTEGER,
   PRIORITY             INTEGER,
   TRIGGER_STATE        VARCHAR2(16)         NOT NULL,
   TRIGGER_TYPE         VARCHAR2(8)          NOT NULL,
   START_TIME           INTEGER              NOT NULL,
   END_TIME             INTEGER,
   CALENDAR_NAME        VARCHAR2(200),
   MISFIRE_INSTR        SMALLINT,
   JOB_DATA             BLOB,
   CONSTRAINT PK_QRTZ_TRIGGERS PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: RMS_NOTICE_RANGE                                      */
/*==============================================================*/
CREATE TABLE RMS_NOTICE_RANGE 
(
   ID                   CHAR(32)             NOT NULL,
   RANGE_ID             VARCHAR2(32),
   NOTICE_ID            VARCHAR2(32),
   TYPE                 INTEGER,
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   CONSTRAINT PK_RMS_NOTICE_RANGE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_SYS_NOTICE                                        */
/*==============================================================*/
CREATE TABLE RMS_SYS_NOTICE 
(
   ID                   CHAR(32)             NOT NULL,
   TITLE                VARCHAR2(200),
   CONTENT              VARCHAR2(2000),
   PUBLISH_DATE         INTEGER,
   START_DATE           INTEGER,
   END_DATE             INTEGER,
   VALID_FLAG           CHAR(1),
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_RMS_SYS_NOTICE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_SYS_NOTICE_HISTORY                                */
/*==============================================================*/
CREATE TABLE RMS_SYS_NOTICE_HISTORY 
(
   ID                   CHAR(32)             NOT NULL,
   NOTICE_ID            VARCHAR2(32),
   USER_ID              VARCHAR2(32),
   ACCESS_DATE          INTEGER,
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_RMS_SYS_NOTICE_HISTORY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: SCHED_ACCESSLOG                                       */
/*==============================================================*/
CREATE TABLE SCHED_ACCESSLOG 
(
   ID                   CHAR(32)             NOT NULL,
   JOB_NAME             VARCHAR2(200)        NOT NULL,
   JOB_GROUP            VARCHAR2(200)        NOT NULL,
   TRIGGER_NAME         VARCHAR2(200)        NOT NULL,
   TRIGGER_GROUP        VARCHAR2(200)        NOT NULL,
   BEGINTIME            INTEGER,
   ENDTIME              INTEGER,
   ELAPSEDTIME          INTEGER,
   RESULT               SMALLINT,
   MEMO                 VARCHAR2(256),
   VERSION              INTEGER,
   USERNAME             VARCHAR2(128),
   LOGNAME              VARCHAR2(128),
   CONSTRAINT PK_SCHED_ACCESSLOG PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TOOLS_MESSAGE                                         */
/*==============================================================*/
CREATE TABLE TOOLS_MESSAGE 
(
   ID                   CHAR(32)             NOT NULL,
   TOUSERID             VARCHAR2(32)         NOT NULL,
   FROMUSERID           VARCHAR2(32)         NOT NULL,
   TOUSERACCOUT         VARCHAR2(32)         NOT NULL,
   FROMUSERACCOUNT      VARCHAR2(32)         NOT NULL,
   SUBJECT              VARCHAR2(128),
   CONTENT              CLOB,
   IFLOOK               CHAR(1),
   TRANSTIME            INTEGER,
   MSGTYPE              SMALLINT,
   REPLY_BY             VARCHAR2(32),
   ATTACHMENTS          VARCHAR2(128),
   VERSION              INTEGER,
   CONSTRAINT PK_TOOLS_MESSAGE PRIMARY KEY (ID)
);

/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2017/4/7 星期五 21:49:22                        */
/*==============================================================*/


/*==============================================================*/
/* Table: RMS_ORGS                                              */
/*==============================================================*/
CREATE TABLE RMS_ORGS 
(
   ID                   CHAR(32)             NOT NULL,
   ORGCODE              VARCHAR2(50),
   ORGNAME              VARCHAR2(128),
   PK_FATHERORG         VARCHAR2(32),
   INNERCODE            VARCHAR2(50),
   MEMO                 VARCHAR2(500),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   SHORTNAME            VARCHAR2(128),
   CONSTRAINT PK_RMS_ORGS PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_POWER_BUTN                                        */
/*==============================================================*/
CREATE TABLE RMS_POWER_BUTN 
(
   ID                   CHAR(32)             NOT NULL,
   PK_ROLE              VARCHAR2(32),
   RESOURCE_ID          VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT PK_RMS_POWER_BUTN PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_POWER_MENU                                        */
/*==============================================================*/
CREATE TABLE RMS_POWER_MENU 
(
   ID                   CHAR(32)             NOT NULL,
   PK_ROLE              VARCHAR2(32),
   RESOURCE_ID          VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT PK_RMS_POWER_MENU PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_POWER_ORG                                         */
/*==============================================================*/
CREATE TABLE RMS_POWER_ORG 
(
   ID                   CHAR(32)             NOT NULL,
   PK_ROLE              VARCHAR2(32),
   RESOURCE_ID          VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT PK_RMS_POWER_ORG PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_POWER_PAGE                                        */
/*==============================================================*/
CREATE TABLE RMS_POWER_PAGE 
(
   ID                   CHAR(32)             NOT NULL,
   PK_ROLE              VARCHAR2(32),
   RESOURCE_ID          VARCHAR2(32),
   OBJTYPE              SMALLINT,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT PK_RMS_POWER_PAGE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_ROLE                                              */
/*==============================================================*/
CREATE TABLE RMS_ROLE 
(
   ID                   CHAR(32)             NOT NULL,
   ROLENAME             VARCHAR2(128),
   PK_ORG               VARCHAR2(32),
   MEMO                 VARCHAR2(500),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   AGINGLIMIT           CHAR(1),
   PERMISSIONAGING      INTEGER,
   AGINGUNIT            CHAR(1),
   CONSTRAINT PK_RMS_ROLE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_USER                                              */
/*==============================================================*/
CREATE TABLE RMS_USER 
(
   ID                   CHAR(32)             NOT NULL,
   ACCOUNT              VARCHAR2(50),
   USERNAME             VARCHAR2(128),
   USERTYPE             SMALLINT,
   ONLYADMIN            CHAR(1),
   USER_PASSWORD        VARCHAR2(64),
   MOBILE               VARCHAR2(30),
   TEL                  VARCHAR2(30),
   EMAIL                VARCHAR2(50),
   SEX                  SMALLINT,
   PK_ORG               VARCHAR2(32),
   ISRESET              CHAR(1),
   ISLOCKED             CHAR(1),
   ABLETIME             INTEGER,
   DISABLETIME          INTEGER,
   MEMO                 VARCHAR2(500),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   ORIGIN               SMALLINT             DEFAULT 0,
   UPDATE_PWD_TIME      INTEGER,
   ISDISABLED           CHAR(1),
   CONSTRAINT PK_RMS_USER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "TMP_RMS_USER"                                        */
/*==============================================================*/
CREATE TABLE "TMP_RMS_USER" 
(
   ID                   CHAR(32)             NOT NULL,
   USERID               CHAR(32)             DEFAULT NULL,
   ACCOUNT              VARCHAR2(50)         DEFAULT NULL,
   NAME                 VARCHAR2(128)        DEFAULT NULL,
   ISLOCKED             CHAR(1)              DEFAULT NULL,
   ISDISABLED         	CHAR(1)              DEFAULT NULL,
   MOBILE               VARCHAR2(30)         DEFAULT NULL,
   TEL                  VARCHAR2(30)         DEFAULT NULL,
   EMAIL                VARCHAR2(50)         DEFAULT NULL,
   SEX                  SMALLINT             DEFAULT NULL,
   MEMO                 VARCHAR2(500)        DEFAULT NULL,
   USERCREATETIME      	INTEGER              DEFAULT NULL,
   USERLASTMODIFYTIME  	INTEGER              DEFAULT NULL,
   SYNCSTATE           	CHAR(1)              DEFAULT NULL,
   CREATETIME           INTEGER              DEFAULT NULL,
   LASTMODIFYTIME       INTEGER              DEFAULT NULL,
   CONSTRAINT PK_TMP_RMS_USER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_USERGROUP                                         */
/*==============================================================*/
CREATE TABLE RMS_USERGROUP 
(
   ID                   CHAR(32)             NOT NULL,
   GROUPNAME            VARCHAR2(128),
   PARENTID             VARCHAR2(32),
   INNERCODE            VARCHAR2(50),
   PK_ORG               VARCHAR2(32),
   MEMO                 VARCHAR2(500),
   IS_PRESET            CHAR(1),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_RMS_USERGROUP PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_USERGROUP_RLT                                     */
/*==============================================================*/
CREATE TABLE RMS_USERGROUP_RLT 
(
   ID                   CHAR(32)             NOT NULL,
   PK_USER              VARCHAR2(32),
   PK_USERGROUP         VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT AK_KEY_1_RMS_USER UNIQUE (ID)
);

/*==============================================================*/
/* Table: RMS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE RMS_USER_ROLE 
(
   ID                   CHAR(32)             NOT NULL,
   PK_ROLE              VARCHAR2(32),
   PK_USER              VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   AGING_ENDDATE        INTEGER,
   ISPROMPTED           CHAR(1),
   CONSTRAINT PK_RMS_USER_ROLE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_BUTNREGISTER                                       */
/*==============================================================*/
CREATE TABLE ST_BUTNREGISTER 
(
   ID                   CHAR(32)             NOT NULL,
   FUNNAME              VARCHAR2(128),
   FUNCODE              VARCHAR2(50),
   URL                  VARCHAR2(2048),
   PARENTID             VARCHAR2(32),
   ISENABLE             CHAR(1),
   MEMO                 VARCHAR2(500),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_ST_BUTNREGISTER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_MENUREGISTER                                       */
/*==============================================================*/
CREATE TABLE ST_MENUREGISTER 
(
   ID                   CHAR(32)             NOT NULL,
   FUNNAME              VARCHAR2(128),
   FUNCODE              VARCHAR2(50),
   DISPCODE             VARCHAR2(50),
   PARENTID             VARCHAR2(32),
   INNERCODE            VARCHAR2(50),
   FUNTYPE              SMALLINT,
   ISPOWER              CHAR(1),
   URL                  VARCHAR2(2048),
   HELPNAME             VARCHAR2(2048),
   HINTINF              VARCHAR2(256),
   ISENABLE             CHAR(1),
   MEMO                 VARCHAR2(500),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   MENUID               VARCHAR2(32),
   SAFELEVEL            VARCHAR2(32),
   IS_SYSTEM_MENU       CHAR(1),
   PAGEID               VARCHAR2(32),
   ISROOTMENU           CHAR(1),
   ISREPORT             CHAR(1),
   ICONFIELD			VARCHAR2(255),
   CONSTRAINT PK_ST_MENUREGISTER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_PAR_DEF                                            */
/*==============================================================*/
CREATE TABLE ST_PAR_DEF 
(
   ID                   VARCHAR2(32)         NOT NULL,
   PARNAME              VARCHAR2(200),
   PARCODE              VARCHAR2(30),
   GROUPNAME            VARCHAR2(200),
   GROUPCODE            VARCHAR2(30),
   VALUETYPE            SMALLINT,
   VALUERANGE           VARCHAR2(200),
   DEFAULTVALUE         VARCHAR2(100),
   PARVALUE             VARCHAR2(100),
   MEMO                 VARCHAR2(200),
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   CONSTRAINT PK_ST_PAR_DEF PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: BP_IDXDIMREL                                          */
/*==============================================================*/
CREATE TABLE BP_IDXDIMREL 
(
   ID                   CHAR(32)             NOT NULL,
   INDEXID              VARCHAR2(32),
   DIMID                VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   CONSTRAINT PK_BP_IDXDIMREL PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: BP_INDEXINFO                                          */
/*==============================================================*/
CREATE TABLE BP_INDEXINFO 
(
   ID                   CHAR(32)             NOT NULL,
   INDEXCODE            VARCHAR2(32),
   INDEXNAME            VARCHAR2(128),
   INDEXCODENAME        VARCHAR2(32),
   INDEXCOLUMNNAME      VARCHAR2(18),
   INDEXTABLEID         VARCHAR2(50),
   INDEXTABLENAME       VARCHAR2(50),
   STATFREQ             VARCHAR2(10),
   DATASOURCE           VARCHAR2(50),
   COMEDEPART           VARCHAR2(32),
   BUSINESSBORE         CLOB,
   TECHBORE             CLOB,
   PEICISION            INTEGER,
   METERUNIT            VARCHAR2(10),
   ONLYFLEXIABLEQUERY   CHAR(1),
   REMARK               CLOB,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_BP_INDEXINFO PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: BP_INDGROUP_RLT                                       */
/*==============================================================*/
CREATE TABLE BP_INDGROUP_RLT 
(
   ID                   CHAR(32)             NOT NULL,
   PK_INDEX             VARCHAR2(50)         DEFAULT NULL,
   PK_INDEXGROUP        VARCHAR2(50)         DEFAULT NULL,
   CREATEBY             VARCHAR2(32)         DEFAULT NULL,
   CREATETIME           INTEGER              DEFAULT NULL,
   CONSTRAINT PK_BP_INDGROUP_RLT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: BP_INDGROUPS                                          */
/*==============================================================*/
CREATE TABLE BP_INDGROUPS 
(
   PK_FATHERID          VARCHAR2(32)         DEFAULT NULL,
   ID                   CHAR(32)             NOT NULL,
   GROUPNAME            VARCHAR2(128)        DEFAULT NULL,
   GROUPCODE            VARCHAR2(128)        DEFAULT NULL,
   DETAIL               VARCHAR2(255)        DEFAULT NULL,
   INNERCODE            VARCHAR2(50)         DEFAULT NULL,
   CREATEBY             VARCHAR2(32)         DEFAULT NULL,
   CREATETIME           INTEGER              DEFAULT NULL,
   LASTMODIFYBY         VARCHAR2(32)         DEFAULT NULL,
   LASTMODIFYTIME       INTEGER              DEFAULT NULL,
   VERSION              INTEGER              DEFAULT NULL,
   CONSTRAINT PK_BP_INDGROUPS PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DATADIC                                            */
/*==============================================================*/
CREATE TABLE CP_DATADIC 
(
   ID                   CHAR(32)             NOT NULL,
   DIC_NAME             VARCHAR2(128),
   DIC_CODE             VARCHAR2(50),
   DIC_DESC             VARCHAR2(256),
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_CP_DATADIC PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DATADICVAL                                         */
/*==============================================================*/
CREATE TABLE CP_DATADICVAL 
(
   ID                   CHAR(32)             NOT NULL,
   DIMVL_NAME           VARCHAR2(128),
   DIMVL_CODE           VARCHAR2(50),
   DIMVL_DESC           VARCHAR2(256),
   PK_FATHERID          VARCHAR2(32),
   PK_DICID             VARCHAR2(32),
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_CP_DATADICVAL PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DEFDOC                                             */
/*==============================================================*/
CREATE TABLE CP_DEFDOC 
(
   ID                   VARCHAR2(50)         NOT NULL,
   TYPE_ID              VARCHAR2(32),
   DOC_CODE             VARCHAR2(128),
   DOC_NAME             VARCHAR2(128),
   DOC_DESC             VARCHAR2(500),
   IS_PRESET            CHAR(1),
   SORTNUM              INTEGER,
   PK_FATHERID          VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_CP_DEFDOC PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DEFDOCLIST                                         */
/*==============================================================*/
CREATE TABLE CP_DEFDOCLIST 
(
   ID                   CHAR(32)             NOT NULL,
   TYPE_CODE            VARCHAR2(50),
   TYPE_NAME            VARCHAR2(128),
   TYPE_DESC            VARCHAR2(500),
   IS_PRESET            CHAR(1),
   SORTNUM              INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   DEF_TYPE             VARCHAR2(200),
   CONSTRAINT PK_CP_DEFDOCLIST PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_FAVORITES                                          */
/*==============================================================*/
CREATE TABLE CP_FAVORITES 
(
   ID                   CHAR(32)             NOT NULL,
   NAME                 VARCHAR2(128),
   PID                  VARCHAR2(32),
   URL                  VARCHAR2(256),
   FILE_TYPE            SMALLINT,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   CONSTRAINT PK_CP_FAVORITES PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_LOG_CONTENT                                        */
/*==============================================================*/
CREATE TABLE CP_LOG_CONTENT 
(
   ID                   CHAR(32)             NOT NULL,
   LOG_ID               CHAR(32),
   COL_NAME             VARCHAR2(18),
   COL_DESC             VARCHAR2(256),
   NEW_DATA             CLOB,
   OLD_DATA             CLOB,
   CONSTRAINT PK_CP_LOG_CONTENT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_LOG_OP                                             */
/*==============================================================*/
CREATE TABLE CP_LOG_OP 
(
   ID                   CHAR(32)             NOT NULL,
   TABLE_NAME           VARCHAR2(18),
   TABLE_DESC           VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   TYPE                 CHAR(1),
   CONSTRAINT PK_CP_LOG_OP PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_LOGINLOG                                           */
/*==============================================================*/
CREATE TABLE CP_LOGINLOG 
(
   ID                   CHAR(32)             NOT NULL,
   LOGIN_ACCOUNT        VARCHAR2(50),
   IP_ADDR              VARCHAR2(20),
   MAC_ADDR             VARCHAR2(20),
   LOGIN_STATUS         CHAR(1),
   LOGIN_TIME           INTEGER,
   CONSTRAINT PK_CP_LOGINLOG PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_POWER_DIC                                          */
/*==============================================================*/
CREATE TABLE CP_POWER_DIC 
(
   ID                   CHAR(32)             NOT NULL,
   PK_DATASOURCE        VARCHAR2(32),
   PK_DATADICID         VARCHAR2(32),
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   CONSTRAINT PK_CP_POWER_DIC PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_ROLE_DATADIC                                       */
/*==============================================================*/
CREATE TABLE CP_ROLE_DATADIC 
(
   ID                   CHAR(32)             NOT NULL,
   PK_ROLE              VARCHAR2(32)         DEFAULT NULL,
   PK_DIC               VARCHAR2(32)         DEFAULT NULL,
   PK_DICVAL            VARCHAR2(32)         DEFAULT NULL,
   CREATETIME           INTEGER              DEFAULT NULL,
   CREATEBY             VARCHAR2(32)         DEFAULT NULL,
   CONSTRAINT PK_CP_ROLE_DATADIC PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_SYS_CONF_EXT                                       */
/*==============================================================*/
CREATE TABLE CP_SYS_CONF_EXT 
(
   ID                   CHAR(32)             NOT NULL,
   CONF_NAME            VARCHAR2(128),
   CONF_CODE            VARCHAR2(128),
   CONF_VALUE           VARCHAR2(128),
   VALUE_TYPE           SMALLINT,
   CATEGORY_NAME        VARCHAR2(128),
   CATEGORY_CODE        VARCHAR2(128),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT PK_CP_SYS_CONF_EXT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DATASOURCE_REF                                   	*/
/*==============================================================*/
CREATE TABLE CP_DATASOURCE_REF 
(
   ID                 CHAR(32)             NOT NULL,
   PK_DATASOURCE      CHAR(32),
   RES_ID             CHAR(32),
   RES_NAME           VARCHAR2(128),
   MEMO               VARCHAR2(256),
   CONSTRAINT PK_CP_DATASOURCE_REF PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: CP_DATASOURCE_LIBRARY                                 */
/*==============================================================*/
CREATE TABLE CP_DATASOURCE_LIBRARY
(
   ID                   CHAR(32) 			NOT NULL,
   DS_TYPE              VARCHAR2(32),
   DS_VERSION           VARCHAR2(128),
   QUALIFIEDCLASSNAME   VARCHAR2(256),
   JARPATH              VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   VERSION              INTEGER,
   CONSTRAINT CP_DATASOURCE_LIBRARY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PT_USER_DEFAULTPAGE                                   */
/*==============================================================*/
CREATE TABLE PT_USER_DEFAULTPAGE 
(
   ID                   CHAR(32)             NOT NULL,
   PK_USER              CHAR(32),
   PK_PAGE              CHAR(32),
   PK_MENU              CHAR(32),
   CONSTRAINT PK_PT_USER_DEFAULTPAGE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: RMS_HELP                                              */
/*==============================================================*/
CREATE TABLE RMS_HELP 
(
   ID                   CHAR(32)             NOT NULL,
   TITLE                VARCHAR2(128),
   ATTACH_NAME          VARCHAR2(128),
   ATTACH_PATH          VARCHAR2(2048),
   MEMO                 VARCHAR2(256),
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_RMS_HELP PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_CONF                                               */
/*==============================================================*/
CREATE TABLE ST_CONF 
(
   ID                   CHAR(32),
   SYSHOMESTATE         CHAR(1),
   SYSHOMEURL           VARCHAR2(2048),
   REPORTINTS           VARCHAR2(200),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER
);

/*==============================================================*/
/* Table: ST_PAGEREGISTER                                       */
/*==============================================================*/
CREATE TABLE ST_PAGEREGISTER 
(
   ID                   CHAR(32)             NOT NULL,
   FUNNAME              VARCHAR2(128),
   DISPCODE             VARCHAR2(50),
   MENUID               VARCHAR2(32),
   PAGEID               VARCHAR2(32),
   URL                  VARCHAR2(2048),
   MEMO                 VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_ST_PAGEREGISTER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_PARAM                                              */
/*==============================================================*/
CREATE TABLE ST_PARAM 
(
   ID                   VARCHAR2(32)         NOT NULL,
   PARNAME              VARCHAR2(128),
   PARCODE              VARCHAR2(50),
   PARGROUPID           VARCHAR2(50),
   VALUETYPE            SMALLINT,
   VALUERANGE           VARCHAR2(256),
   DEFAULTVALUE         VARCHAR2(128),
   PARVALUE             VARCHAR2(128),
   MEMO                 VARCHAR2(256),
   CREATETIME           INTEGER,
   CREATEBY             VARCHAR2(32),
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   CONSTRAINT PK_ST_PARAM PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_PASSSET                                            */
/*==============================================================*/
CREATE TABLE ST_PASSSET 
(
   ID                   CHAR(32)             NOT NULL,
   MINLEN               INTEGER,
   MAXLEN               INTEGER,
   ISLOWER              CHAR(1),
   ISUPPER              CHAR(1),
   ISNUMBER             CHAR(1),
   ISSPECIAL            CHAR(1),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_ST_PASSSET PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: ST_RESREGISTER                                        */
/*==============================================================*/
CREATE TABLE ST_RESREGISTER 
(
   ID                   CHAR(32)             NOT NULL,
   PARENTTYPE           SMALLINT,
   PARENTID             VARCHAR2(32),
   URL                  VARCHAR2(2048),
   MEMO                 VARCHAR2(500),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_ST_RESREGISTER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TMP_CP_LOG_CONTENT                                    */
/*==============================================================*/
CREATE TABLE TMP_CP_LOG_CONTENT 
(
   ID                   CHAR(32)             NOT NULL,
   LOG_ID               CHAR(32),
   COL_NAME             VARCHAR2(18),
   COL_DESC             VARCHAR2(256),
   NEW_DATA             CLOB,
   OLD_DATA             CLOB,
   CONSTRAINT PK_TMP_CP_LOG_CONTENT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TMP_CP_LOG_OP                                         */
/*==============================================================*/
CREATE TABLE TMP_CP_LOG_OP 
(
   ID                   CHAR(32)             NOT NULL,
   TABLE_NAME           VARCHAR2(18),
   TABLE_DESC           VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   SERVICE_ID           CHAR(32),
   DATA_ID              CHAR(32),
   TYPE                 CHAR(1),
   CONSTRAINT PK_TMP_CP_LOG_OP PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_BUSINESSFORM"                                   */
/*==============================================================*/
CREATE TABLE "FLOW_BUSINESSFORM" 
(
   "ID"                 CHAR(32)             NOT NULL,
   "PK_DEPLOYMENTID"    VARCHAR2(32),
   "FORMID"             VARCHAR2(32),
   "PARAMS"             VARCHAR2(4000),
   "STATUS"             SMALLINT,
   "DOWNLOADURL"        VARCHAR2(2048),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_FLOW_BUSINESSFORM PRIMARY KEY ("ID")
);

/*==============================================================*/
/* Table: "FLOW_FORM_PROCEINST_RLT"                             */
/*==============================================================*/
CREATE TABLE "FLOW_FORM_PROCEINST_RLT" 
(
   "ID"                 CHAR(32)             NOT NULL,
   "PK_BUSINESSFORMID"  VARCHAR2(32),
   "PROCEINSTID"        VARCHAR2(64),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_FLOW_FORM_PROCEINST_RLT PRIMARY KEY ("ID")
);


COMMENT ON TABLE "FLOW_FORM_PROCEINST_RLT" IS
'业务关联流程实例';

COMMENT ON COLUMN "FLOW_FORM_PROCEINST_RLT"."ID" IS
'主键ID';

COMMENT ON COLUMN "FLOW_FORM_PROCEINST_RLT"."PK_BUSINESSFORMID" IS
'流程业务key';

COMMENT ON COLUMN "FLOW_FORM_PROCEINST_RLT"."PROCEINSTID" IS
'流程实例ID';

/*==============================================================*/
/* Table: "FLOW_NOTIFYEVENTDETAIL"                              */
/*==============================================================*/
CREATE TABLE "FLOW_NOTIFYEVENTDETAIL" 
(
   ID                   CHAR(32)             NOT NULL,
   "PK_DEPLOYMENTID"    VARCHAR2(32),
   "PK_NODEINFOID"      VARCHAR2(32),
   "PK_EVENTTYPEID"     VARCHAR2(32),
   "NOTIFYURL"          VARCHAR2(2048),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_NOTIFYEVENTDETAIL PRIMARY KEY (ID)
);

COMMENT ON COLUMN "FLOW_NOTIFYEVENTDETAIL".ID IS
'主键ID';

COMMENT ON COLUMN "FLOW_NOTIFYEVENTDETAIL"."PK_DEPLOYMENTID" IS
'流程部署ID';

COMMENT ON COLUMN "FLOW_NOTIFYEVENTDETAIL"."PK_NODEINFOID" IS
'流程节点ID';

COMMENT ON COLUMN "FLOW_NOTIFYEVENTDETAIL"."PK_EVENTTYPEID" IS
'流程事件类型ID';

COMMENT ON COLUMN "FLOW_NOTIFYEVENTDETAIL"."NOTIFYURL" IS
'通知url';

/*==============================================================*/
/* Table: "FLOW_APPRAUTHORITY"                                  */
/*==============================================================*/
CREATE TABLE "FLOW_APPRAUTHORITY" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   NODEINFOID           VARCHAR2(32),
   APPRTYPE             VARCHAR2(32),
   APPRTYPEID           VARCHAR2(32),
   APPRTYPECODE         VARCHAR2(128),
   APPRTYPENAME         VARCHAR2(128),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_APPRAUTHORITY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_CATEGORY"                                       */
/*==============================================================*/
CREATE TABLE "FLOW_CATEGORY" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   CATEGORYCODE         VARCHAR2(128),
   CATEGORYNAME         VARCHAR2(128),
   MEMO                 VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   INNERCODE            VARCHAR2(50),
   PK_FATHERCODE        VARCHAR2(32),
   CONSTRAINT PK_FLOW_CATEGORY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_DELEGATE"                                       */
/*==============================================================*/
CREATE TABLE "FLOW_DELEGATE" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   STARTTIME            INTEGER,
   ENDTIME              INTEGER,
   USERID               VARCHAR2(32),
   ACCEPTID             VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_DELEGATE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_DELEGATE_DETAIL"                                */
/*==============================================================*/
CREATE TABLE "FLOW_DELEGATE_DETAIL" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   KID                  VARCHAR2(32),
   DEPLOYMENTID         VARCHAR2(64),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_DELEGATE_DETAIL PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_DEPLOYMENT"                                     */
/*==============================================================*/
CREATE TABLE "FLOW_DEPLOYMENT" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   DEPLOYMENTCODE       VARCHAR2(128),
   DEPLOYMENTNAME       VARCHAR2(256),
   DEPLOYID             VARCHAR2(64),
   PROCDEFID            VARCHAR2(64),
   STATE                SMALLINT,
   FORMTYPEID           VARCHAR2(32),
   CATEGORYID           VARCHAR2(32),
   METAINFO             CLOB,
   MEMO                 VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_DEPLOYMENT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_EVENT"                                          */
/*==============================================================*/
CREATE TABLE "FLOW_EVENT" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   EVENTCODE            VARCHAR2(128),
   EVENTNAME            VARCHAR2(128),
   EVENTTYPE            VARCHAR2(32),
   FORMTYPEID           VARCHAR2(32),
   IMPLEMENT            VARCHAR2(256),
   NOTE                 VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_EVENT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_EVENTTYPE"                                      */
/*==============================================================*/
CREATE TABLE "FLOW_EVENTTYPE" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   EVENTTYPECODE        VARCHAR2(128),
   EVENTTYPENAME        VARCHAR2(128),
   EVENTINTERFACE       VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_EVENTTYPE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_FORMTYPE"                                       */
/*==============================================================*/
CREATE TABLE "FLOW_FORMTYPE" 
(
   ID                   CHAR(32)             NOT NULL,
   FORMCODE             VARCHAR2(128),
   FORMNAME             VARCHAR2(128),
   FORMURL              VARCHAR2(256),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   PK_CATEGORYID        VARCHAR2(32),
   CONSTRAINT PK_FLOW_FORMTYPE PRIMARY KEY (ID)
);

COMMENT ON COLUMN "FLOW_FORMTYPE".PK_CATEGORYID IS
'对应流程类型外键';

/*==============================================================*/
/* Table: "FLOW_FORMVARIABLE"                                   */
/*==============================================================*/
CREATE TABLE "FLOW_FORMVARIABLE" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   FORMTYPEID           VARCHAR2(32),
   VARIABLENAME         VARCHAR2(128),
   DISPLAYNAME          VARCHAR2(128),
   MEMO                 VARCHAR2(128),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   "COLUMN_11"          CHAR(10),
   CONSTRAINT PK_FLOW_FORMVARIABLE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_NODEINFO"                                       */
/*==============================================================*/
CREATE TABLE "FLOW_NODEINFO" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   PROCDEFKEY           VARCHAR2(128),
   DEPLOYMENTID         VARCHAR2(32),
   ACTID                VARCHAR2(64),
   ACTNAME              VARCHAR2(128),
   FORMTYPEID           VARCHAR2(32),
   TIMERTASKID          VARCHAR2(32),
   NOTICE               SMALLINT,
   MULTIINSTYPE         VARCHAR2(32),
   MULTIINSVALUE        INTEGER,
   EVENTTYPEID          VARCHAR2(32),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_NODEINFO PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_PROCESSHISTORY"                                 */
/*==============================================================*/
CREATE TABLE "FLOW_PROCESSHISTORY" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   DEPLOYMENTID         VARCHAR2(32),
   PROCESSINSID         VARCHAR2(64),
   ACTID                VARCHAR2(64),
   TASKID               VARCHAR2(64),
   ACTNAME              VARCHAR2(255),
   OPTIONS              VARCHAR2(255),
   USERID               VARCHAR2(32),
   RESULT               SMALLINT,
   FORMID               VARCHAR2(64),
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_PROCESSHISTORY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FLOW_TIMERTASK"                                      */
/*==============================================================*/
CREATE TABLE "FLOW_TIMERTASK" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   DEPLOYMENTID         VARCHAR2(32),
   EXECTYPE             INTEGER,
   FORMTYPEID           VARCHAR2(32),
   ACTID                VARCHAR2(64),
   LENGTH               INTEGER,
   UNIT                 VARCHAR2(10),
   ISWORKDAYS           SMALLINT,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_FLOW_TIMERTASK PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: "FORM_LEAVE"                                          */
/*==============================================================*/
CREATE TABLE "FORM_LEAVE" 
(
   ID                   VARCHAR2(32)         NOT NULL,
   STARTTIME            INTEGER,
   ENDTIME              INTEGER,
   PHONE                VARCHAR2(32),
   LEAVETYPE            VARCHAR2(32),
   REASON               VARCHAR2(256),
   LEAVEDAY             INTEGER,
   CREATEBY             VARCHAR2(32),
   CREATETIME           INTEGER,
   LASTMODIFYTIME       INTEGER,
   LASTMODIFYBY         VARCHAR2(32),
   VERSION              INTEGER,
   CONSTRAINT PK_FORM_LEAVE PRIMARY KEY (ID)
);

COMMENT ON TABLE "FORM_LEAVE" IS
'测试用的单据';

/*==============================================================*/
/* Table: "MAIL_REGISTRY"                                       */
/*==============================================================*/
CREATE TABLE "CP_MAIL_REGISTRY" 
(
   "ID"                 CHAR(32)         	 NOT NULL,
   "APPKEY"             VARCHAR2(32)         NOT NULL,
   "USERNAME"           VARCHAR2(128)        NOT NULL,
   "PASSWORD"           VARCHAR2(128)        NOT NULL,
   "VALIDATE"           CHAR(1)          	 NOT NULL,
   "HOST"               VARCHAR2(20)         NOT NULL,
   "PORT"               VARCHAR2(6)          NOT NULL,
   "FROMADDRESS"        VARCHAR2(100)        NOT NULL,
   "CREATEBY"           VARCHAR2(32),
   "CREATETIME"         INTEGER,
   "LASTMODIFYTIME"     INTEGER,
   "LASTMODIFYBY"       VARCHAR2(32),
   "VERSION"            INTEGER,
   CONSTRAINT PK_MAIL_REGISTRY PRIMARY KEY ("ID")
);

