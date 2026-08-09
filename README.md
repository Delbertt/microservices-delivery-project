
# DeliveryProj

## Project Context

This project is a **personal study initiative**, built to explore Spring Cloud microservices architecture, event-driven communication, and resilience patterns. It is non-commercial and educational, not intended for production use.

## Tech Stack

- **Backend:** Java 21, Spring Boot, Spring Cloud (Gateway, Eureka), Spring Data JPA, Spring Kafka, Resilience4j
- **Database:** PostgreSQL, pgAdmin
- **DevOps/Tools:** Maven, Docker Compose, Apache Kafka, Kafka UI, Lombok, JUnit 5 / REST Assured

## Project Structure

```
DeliveryProj/
└── Microservices/
    ├── Service-registry/       # Eureka discovery server
    ├── Gateway/                # API Gateway (routing, retry, circuit breaker)
    ├── Courier-Management/     # Courier domain: assigns/fulfills deliveries, consumes Kafka events
    └── Delivery-Tracking/      # Delivery domain: manages delivery lifecycle, publishes Kafka events
```
## Architecture

Clients call the **Gateway**, which routes requests to the appropriate service using **Eureka** for discovery and load balancing. **Delivery-Tracking** owns the delivery lifecycle (draft → placed → picked up → delivered) and publishes domain events to **Kafka**. **Courier-Management** consumes those events to assign and fulfill deliveries, and exposes an API that Delivery-Tracking calls (protected by Resilience4j retry/circuit breaker) to calculate courier payouts.

```
        ┌────────────┐
Client ─▶  Gateway    │
        └─────┬──────┘
              │ lb:// (via Eureka)
     ┌────────┴─────────┐
     ▼                  ▼
┌───────────┐     ┌───────────────────┐
│ Delivery- │────▶│ Courier-Management │
│ Tracking  │ HTTP│ (retry + circuit   │
│           │     │  breaker)          │
└─────┬─────┘     └─────────▲─────────┘
      │  Kafka events       │
      └──────────────────────┘
```

## Design Patterns

- **Dependency Injection** — constructor injection via Spring's IoC container throughout all services.
- **Factory** — static factory methods for aggregate creation (`Delivery.draft()`, `Courier.brandNew()`).
- **Builder** — Lombok `@Builder` for value objects (`ContactPoint`, `PreparationDetails`).
- **Strategy** — swappable implementations behind interfaces (`DeliveryTimeEstimationService`, `IntegrationEventPublisher`).
- **Observer** — domain events via Spring's `AbstractAggregateRoot`/`ApplicationEventPublisher`, extended across services through Kafka producer/consumer.
- **Repository** — Spring Data JPA repositories abstracting persistence.
- **Circuit Breaker** — Resilience4j, applied both at the Gateway and on inter-service HTTP calls.
- **Gateway / Load Balancing** — Eureka-backed `lb://` routing at the API Gateway.

---

