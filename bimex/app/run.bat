set JAVA_HOME=c:/dev-tools/java/jdk1.8.0_151
java -version
%JAVA_HOME%\bin\java -DDB_SERVER=47.98.224.133:1433 -DDB=bid -DDB_USER=sa -DDB_PWD=Sa1234 -jar target/app-1.0-SNAPSHOT.jar
