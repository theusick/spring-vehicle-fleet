# spring-vehicle-fleet

Vehicle fleet backend with **Spring Boot 3**, **Thymeleaf** + Bootstrap, Hibernate, PostgreSQL.

## Requirements

- **Java 23+**: [OpenJDK 23.0.1](https://jdk.java.net/23/)
- **Maven 3.9.8+**: [Apache Maven](https://maven.apache.org/download.cgi)

#### Additionally

- **Docker**
- **PostgreSQL 16**: [PostgreSQL Download](https://www.postgresql.org/download/)

## Building the Project

To build the project, first run externally PostgreSQL or by docker-compose:

```bash
docker-compose up -d
```

Copy `.env.example` to `.env` and setup environment config.

Build app:

```bash
mvn clean package
```
