FROM openjdk:11
COPY ./target/ticketing-project-rest-0.0.1-SNAPSHOT.jar  /usr/app
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","ticketing-project-rest-0.0.1-SNAPSHOT.jar"]