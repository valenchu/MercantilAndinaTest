# Usa la imagen oficial de OpenJDK con Maven
FROM maven:3.8.1-openjdk-8

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo pom.xml y otros archivos necesarios
COPY pom.xml .

# Copia los archivos fuente a la imagen
COPY src ./src

# Empaqueta la aplicación usando Maven
RUN mvn clean package

# Copia el JAR ejecutable a la imagen
COPY target/mercantiltest-0.0.1-MERCANTIL.jar .

# Expone el puerto 8080 para la aplicación Spring Boot (ajusta según sea necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "mercantiltest-0.0.1-MERCANTIL.jar"]
