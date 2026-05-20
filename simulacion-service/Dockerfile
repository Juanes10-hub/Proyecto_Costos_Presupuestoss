# ════════════════════════════════════════════════════════════════════
# ETAPA 1 — Compilar la biblioteca dinámica C
# ════════════════════════════════════════════════════════════════════
FROM eclipse-temurin:21-jdk AS native-builder

RUN apt-get update && \
    apt-get install -y gcc binutils && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /native

COPY native/simulacion.h .
COPY native/simulacion.c .

RUN gcc -shared -fPIC -O2 -o libsimulacion.so simulacion.c

RUN echo "=== Tabla de símbolos de libsimulacion.so ===" && \
    nm -D libsimulacion.so | grep " T "


# ════════════════════════════════════════════════════════════════════
# ETAPA 2 — Compilar el microservicio Spring Boot
# ════════════════════════════════════════════════════════════════════
FROM maven:3.9-eclipse-temurin-21 AS java-builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q


# ════════════════════════════════════════════════════════════════════
# ETAPA 3 — Imagen de producción
# ════════════════════════════════════════════════════════════════════
FROM eclipse-temurin:21-jre

COPY --from=native-builder /native/libsimulacion.so /usr/local/lib/libsimulacion.so

RUN ldconfig

COPY --from=java-builder /app/target/simulacion-service-*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", \
            "-Djna.library.path=/usr/local/lib", \
            "-jar", "/app/app.jar"]
