mvn clean install

cd app
mvn clean package

cd ../gateway
mvn clean package

cd ../admin
mvn clean package

cd ../job-server
mvn clean package
