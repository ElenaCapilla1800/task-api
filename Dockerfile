# Imagen base con Java 23
FROM eclipse-temurin:23-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el jar compilado al contenedor
# El * es para no depender del número de versión exacto
COPY target/*.jar app.jar

# Puerto que expone el contenedor
EXPOSE 8082

# Comando que se ejecuta al arrancar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]