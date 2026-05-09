# Order Management Service

A Spring Boot backend service for managing customers, products, and purchase orders using a clean layered architecture with controllers, services, repositories, DTOs, and centralized exception handling.

## Overview

This project is designed as a modular REST API for a basic order management domain. It currently focuses on three core business areas:

- Customer management
- Product management
- Order management

The codebase is organized to demonstrate common Spring Boot backend patterns such as:

- Layered architecture
- DTO-based API design
- Spring Data JPA repositories
- Centralized exception handling
- JPA auditing support

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven Wrapper
- REST APIs

## Project Structure

```text
src/main/java/com/acme/ordermanagement
├── common
│   ├── config
│   │   └── JpaAuditConfig.java
│   └── exception
│       ├── ApiErrorResponse.java
│       ├── GlobalExceptionHandler.java
│       ├── ResourceAlreadyExistsException.java
│       └── ResourceNotFoundException.java
├── customer
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
├── order
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
└── product
    ├── controller
    ├── dto
    ├── entity
    ├── repository
    └── service
```

## Current Features

### Customer APIs
- Create customer
- Get customer by id
- List customers

### Product APIs
- Create product
- Get product by id
- List products

### Order APIs
- Create order
- Get order by id
- List orders

### Shared Backend Features
- Global exception handling
- Domain-specific exceptions
- DTO-based request and response models
- JPA auditing configuration

## Architecture

This project follows a standard layered architecture:

- **Controller layer** handles HTTP requests and responses.
- **Service layer** contains business logic and orchestration.
- **Repository layer** manages persistence using Spring Data JPA.
- **Entity layer** represents database models.
- **DTO layer** separates API contracts from persistence models.

This structure keeps the code maintainable, testable, and easy to extend.

## Local Setup

### Prerequisites

Make sure the following are installed:

- Java 17 or Java 21
- Git

Maven installation is optional because this project includes the Maven Wrapper.

### Clone the Repository

```bash
git clone https://github.com/Venkat0629/order-management-service.git
cd order-management-service
```

### Run the Application

For Windows:

```powershell
mvnw.cmd spring-boot:run
```

For Linux or macOS:

```bash
./mvnw spring-boot:run
```

The application should start on:

```text
http://localhost:8080
```

## API Overview

> Note: The exact endpoint mappings may vary depending on controller annotations. Update this section once controller mappings are finalized.

### Customers

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/customers` | Create a new customer |
| GET | `/customers` | Get all customers |
| GET | `/customers/{id}` | Get customer by id |

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/products` | Create a new product |
| GET | `/products` | Get all products |
| GET | `/products/{id}` | Get product by id |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders` | Create a new order |
| GET | `/orders` | Get all orders |
| GET | `/orders/{id}` | Get order by id |

## Example Request Payloads

### Create Customer

```json
{
  "name": "Sai Mane",
  "email": "sai@example.com"
}
```

### Create Product

```json
{
  "name": "Wireless Mouse",
  "price": 799.0,
  "stockQuantity": 25
}
```

### Create Order

```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

## Error Handling

The project uses centralized exception handling through a global exception handler. This helps return consistent error responses for common cases such as:

- Resource not found
- Duplicate resource creation
- Validation failures (recommended next improvement)

## Recommended Next Improvements

### API Quality
- Add request validation using Jakarta Bean Validation
- Return proper HTTP status codes for all scenarios
- Add pagination and filtering for list endpoints
- Improve response consistency across modules

### Business Rules
- Validate product stock before order creation
- Support order status transitions
- Add inventory update rules during order lifecycle changes
- Strengthen domain constraints and uniqueness checks

### Testing
- Add unit tests for services
- Add controller tests using MockMvc
- Add integration tests for persistence flows
- Add negative test cases for exception scenarios

### Developer Experience
- Add Swagger/OpenAPI documentation
- Add a GitHub Actions workflow for build and test
- Add Postman collection and environment files
- Document database configuration clearly

## Postman

A starter Postman collection can be used to test the APIs locally. It should include variables such as:

- `baseUrl`
- `customerId`
- `productId`
- `orderId`

## Future Enhancements

- Update order status through dedicated APIs
- Retrieve orders by customer
- Update product stock and price
- Add pagination, sorting, and filtering
- Improve reporting responses for order summaries
- Add monitoring and production-oriented logging

## Why This Project Matters

This repository is a good backend portfolio project because it demonstrates practical Spring Boot fundamentals in a realistic business domain. With stronger validation, testing, API docs, and CI, it can become a strong interview-ready project for Java backend or full stack roles.