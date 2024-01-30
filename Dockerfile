FROM maven:3.8.1-openjdk-8
MAINTAINER valentin.cassino
#Exponemos el puerto 8080
EXPOSE 8080
COPY target/mercantiltest-0.0.1-MERCANTIL.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]