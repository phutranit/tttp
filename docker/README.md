#Build docker (create images)

sudo docker build --tag=tttp-mariadb:1.0 mariadb/

sudo docker build --tag=tttp-phpadmin:1.0 myphpadmin/

sudo docker build --tag=tttp-server:1.0 server/

sudo docker build --tag=tttp-nginx:1.0 nginx/

#Run docker (run containers)

# Run Mariadb server

1. sudo docker run -d --name=tttp-mariadb -v /home/tttpdata/mariadb:/var/lib/mysql -p 3306:3306 tttp-mariadb:1.0

2. sudo docker exec -ti tttp-mariadb bash

3. mysql -u root < /home/mysql/dump/tttp.sql

# Run Phpmyadmin

sudo docker run -d --name=tttp-phpadmin --link tttp-mariadb:tttpdb -p 9000:80 tttp-phpadmin:1.0

# Run Nginx

sudo docker run -d --name=tttp-nginx -v /home/tttpdata/file:/var/www -p 8089:80 tttp-nginx:1.0

# Run api server

1. sudo docker run -d --name=tttp-server -p 8080:8080 -p 8009:8009 --link=tttp-mariadb:tttpdb tttp-server:1.0

2. sudo docker exec -ti tttp-server bash

3. mvn spring-boot:run

#Start and stop (follow order)

sudo docker stop tttp-server

sudo docker stop tttp-mariadb

sudo docker stop tttp-phpadmin

sudo docker start tttp-mariadb

sudo docker start tttp-phpadmin

sudo docker start tttp-server