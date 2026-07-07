FROM tomcat:11.0-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

WORKDIR /LegacyBoxOfficeProviderSOAP
COPY target/LegacyBoxOfficeProviderSOAP.war /usr/local/tomcat/webapps/LegacyBoxOfficeProviderSOAP.war

EXPOSE 8080
CMD ["catalina.sh", "run"]