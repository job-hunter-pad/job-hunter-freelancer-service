FROM openjdk:15

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} freelancerservice.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/freelancerservice.jar"]