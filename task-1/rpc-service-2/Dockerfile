FROM openjdk:8u212-jdk-slim
WORKDIR /usr/rpc-service-2
ADD ./target/rpc-service-2-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar
EXPOSE 50052 50052
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom","-jar","/usr/rpc-service-2/app.jar"]
