FROM openjdk:22-jdk

WORKDIR /app

COPY target/lab2-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java", "-Xms16g", "-Xmx16g", "-jar", "app.jar"]