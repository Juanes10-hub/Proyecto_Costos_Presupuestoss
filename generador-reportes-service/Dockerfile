# Stage 1: Compilar C
FROM eclipse-temurin:21-jdk AS native-builder
RUN apt-get update && \
    apt-get install -y gcc binutils && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /native
COPY native/generador_reportes.h .
COPY native/generador_reportes.c .

RUN gcc -shared -fPIC -O2 -o libgenerador_reportes.so generador_reportes.c

RUN echo "=== Tabla de símbolos ===" && \
    nm -D libgenerador_reportes.so | grep " T "

# Stage 2: Compilar Java
FROM maven:3.9-eclipse-temurin-21 AS java-builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src/ src/
RUN mvn clean package -DskipTests -q

# Stage 3: Runtime
FROM eclipse-temurin:21-jre
COPY --from=native-builder /native/libgenerador_reportes.so /usr/local/lib/

RUN ldconfig

COPY --from=java-builder /app/target/generador-reportes-service-*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djna.library.path=/usr/local/lib", "-jar", "/app/app.jar"]
