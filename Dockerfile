FROM openjdk:17-alpine
WORKDIR /app
COPY ./build/libs/wallet-0.0.1-SNAPSHOT.jar /app/app.jar
CMD [ "java", "-jar", "/app/app.jar"]