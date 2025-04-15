# Usamos una imagen con Java 17 y Maven preinstalados
FROM maven:3.9.6-eclipse-temurin-17 as builder

# Creamos directorio de trabajo
WORKDIR /app

# Copiamos todos los archivos del proyecto al contenedor
COPY . .

# Ejecutamos el build con Maven
RUN ./mvnw clean package -DskipTests

# Ahora, usamos una imagen más liviana para correr el JAR
FROM eclipse-temurin:17-jre

# Directorio donde va a correr el contenedor
WORKDIR /app

# Copiamos el JAR desde el build anterior
COPY --from=builder /app/target/app-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para iniciar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
