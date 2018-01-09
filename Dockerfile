FROM openjdk:8-jdk-alpine

MAINTAINER jm5619

RUN mkdir /app

WORKDIR /app

ADD ./target/ir-property-listing-1.0.0-SNAPSHOT.jar /app

EXPOSE 8084

CMD ["java", "-jar", "ir-property-listing-1.0.0-SNAPSHOT.jar"]
