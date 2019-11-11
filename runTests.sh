#!/bin/bash

docker-compose down
docker-compose up -d --build
mvn clean test
docker-compose down
