#!/bin/sh

sudo keytool -trustcacerts -keystore  /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit -alias payara -import -file ~/Documents/HEIG/S5/2_AMT/Projets/Project-One/ssl/payara-self-signed.crt
