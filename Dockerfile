FROM java:8-jdk-alpine

COPY ./search-0.0.1-SNAPSHOT.jar /app/

WORKDIR /app

RUN sh -c 'touch search-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","search-0.0.1-SNAPSHOT.jar"]
