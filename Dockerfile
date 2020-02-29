FROM openjdk:8-jdk-slim
VOLUME /tmp
ADD target/money-api-0.0.1-SNAPSHOT.jar money-api.jar
EXPOSE 80
RUN bash -c 'touch /money-api.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/money-api.jar"]