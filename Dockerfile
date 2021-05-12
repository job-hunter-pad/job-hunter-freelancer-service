FROM openjdk:15-jdk-alpine

ARG VERIF_URL=http://apigateway/api/auth/validateId

ENV AUTH_VERIFICATION_URL=$VERIF_URL

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} freelancerservice.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/freelancerservice.jar"]