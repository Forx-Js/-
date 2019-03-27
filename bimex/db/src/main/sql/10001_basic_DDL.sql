CREATE TABLE SysUserInfo
(
  UserID        VARCHAR(32)       NOT NULL,
  UserName      VARCHAR(50)       NULL,
  Password      VARCHAR(50)       NULL,
  StaffID       VARCHAR(30)       NULL,
  Name          NVARCHAR(50)      NOT NULL,
  Sex           VARCHAR(10)       NULL,
  Nation        NVARCHAR(50)      NULL,
  BirthDay      DATETIME          NULL,
  NativePlace   NVARCHAR(100)     NULL,
  IDCard        VARCHAR(50)       NULL,
  PhoneNum      VARCHAR(100)      NULL,
  TelNum        VARCHAR(100)      NULL,
  Address       NVARCHAR(500)     NULL,
  Status        TINYINT           NULL,
  Type          TINYINT           NULL,
  WorkTypeCode  VARCHAR(32)       NULL,
  WorkType      NVARCHAR(50)      NULL,
  HeadURL       VARCHAR(200)      NULL,
  PrjID         VARCHAR(32)       NULL,
  IfDelete      BIT DEFAULT ((0)) NULL,
  OperUserID    VARCHAR(32)       NULL,
  domainAccount VARCHAR(1)        NULL,
  OperTime      DATETIME          NULL,
  CONSTRAINT Pk_SysUserInfo PRIMARY KEY (UserID)
);


CREATE UNIQUE INDEX ix_SysUserInfo_1
  ON SysUserInfo (UserName);


CREATE TABLE SysUserPass
(
  UserID        VARCHAR(32)  NOT NULL,
  Password      VARCHAR(100) NOT NULL,
  Salt          VARCHAR(50)  NULL,
  LastOperTime  DATETIME     NULL,
  IfForceUpdate VARCHAR(50)  NULL,
  UserName      VARCHAR(50)  NULL,
  CONSTRAINT PK_SysUserPass PRIMARY KEY (UserID)
);


CREATE TABLE SysRole
(
  RoleID   VARCHAR(32)    NOT NULL,
  RoleName NVARCHAR(50)   NULL,
  PID      VARCHAR(32)    NULL,
  Type     TINYINT        NULL,
  Remark   NVARCHAR(1000) NULL,
  CONSTRAINT PK_SYSROLE PRIMARY KEY NONCLUSTERED (RoleID)
);


CREATE TABLE SysParam
(
  ParamId   VARCHAR(100)  NOT NULL,
  ParamName VARCHAR(100)  NOT NULL,
  Value     NVARCHAR(500) NOT NULL,
  ModuleId  VARCHAR(20)   NULL,
  Notes     NVARCHAR(200) NULL,
  CONSTRAINT PK_SysParam PRIMARY KEY (ParamId)
);

CREATE INDEX ix_SysParam_1
  ON SysParam (ParamName);


CREATE TABLE SysUserRoleInfo
(
  ID         NUMERIC(18) IDENTITY NOT NULL,
  UserID     VARCHAR(32)          NULL,
  RoleID     VARCHAR(32)          NULL,
  PositionID VARCHAR(32)          NULL,
  CONSTRAINT PK_SYSUSERROLEINFO PRIMARY KEY NONCLUSTERED (ID)
);

CREATE INDEX ix_SysUserRoleInfo_1
  ON SysUserRoleInfo (UserID);

CREATE INDEX ix_SysUserRoleInfo_2
  ON SysUserRoleInfo (RoleID, UserID);

CREATE TABLE SysUserSession
(
  Token         VARCHAR(256)  NOT NULL,
  UserID        VARCHAR(32)   NULL,
  LoginTime     DATETIME      NULL,
  LoginIP       VARCHAR(50)   NULL,
  ImeiNo        VARCHAR(50)   NULL,
  SessionString VARCHAR(4096) NULL,
  RefreshTime   DATETIME      NULL,
  LogoutTime    DATETIME      NULL,
  IfActive      INT           NULL,
  TerminalType  VARCHAR(20)   NULL,
  CONSTRAINT PK_SysUserSession PRIMARY KEY (Token)
);


