FROM mariadb:10.1.21

ENV MYSQL_ALLOW_EMPTY_PASSWORD true
RUN mkdir /home/mysql
RUN mkdir /home/mysql/dump
#ADD dump.sh /home/mysql/dump/dump.sh
#RUN chmod u+x /home/mysql/dump/dump.sh
ADD tttp.sql.gz /home/mysql/dump/tttp.sql.gz
#RUN /home/mysql/dump/dump.sh