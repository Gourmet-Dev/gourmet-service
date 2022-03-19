FROM amazoncorretto:11 as place-api
ENV PLACE_API_VMOPTS="--illegal-access=warn --add-opens java.base/java.time=ALL-UNNAMED"
ARG PLACE_API_APP=build/service/place-api/*.jar
ARG PLACE_API_PORT=8080
COPY ${PLACE_API_APP} app.jar
EXPOSE ${PLACE_API_PORT}
ENTRYPOINT ["java", "${PLACE_API_VMOPTS}", "-jar", "/app.jar"]
