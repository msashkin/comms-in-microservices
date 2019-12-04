FROM openjdk:8u212-jdk-slim
WORKDIR /usr/web-service-2
ADD ./target/web-service-2-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080 60000
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom","-jar","/usr/web-service-2/app.jar"]
