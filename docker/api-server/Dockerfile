FROM dordoka/tomcat

RUN apt-get update && \
    apt-get -y install maven

ADD deployment.key /var/www/deployment.key
RUN chmod 400 /var/www/deployment.key
RUN mkdir /root/.ssh
RUN ssh-keyscan bitbucket.org >> ~/.ssh/known_hosts
RUN eval "$(ssh-agent -s)" && ssh-add /var/www/deployment.key && git clone git@bitbucket.org:toancauxanh/tttp-api.git /var/www/tttp-api
WORKDIR /var/www/tttp-api
RUN ls -la .
RUN git checkout develop
WORKDIR /var/www/tttp-api
RUN mvn spring-boot:run

#ADD run.sh /var/www/run.sh
#RUN chmod u+x /var/www/run.sh
#WORKDIR /var/www/