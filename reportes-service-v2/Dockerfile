# ETAPA 1: Compilar biblioteca C
FROM eclipse-temurin:21-jdk AS native-builder

RUN apt-get update && apt-get install -y gcc && rm -rf /var/lib/apt/lists/*

WORKDIR /native
COPY native/reportes.h .
COPY native/reportes.c .

RUN gcc -shared -fPIC -O2 -o libreportes.so reportes.c

# ETAPA 2: Compilar microservicio
FROM maven:3.9-eclipse-temurin-21 AS java-builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

# ETAPA 3: Imagen final
FROM eclipse-temurin:21-jre

COPY --from=native-builder /native/libreportes.so /usr/local/lib/libreportes.so
RUN ldconfig

COPY --from=java-builder /app/target/reportes-service-*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djna.library.path=/usr/local/lib", "-jar", "/app/app.jar"]
