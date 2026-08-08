# 🚚 Delivery Microservices Platform

A production-grade, event-driven microservices system for managing delivery operations, built as a comprehensive study project to master distributed systems architecture, resilience patterns, and asynchronous communication.

This project simulates a real-world delivery service platform where multiple specialized microservices collaborate to handle order processing, driver assignment, real-time tracking, and notifications—all orchestrated through event-driven messaging and fault-tolerant design patterns.

---

## 🎯 Motivation & Learning Goals

I developed this project as an in-depth study to bridge the gap between theoretical microservices concepts and practical, running code. The primary objectives were to:

*   **Master Distributed System Patterns**: Implement and understand service discovery, API gateways, circuit breakers, retries, and fallback mechanisms in a realistic scenario.
*   **Implement Event-Driven Architecture**: Move beyond simple REST APIs to build asynchronous, decoupled services using Apache Kafka for reliable message streaming.
*   **Apply Resilience Engineering**: Integrate **Resilience4j** to make the system tolerant of network failures, timeouts, and partial outages—a critical skill for production environments.
*   **Understand Containerization & Orchestration**: Use Docker and Docker Compose to define, run, and scale the entire multi-service ecosystem locally.
*   **Gain Hands-on Experience with Modern Java Stack**: Deepen proficiency in Spring Boot, Spring Cloud, and reactive programming paradigms.

---

## 🧩 Tech Stack

| Category | Technologies & Tools |
| :--- | :--- |
| **Language** | Java 17+ |
| **Frameworks** | Spring Boot 3.x, Spring Cloud, Spring Data JPA |
| **Resilience** | Resilience4j (Circuit Breaker, Retry, Rate Limiter, Bulkhead) |
| **Messaging** | Apache Kafka, Spring Kafka |
| **Database** | PostgreSQL (per-service database), MongoDB (optional for tracking) |
| **API & Communication** | RESTful APIs, OpenFeign (declarative HTTP clients), API Gateway (Spring Cloud Gateway) |
| **Service Discovery** | Netflix Eureka |
| **Containerization** | Docker, Docker Compose |
| **Monitoring** | Micrometer, Prometheus, Grafana (optional) |
| **Build Tool** | Maven / Gradle |

---

## 📁 Project Structure

The repository is organized by microservice, each with its own bounded context and database.

microservices-delivery-project/
├── Microservices/ # Root directory for all services
│ ├── api-gateway/ # Spring Cloud Gateway - single entry point
│ ├── discovery-service/ # Netflix Eureka - service registry
│ ├── order-service/ # Manages orders, status, and lifecycle
│ ├── driver-service/ # Manages driver profiles, availability, and assignment
│ ├── tracking-service/ # Handles real-time delivery tracking
│ ├── notification-service/ # Sends events via email/SMS/push
│ └── ... (other services as needed)
├── docker-compose.yml # Orchestrates all services, Kafka, and databases
├── .env.example # Environment variables template
└── .gitignore

**Key Components**:

*   **API Gateway**: Routes requests to appropriate services, handles cross-cutting concerns (auth, logging).
*   **Discovery Service**: Enables services to locate each other dynamically without hardcoded addresses.
*   **Order Service**: Core service managing the delivery order lifecycle (creation → assignment → completion).
*   **Driver Service**: Manages driver data, location updates, and availability status.
*   **Tracking Service**: Processes location events and provides real-time status to end-users.
*   **Notification Service**: Consumes events to send alerts (e.g., "Order picked up", "Delivered").
*   **Resilience4j Patterns**: Applied across inter-service HTTP calls to prevent cascading failures.