CREATE TABLE UploadFileInfo
(
  FileKey    VARCHAR(50)   NOT NULL,
  FileType   VARCHAR(50)   NULL,
  FilePath   VARCHAR(300)  NOT NULL,
  CreateTime DATETIME      NOT NULL,
  UserID     VARCHAR(50)   NULL,
  IfValid    BIT           NULL,
  ThumbPath  VARCHAR(300)  NULL,
  FileName   NVARCHAR(200) NULL,
  RealName   NVARCHAR(200) NULL,
  FileSize   NVARCHAR(200) NULL,
  CONSTRAINT PK_UploadFileInfo PRIMARY KEY (FileKey)
);


CREATE TABLE SysOrganizationInfo
(
  DepID                VARCHAR(16)    NOT NULL,
  DepCode              VARCHAR(16)    NULL,
  DepName              NVARCHAR(100)  NOT NULL,
  IsComp               BIT            NULL,
  PDepID               VARCHAR(16)    NULL,
  BelongAreaCode       VARCHAR(16)    NULL,
  BelongArea           NVARCHAR(50)   NULL,
  OrganizationTypeCode VARCHAR(16)    NULL,
  OrganizationType     NVARCHAR(50)   NULL,
  Remark               NVARCHAR(1000) NULL,
  FullDepName          NVARCHAR(200)  NULL,
  DataOrder            INT            NULL,
  CONSTRAINT PK_SYSORGANIZATIONINFO PRIMARY KEY NONCLUSTERED (DepID)
);

CREATE INDEX ix_SysOrganizationInfo_1
  ON SysOrganizationInfo (DepCode);


CREATE TABLE SysUserOrgRela
(
  INum   VARCHAR(16) NOT NULL,
  UserID VARCHAR(16) NULL,
  DepID  VARCHAR(16) NULL,
  CONSTRAINT PK_SYSUSERORGRELA PRIMARY KEY (INum)
);

CREATE UNIQUE INDEX ix_SysUserOrgRela_1
  ON SysUserOrgRela (UserID, DepID);
CREATE UNIQUE INDEX ix_SysUserOrgRela_2
  ON SysUserOrgRela (DepID, UserID);


CREATE TABLE SysDicInfo
(
  DicID    VARCHAR(32)       NOT NULL,
  DicName  NVARCHAR(100)     NULL,
  Editable INT DEFAULT ((0)) NULL,
  Remark   NVARCHAR(1000)    NULL,
  SortNum  INT               NULL,
  CONSTRAINT PK_SysDicInfo PRIMARY KEY (DicID)
);


CREATE TABLE SysDicContent
(
  ID      VARCHAR(32)    NOT NULL,
  CNName  NVARCHAR(100)  NULL,
  ENName  VARCHAR(100)   NULL,
  PID     VARCHAR(32)    NULL,
  DicCode NVARCHAR(100)  NOT NULL,
  SortNum INT            NULL,
  Remark  NVARCHAR(1000) NULL,
  CONSTRAINT PK_SysDicContent PRIMARY KEY (ID, DicCode)
);

CREATE INDEX ix_SysDicContent_1
  ON SysDicContent (DicCode);

CREATE TABLE PubSerialNo
(
  SerialName   VARCHAR(50) NOT NULL,
  CurrentValue INT         NULL,
  ValueLength  INT         NULL,
  Prefix       VARCHAR(20) NULL,
  Suffix       VARCHAR(20) NULL,
  Ver          INT         NULL,
  CONSTRAINT PK_PubSerialNo PRIMARY KEY (SerialName)
);


CREATE TABLE changelog
(
  change_number BIGINT       NOT NULL,
  complete_dt   DATETIME     NOT NULL,
  applied_by    VARCHAR(100) NOT NULL,
  description   VARCHAR(500) NOT NULL,
  CONSTRAINT Pkchangelog PRIMARY KEY (change_number)
);

