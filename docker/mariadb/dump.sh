!/bin/bash
/usr/bin/mysqld_safe &
sleep 5
mysql -u root -e "CREATE DATABASE tttp"
mysql -u root tttp < /home/mysql/dump/tttp.sql
