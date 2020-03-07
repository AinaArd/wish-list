FROM adoptopenjdk/openjdk8:alpine-jre

COPY ./target/wish-list-0.1.jar wish-list-0.1.jar
CMD ["java", "-Xmx128m", "-jar", "wish-list-0.1.jar"]
