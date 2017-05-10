# Thanh Tra - API

## Setup development environment

* [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.0+](https://maven.apache.org/download.cgi)
* [Eclipse for java EE IDE](https://www.eclipse.org/downloads/)
* [Docker](https://docs.docker.com/engine/getstarted/step_one/)

### Build Docker Images
Make sure you are in `docke` directory. Do following command for building docker images

1\. MariaDB
```
sudo docker build --tag=tttp-server:1.0 server/
```

### Running and stopping the container images

1\. Run MariaDB container first

```
sudo docker run -d --name=tttp-mariadb -p 4306:3306 tttp-mariadb:1.0
```

Docker allows us to restart a container with a single command:

```
docker restart tttp-mariadb

docker start tttp-mariadb

docker stop tttp-mariadb
```

To destroy a container, perhaps because the image does not suit our needs, we can stop it and then run
```
docker rm tttp-mariadb
```

To access the container via Bash, we will run this command:

```
docker exec -it tttp-mariadb bash
```

To connect to MariaDB from outside (HeidiSQl/MySql Workbench) we use port which define in the docker run command, here is **4306** (*-p 4306:3306*)

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

	- run devmode (autorestart): mvn spring-boot:run

	- run devmode (autohotswap): MAVEN_OPTS="-XXaltjvm=dcevm -javaagent:$HOME/hotswap-agent.jar=autoHotswap=true" mvn tomcat7:run

	- run testmode: mvn tomcat7:run

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact