# Thanh Tra - API

## Setup development environment

###Requirement

* [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.0+](https://maven.apache.org/download.cgi)
* [Eclipse for java EE IDE](https://www.eclipse.org/downloads/)
* [Docker](https://docs.docker.com/engine/getstarted/step_one/)

### Build Docker Images
Make sure you are in `dockerize` directory. Do following command for building docker images

1\. MariaDB

Build comtainer image
```sh
sudo docker build --tag=javacore-user-mariadb:1.0 mariadb/
```

2\. API Server
```sh## Setup development environment

###Requirement

* [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.0+](https://maven.apache.org/download.cgi)
* [Eclipse for java EE IDE](https://www.eclipse.org/downloads/)
* [Docker](https://docs.docker.com/engine/getstarted/step_one/)

### Build Docker Images
Make sure you are in `dockerize` directory. Do following command for building docker images

1\. MariaDB

sudo docker build --tag=javacore-user-server:1.0 server/
```

### Running and stopping the container images
1\. Run MariaDB container first
```sh
sudo docker run -d --name=javacore-user-mariadb -p 4306:3306 javacore-user-mariadb:1.0
```
Docker allows us to restart a container with a single command:

```
#!sh
docker restart javacore-user-mariadb

docker start javacore-user-mariadb

docker stopt javacore-user-mariadb
```

To destroy a container, perhaps because the image does not suit our needs, we can stop it and then run
```
#!sh
docker rm javacore-user-mariadb
```

To access the container via Bash, we will run this command:
```
#!sh
docker exec -it javacore-user-mariadb bash
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