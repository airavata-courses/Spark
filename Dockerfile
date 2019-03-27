FROM java:8-jdk-alpine

COPY /rating/target/rating-0.0.1-SNAPSHOT.jar /app/

WORKDIR /app

RUN sh -c 'touch rating-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","rating-0.0.1-SNAPSHOT.jar"]
