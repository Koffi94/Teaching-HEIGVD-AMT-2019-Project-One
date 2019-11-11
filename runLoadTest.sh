#!/bin/sh

docker stop projectone_payara_1
docker rm projectone_payara_1
mvn clean install -DskipTests
cp target/project-one.war Docker-Images/Payara-image/
docker-compose up -d --build payara
sleep 30
docker exec projectone_payara_1 /opt/payara/appserver/bin/asadmin --user admin --passwordfile /opt/payara/passwordFile deploy /opt/payara/scripts/project-one.war
google-chrome http://localhost:8080/project-one/login
