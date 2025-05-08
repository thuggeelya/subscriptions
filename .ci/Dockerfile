FROM gradle:8.5-jdk21 AS builder

COPY settings.gradle /home/gradle
COPY . /app

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/subscriptions-0.0.1-SNAPSHOT.jar subscriptions.jar

ENTRYPOINT ["java", "-jar", "subscriptions.jar"]
