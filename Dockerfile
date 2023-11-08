FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/bookstore-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#FROM eclipse-temurin:17-jdk-alpine
#EXPOSE 8080
#ARG JAR_FILE=target/bookstore-task-0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} bookstore-task.jar
#ENTRYPOINT ["java","-jar","/bookstore-task.jar"]