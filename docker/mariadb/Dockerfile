FROM mariadb:10.1.21

ENV MYSQL_ROOT_PASSWORD 123456
ENV MYSQL_DATABASE tttp
ENV MYSQL_USER tttp
ENV MYSQL_PASSWORD 123456

COPY dump.sql.gz /docker-entrypoint-initdb.d/dump.sql.gz