# Build project
FROM maven:3-openjdk-17 AS build
WORKDIR /home/app
COPY src ./src
COPY pom.xml ./
RUN mvn -f pom.xml clean package

FROM icr.io/appcafe/open-liberty:kernel-slim-java17-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

ENV DEFAULT_HTTP_PORT=9081
ENV DEFAULT_HTTPS_PORT=9441

LABEL \
  org.opencontainers.image.authors="Mikolaj Baczynski" \
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="booking-app" \
  version="$VERSION-$REVISION" \
  summary="The booking-app microservice" \
  description="This image contains the booking-app microservice running with the Open Liberty runtime."

EXPOSE 9081

COPY --chown=1001:0 /src/main/liberty/config /config

RUN features.sh

COPY --chown=1001:0 --from=build /home/app/target/*.war /config/apps

RUN configure.sh
