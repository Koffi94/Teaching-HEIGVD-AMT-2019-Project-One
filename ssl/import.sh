#!/bin/sh

sudo keytool -trustcacerts -keystore  /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit -alias payara -import -file ~/Documents/HEIG-VD/Cours_semestre5_2019/AMT/projects/Teaching-HEIGVD-AMT-2019-Project-One/ssl/payara-self-signed.crt
