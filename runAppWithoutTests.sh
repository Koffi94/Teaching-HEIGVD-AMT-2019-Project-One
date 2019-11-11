#!/bin/sh

docker-compose down
mvn clean install -DskipTests
docker-compose up -d --build
sleep 30
docker exec projectone_payara_1 /opt/payara/appserver/bin/asadmin --user admin --passwordfile /opt/payara/passwordFile deploy /opt/payara/scripts/project-one.war
google-chrome http://localhost:8080/project-one/login
