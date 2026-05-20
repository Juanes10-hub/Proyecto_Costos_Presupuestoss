# ════════════════════════════════════════════════════════════════════
# ETAPA 1 — Compilar la biblioteca dinámica C
# Usa la imagen del JDK para tener GCC disponible via apt
# ════════════════════════════════════════════════════════════════════
FROM eclipse-temurin:21-jdk AS native-builder

# Instalar GCC y binutils (nm para verificar tabla de símbolos)
RUN apt-get update && \
    apt-get install -y gcc binutils && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /native

# Copiar los fuentes C
COPY native/estimacion.h .
COPY native/estimacion.c .

# Compilar la biblioteca dinámica
# -shared : produce un .so (shared object)
# -fPIC   : Position Independent Code (obligatorio para .so)
# -O2     : optimización nivel 2
RUN gcc -shared -fPIC -O2 -o libestimacion.so estimacion.c

# Verificar que la biblioteca se compiló correctamente
RUN echo "=== Tabla de símbolos de libestimacion.so ===" && \
    nm -D libestimacion.so | grep " T "


# ════════════════════════════════════════════════════════════════════
# ETAPA 2 — Compilar el microservicio Spring Boot
# ════════════════════════════════════════════════════════════════════
FROM maven:3.9-eclipse-temurin-21 AS java-builder

WORKDIR /app

# Descargar dependencias primero (capa cacheada si pom.xml no cambia)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Compilar el proyecto
COPY src ./src
RUN mvn clean package -DskipTests -q


# ════════════════════════════════════════════════════════════════════
# ETAPA 3 — Imagen de producción (solo JRE + biblioteca + JAR)
# ════════════════════════════════════════════════════════════════════
FROM eclipse-temurin:21-jre

# Copiar la biblioteca nativa desde la etapa 1
COPY --from=native-builder /native/libestimacion.so /usr/local/lib/libestimacion.so

# Actualizar caché del enlazador dinámico (ldconfig lee /usr/local/lib)
RUN ldconfig

# Copiar el JAR desde la etapa 2
COPY --from=java-builder /app/target/estimacion-service-*.jar /app/app.jar

EXPOSE 8080

# -Djna.library.path: le dice a JNA dónde buscar libestimacion.so
ENTRYPOINT ["java", \
            "-Djna.library.path=/usr/local/lib", \
            "-jar", "/app/app.jar"]
