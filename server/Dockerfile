# Context : ..
FROM openjdk:8u162-jdk-stretch as first

LABEL maintainer="VIEGAS Michael"

WORKDIR /workspace

COPY gradle/ ./gradle
COPY gradlew gradlew.bat build.gradle settings.gradle resolveDependencies.gradle ./
COPY server/build.gradle server/

#RUN ls -laR

RUN ./gradlew resolveDependencies

COPY ./ ./

RUN ./gradlew :server:build

FROM openjdk:8u162-jre-slim-stretch

COPY --from=first /workspace/server/build/libs/server-*.jar /opt/app.jar

WORKDIR /opt

EXPOSE 80

CMD ls -la /opt && java -jar -Dspring.profiles.active=docker /opt/app.jar
