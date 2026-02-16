# AGENTS.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Build & Run Commands

Each service is an independent Maven project. There is no parent POM; build services individually:

```bash
# Build a single service
./mvnw -f <service>/pom.xml clean package -DskipTests

# Run tests for a service
./mvnw -f <service>/pom.xml test

# Run a single test class
./mvnw -f order-service/pom.xml test -Dtest=OrderServiceApplicationTests

# Start entire stack (builds images + starts containers)
docker compose up --build

# Start only infrastructure (DBs, Kafka, Keycloak, observability)
docker compose up keycloak keycloak_db user-db order-db payment-db kafka-broker discovery prometheus grafana loki promtail

# Rebuild and restart a single service
docker compose up --build order-service
```

## Tech Stack

- **Java 21** / **Spring Boot 3.5.10** / **Spring Cloud 2025.0.1**
- **Eureka** for service discovery
- **Spring Cloud Gateway** (WebFlux) for API routing
- **Keycloak** for OAuth2/JWT authentication (realm: `microservices`)
- **PostgreSQL 16** (separate database per service)
- **Kafka** (KRaft mode) for async messaging
- **Prometheus + Grafana + Loki** for observability

## Architecture

```
┌─────────────┐      ┌─────────────────┐      ┌────────────────┐
│  Keycloak   │◄────►│   api-gateway   │◄────►│ discovery-svc  │
│  (OAuth2)   │      │  (port 8085)    │      │ (Eureka 8761)  │
└─────────────┘      └────────┬────────┘      └────────────────┘
                              │ routes via Eureka LB
         ┌────────────────────┼────────────────────┐
         ▼                    ▼                    ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│  user-service   │  │  order-service  │  │ payment-service │
│  (random port)  │  │  (random port)  │  │  (random port)  │
│      ▼          │  │      ▼          │  │      ▼          │
│   user-db       │  │   order-db      │  │  payment-db     │
│   (54321)       │  │   (54322)       │  │   (54323)       │
└─────────────────┘  └────────┬────────┘  └────────┬────────┘
                              │                    │
                              │ Kafka: order.created
                              └────────►───────────┘
```

**API Gateway routes** (all require JWT):
- `/api/v1/users/**` → user-service `/api/users/**`
- `/api/v1/orders/**` → order-service `/api/orders/**`
- `/api/v1/payments/**` → payment-service `/api/payments/**`

**Kafka event flow**: order-service publishes `OrderCreatedEvent` to `order.created` topic → payment-service consumes and creates payment record.

## Code Conventions

- **Package structure**: `dev.folomkin.<servicename>` (e.g., `dev.folomkin.orderservice`)
- **Layered architecture**: controller → service → repository
- **DTOs** in `model/dto/`, **entities** in `model/entity/`
- **Kafka** producers in `kafka/producer/`, consumers in `kafka/consumer/`
- **Security**: JWT auth via `@AuthenticationPrincipal Jwt jwt`, user ID from `jwt.getSubject()`
- Services run on `SERVER_PORT=0` (random port) and register with Eureka using random UUID instance IDs

## Docker

Services use multi-stage builds with Spring Boot layered jars. To add a new service:
1. Copy an existing Dockerfile (they're identical across services)
2. Build fat jar first: `./mvnw -f <service>/pom.xml package`
3. Add service definition to `docker-compose.yml` following existing patterns

## Local Development

When running services locally outside Docker, override Eureka and DB URLs:
```bash
./mvnw -f user-service/pom.xml spring-boot:run \
  -Dspring-boot.run.arguments="--eureka.client.service-url.defaultZone=http://localhost:8761/eureka --spring.datasource.url=jdbc:postgresql://localhost:54321/userdb"
```

## Observability Endpoints

- Eureka: http://localhost:8761
- Keycloak Admin: http://localhost:8080 (admin/admin)
- Kafka UI: http://localhost:8089
- Grafana: http://localhost:3000 (admin/admin123)
- Prometheus: http://localhost:9090
