FROM dordoka/tomcat

RUN apt-get update && \
    apt-get -y install maven
ADD restapi.key /var/www/rest.key
RUN chmod 400 /var/www/rest.key
ADD tttp.key /var/www/tttp.key
RUN chmod 400 /var/www/tttp.key
RUN mkdir /root/.ssh
RUN ssh-keyscan bitbucket.org >> ~/.ssh/known_hosts
RUN eval "$(ssh-agent -s)" && ssh-add /var/www/rest.key && git clone git@bitbucket.org:toancauxanh/restapi-common.git /var/www/restapi-common
WORKDIR /var/www/restapi-common
RUN git checkout master
WORKDIR /var/www/restapi-common
RUN mvn install
RUN eval "$(ssh-agent -s)" && ssh-add /var/www/tttp.key && git clone git@bitbucket.org:toancauxanh/tttp-api.git /var/www/tttp-api
WORKDIR /var/www/tttp-api
RUN ls -la .
RUN git checkout develop
WORKDIR /var/www/tttp-api
RUN mvn spring-boot:run

#ADD run.sh /var/www/run.sh
#RUN chmod u+x /var/www/run.sh
#WORKDIR /var/www/