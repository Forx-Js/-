INSERT INTO dbo.SysParam (ParamId, ParamName, Value, ModuleId, Notes)
VALUES ('loginMode', 'loginMode', 'ldap', '', '登录模式');

INSERT INTO dbo.SysParam (ParamId, ParamName, Value, ModuleId, Notes)
VALUES ('ldapUrl', 'ldapUrl', 'ldap://localhost:359', '', 'ldap服务器url');

INSERT INTO dbo.SysParam (ParamId, ParamName, Value, ModuleId, Notes)
VALUES ('ldapDomain', 'ldapDomain', 'a-b-c-d', '', 'ldap登录的domain');

