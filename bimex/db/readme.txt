使用说明

所有的数据库结构和基础变更都应该通过此工具管理。
* 结构变更、创建索引等使用 DDL 文件，通过 createDDLFile.bat 创建
* 基础数据变更使用 DML 文件，通过 createDMLFile.bat 创建

每条语句使用分号结束，不要用 GO。

应该使用这里的工具更新数据库，而不是直接连接数据库去更新。在使用工具之前，需要设置相关参数，方法有 2 个：
1. 设置 windows 环境变量，增加下面这三个值：
        DB_HOST: 数据库服务器的 IP
        DB_USR: 数据库登录用户名
        DB_PWD: 数据库登录密码
2. 直接编辑 pom.xml, 替换 <properties 下的三个 DB 变量，如将 ${env.DB_HOST} 替换成 127.0.0.1。注意修改后的 pom 不要上传到版本库

设置好参数后，可以使用命令 mvn clean dbdeploy:update 更新数据库

 也可以不设置环境变量直接运行，（windows下）编写 run.bat, 内容如下：
-----------------------
set DB_HOST=127.0.0.1
set DB_USR=sa
set DB_PWD=xxxx

update-db.bat
------------------------

生成上线脚本

