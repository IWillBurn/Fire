# build
FROM gradle:latest AS BUILD
WORKDIR /stockmarkets
COPY . .
RUN gradle build

# package
FROM openjdk:latest AS PACKAGE
ENV JAR_NAME=stockmarkets-main.jar
ENV APP_HOME=/stockmarkets
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/build/libs .
EXPOSE 8080
ENTRYPOINT exec java -jar $JAR_NAME