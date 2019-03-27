-- 表单的配置信息
CREATE TABLE SysForm (
  FormID       VARCHAR(32) NOT NULL PRIMARY KEY,
  ProcessID    VARCHAR(16) NOT NULL, -- 流程编号
  NodeID       VARCHAR(16) NOT NULL, -- 节点编号
  CreateTime   DATETIME    NULL,
  CreateUserID VARCHAR(16) NULL,
  UpdateTime   DATETIME    NULL,
  UpdateUserID VARCHAR(16) NULL,
  IsDeleted    BIT         NOT NULL,
  FormJsonID   VARCHAR(32) NOT NULL
);

CREATE INDEX IX_SysForm_1
  ON SysForm (ProcessID, NodeID);

-- 表单描述 JSON
CREATE TABLE SysFormJson (
  FormJsonID   VARCHAR(32) NOT NULL PRIMARY KEY,
  FormID       VARCHAR(32) NOT NULL,
  FormJson     NVARCHAR(MAX),
  CreateTime   DATETIME    NULL,
  CreateUserID VARCHAR(16) NULL
);

-- 表单字段信息
CREATE TABLE SysFormField (
  FormFieldID      VARCHAR(32)     NOT NULL PRIMARY KEY,
  FormID           VARCHAR(32)     NOT NULL,
  FieldName        VARCHAR(100)    NOT NULL,
  FieldType        VARCHAR(30)     NULL,
  DataFuncName     VARCHAR(100)    NULL,
  FieldCaption     VARCHAR(100)    NOT NULL,
  DataOrder        INT DEFAULT (0) NOT NULL,
  IfTableColumn    INT DEFAULT (0) NOT NULL,
  TableColumnOrder INT             NULL
);

CREATE UNIQUE INDEX ix_SysFormField_1
  ON SysFormField (FormID, FieldName);

-- 表单字段授权
CREATE TABLE SysFormFieldPermission (
  FormID    VARCHAR(32)  NOT NULL,
  FieldName VARCHAR(100) NOT NULL,
  RoleID    VARCHAR(32)  NOT NULL,
  CONSTRAINT PK_SysFormFieldPermission PRIMARY KEY (RoleID, FormID, FieldName)
);

CREATE INDEX ix_SysFormFieldPermission_1
  ON SysFormFieldPermission (FormID, RoleID);

-- 运行时保存的表单数据信息
CREATE TABLE RtFormData (
  RuntimeFormDataID VARCHAR(50)   NOT NULL PRIMARY KEY,
  FormConfigID      VARCHAR(50)   NOT NULL,
  RuntimeID         VARCHAR(16)   NOT NULL,
  ProcessID         VARCHAR(16)   NOT NULL,
  NodeID            VARCHAR(16)   NULL,
  CreateTime        DATETIME      NOT NULL,
  CreateUserID      VARCHAR(16)   NOT NULL,
  UpdateTime        DATETIME      NULL,
  UpdateUserID      VARCHAR(16)   NULL,
  DataJson          NVARCHAR(MAX) NOT NULL
);
