FROM tomcat:9.0.58-jre11-openjdk
RUN apt-get update
RUN apt-get install -y git libshout-dev gcc maven
ADD libshout/libshout-java.so /usr/local/tomcat/target/libshout-java.so

COPY ./audio /audio
COPY ./covers /covers
COPY build/libs/online-radio-api.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]
