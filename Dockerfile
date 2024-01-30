# Usa la imagen oficial de OpenJDK con Maven
FROM maven:3.8.1-openjdk-8
COPY target/mercantiltest-0.0.1-MERCANTIL.jar mercantiltest.jar
ENTRYPOINT ["java", "-jar", "mercantiltest.jar"]

# Expone el puerto 8080 para la aplicación Spring Boot (ajusta según sea necesario)
EXPOSE 8080
