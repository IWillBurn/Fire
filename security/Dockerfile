# build
FROM gradle:latest AS BUILD
WORKDIR /security
COPY . .
RUN gradle build

# package
FROM openjdk:latest AS PACKAGE
ENV JAR_NAME=security-main.jar
ENV APP_HOME=/security
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/build/libs .
EXPOSE 8090
ENTRYPOINT exec java -jar $JAR_NAME