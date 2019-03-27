export JAVA_HOME=/c/dev-tools/java/jdk1.8.0_151
export JAVA=$JAVA_HOME/bin/java 
$JAVA -version
$JAVA -DDB_SERVER=47.98.224.133:1433 -DDB=bid -DDB_USER=sa -DDB_PWD=Sa1234 -jar target/app-1.0-SNAPSHOT.jar
