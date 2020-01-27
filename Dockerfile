FROM tomcat:8.5.50-jdk8-openjdk
COPY build/libs/vabanque.war /usr/local/tomcat/webapps/vabanque.war
EXPOSE 8080
