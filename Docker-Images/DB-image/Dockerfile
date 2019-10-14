# https://devopsheaven.com/sqlite/backup/restore/dump/databases/docker/2017/10/10/sqlite-backup-restore-docker.html
FROM alpine:3.10
RUN apk add --update sqlite
RUN mkdir /db
WORKDIR /db

ENTRYPOINT ["sqlite3"]
CMD ["test.db"]