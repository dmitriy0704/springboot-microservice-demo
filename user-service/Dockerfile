# Стадия 1: распаковка слоёв (builder)
FROM eclipse-temurin:21-jre-jammy AS builder

WORKDIR /extracted

# Копируем fat-jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Распаковываем в слои (layertools — встроено в Spring Boot)
RUN java -Djarmode=layertools -jar app.jar extract

# Стадия 2: runtime (очень лёгкий образ)
FROM eclipse-temurin:21-jre-jammy

# Безопасность: non-root пользователь
RUN addgroup --system appgroup && \
    adduser --system --ingroup appgroup --no-create-home appuser

WORKDIR /app

# Копируем слои по порядку (от редко меняющихся к часто меняющимся)
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

# Права
RUN chown -R appuser:appgroup /app
USER appuser

# Запуск с оптимизацией под контейнер
ENTRYPOINT ["java", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseParallelGC", \
    "-XX:TieredStopAtLevel=1", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "org.springframework.boot.loader.launch.JarLauncher"]