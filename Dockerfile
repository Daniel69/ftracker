FROM anapsix/alpine-java:8
WORKDIR /app
RUN apk add --no-cache tzdata
ENV TZ America/Bogota
COPY main/build/libs/main-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD java -jar /app/app.jar