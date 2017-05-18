#Build docker (create images)

sudo docker build --tag=tttp-mariadb:1.0 mariadb/

sudo docker build --tag=tttp-phpadmin:1.0 myphpadmin/

sudo docker build --tag=tttp-server:1.0 server/

sudo docker build --tag=tttp-nginx:1.0 nginx/

#Run docker (run containers)

# Run Mariadb server

1. sudo docker run -d --name=tttp-mariadb -v /home/tttpdata/mariadb:/var/lib/mysql -p 3306:3306 tttp-mariadb:1.0

2. sudo docker exec -it tttp-mariadb bash

3. mysql -u root < /home/mysql/dump/tttp.sql

# Run Phpmyadmin

sudo docker run -d --name=tttp-phpadmin --link tttp-mariadb:tttpdb -p 9000:80 tttp-phpadmin:1.0

# Run Nginx

*Open the port on which docker daemon listens so the firewall does not block it

firewall-cmd --permanent --zone=trusted --add-interface=docker0

firewall-cmd --permanent --zone=trusted --add-port=4243/tcp


sudo docker run -d --name=tttp-nginx -v /home/tttpdata/file:/usr/share/nginx/media -p 8089:80 tttp-nginx:1.0

# Run api server

sudo docker run --name=tttp-server -v /home/tttpdata/deploy:/usr/local/tomcat/webapps --link=tttp-mariadb:tttpdb -p 8088:8080 tttp-server:1.0

## Deploy package

cp tttp-api-1.0.0.war /home/tttpdata/deploy/ROOT.war

3. sudo docker exec -it tttp-server bash

#Start and stop (follow order)

sudo docker stop tttp-server

sudo docker stop tttp-mariadb

sudo docker stop tttp-phpadmin

sudo docker start tttp-mariadb

sudo docker start tttp-phpadmin

sudo docker start tttp-server