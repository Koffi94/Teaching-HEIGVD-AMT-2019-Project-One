#!/bin/bash

mvn clean install
mv target/project-one.war Docker-Images/Payara-image/
docker-compose up --build
