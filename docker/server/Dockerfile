FROM openjdk:8

RUN apt-get update && \
    apt-get -y install maven
ADD rest.key /var/www/rest.key
RUN chmod 400 /var/www/rest.key
ADD tttp.key /var/www/tttp.key
RUN chmod 400 /var/www/tttp.key
RUN mkdir /root/.ssh
RUN ssh-keyscan bitbucket.org >> ~/.ssh/known_hosts
RUN eval "$(ssh-agent -s)" && ssh-add /var/www/rest.key && git clone https://gg_trandtb:friend1424@bitbucket.org/toancauxanh/restapi-common.git /var/www/restapi-common
WORKDIR /var/www/restapi-common
RUN git checkout master
RUN git pull origin master
RUN ls -la
RUN mvn clean install
RUN eval "$(ssh-agent -s)" && ssh-add /var/www/tttp.key && git clone https://gg_trandtb:friend1424@bitbucket.org/toancauxanh/tttp-api.git /var/www/tttp-api
WORKDIR /var/www/tttp-api
RUN git checkout develop
RUN git pull origin develop
RUN mvn dependency:resolve
RUN mvn spring-boot:run
#RUN ls ./target/
#RUN sh -c "cp /var/www/tttp-api/target/tttp-api-1.0.0.war /opt/tomcat/webapps/ROOT.war"
#ADD run.sh /var/www/run.sh
#RUN chmod u+x /var/www/run.sh
WORKDIR /var/www/tttp-api