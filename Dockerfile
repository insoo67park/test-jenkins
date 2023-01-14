FROM openjdk:17-jdk-alpine
RUN mkdir -p /app/ /app/logs/
ADD target/worker-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
WORKDIR /app/
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]
#spring.profiles.active=dev => application-prod.yaml, spring.profile.active=test => application-test.yaml, spring.profile.active=prod => application-prod.yaml
#application-test.yaml에 spring.profiles.active=dev로 설정에 주어야 함
