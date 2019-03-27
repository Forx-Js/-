SQL="INSERT INTO dbo.SysUserPass (UserID, Password, Salt, LastOperatTime, IfForceUpdate, UserName) VALUES ('{}', 'bd6eee7f8b39b8edfd6cce749d08de6aaae2a715da5eb854e732bae3bf94ef86', '321', NULL, NULL, 'liuwq');"

while read line
do
    echo $SQL | sed "s/{}/$line/"

done < 2.txt
