# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines
run devmode (autorestart): mvn spring-boot:run

run devmode (autohotswap): MAVEN_OPTS="-XXaltjvm=dcevm -javaagent:$HOME/hotswap-agent.jar=autoHotswap=true" mvn tomcat7:run

run testmode: mvn tomcat7:run


### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact