FROM amazoncorretto:11 as place-api
ARG PLACE_API_APP=build/service/place-api/*.jar
ARG PLACE_API_PORT=8080
COPY ${PLACE_API_APP} app.jar
EXPOSE ${PLACE_API_PORT}
ENTRYPOINT ["java", "-jar", "/app.jar"]
