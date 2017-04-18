sudo docker build --tag=tttp-mariadb:1.0 mariadb/
sudo docker build --tag=tttp-phpadmin:1.0 myphpadmin/
sudo docker build --tag=tttp-server:1.0 server/


sudo docker run -d --name=tttp-mariadb -p 3306:3306 tttp-mariadb:1.0
sudo docker run -d --name=tttp-phpadmin --link tttp-mariadb:db -p 9000:8080 tttp-phpadmin:1.0
sudo docker run -d --name=tttp-server -p 8088:80 --link=tttp-mariadb:db tttp-server:1.0