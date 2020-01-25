FROM tomcat:8
COPY build/libs/vabanque-1.0.0.war /usr/local/tomcat/webapps/docker-springboot.war
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Dspring.profiles.active=docker -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/docker-springboot.war" ]
