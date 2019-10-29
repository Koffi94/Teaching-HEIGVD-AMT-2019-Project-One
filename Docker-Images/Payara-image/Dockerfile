# https://hub.docker.com/r/payara/server-web
FROM payara/server-full:latest
LABEL maintainer="Nathanael Mizuani <nathanael.mizutani@heig-vd.ch>, Olivier Koffi <olivier.koffi@heg-vd.ch>"

ADD drivers/mysql-connector-java-5.1.48-bin.jar ./appserver/glassfish/lib/

RUN echo "AS_ADMIN_PASSWORD=admin" > /opt/payara/passwordFile	&& \
    ./appserver/bin/asadmin start-domain production && \
    ./appserver/bin/asadmin --interactive=false --user admin --passwordfile /opt/payara/passwordFile create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=adminpw:DatabaseName=myDB:ServerName=db:port=3306 pool_myDB && \
    ./appserver/bin/asadmin --interactive=false --user admin --passwordfile /opt/payara/passwordFile create-jdbc-resource --connectionpoolid pool_myDB jdbc/myDB

COPY project-one.war $DEPLOY_DIR
