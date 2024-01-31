FROM amazoncorretto:11-alpine-jdk
COPY target/gym-0.1.0.jar gym-0.1.0.jar
ENTRYPOINT ["java", "-jar", "/gym-0.1.0.jar"]
