## The Band Web Page
Application is based on 4 following microservices:
 - event-api provides Rest API to CRUD operations for events (gigs). Data are stored in Mongo database.
 - booking-app provides Rest API to CRUD operations for booking. It's possible to make a reservation for avaible events. Data are stored in PostgreSQL database.
 - review-api provides Rest API to managing likes and comments.  Data are stored in Neo4J database.
 - fronted provides UI written with Jakarta EE Faces. What is more it's responsible for authentication and merging requests from different microservices. User data are stored in Mongo database.
 
Application is monitored by Jaeger and Prometheus systems.

## Architecture
![Architecture image](/../main/architecture.jpg)

## Build and Run
You can build the whole application thanks to file [docker-compose.yml](/../main/MicroProfile/docker-compose.yml) by executing following command:
```bash
docker compose up --build
```
or one of microservices by adding name of container:
```bash
docker compose up events-app --build
```

## Ports
Services' ports are defined in docker-compose.yml. 
 - booking-db: 8001,
 - events-db: 27017,
 - user-db: 27018,
 - review-db: 7474,
 - booking-app: 9081, 9441 (https),
 - events-app: 9080, 9440 (https),
 - review-app: 9083, 9443 (https),
 - frontend: 9082, 9442 (https),
 - porometheus: 9000,
 -  jaeger: 16686.
