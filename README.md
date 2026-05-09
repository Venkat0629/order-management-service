# Order Management Service

A Spring Boot REST API for managing customers, products, and purchase orders in an order management domain. The repository is organized into `customer`, `product`, `order`, and `common` packages under `com.acme.ordermanagement`, with dedicated controllers, DTOs, entities, repositories, and services for each module. [1]

## Tech Stack

- Java 21 [1]
- Spring Boot 3.5.14 [1]
- Spring Web [1]
- Spring Data JPA [1]
- Spring Validation [1]
- PostgreSQL [1]
- Lombok [1]
- Maven [1]

## Project Structure

```text
src/main/java/com/acme/ordermanagement
├── common
│   ├── config
│   └── exception
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
├── product
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
└── OrderManagementServiceApplication.java
```

This structure shows a clean feature-based package layout, plus shared configuration and exception handling in the `common` module. [1]

## Features

- Create customers through `/api/customers`. [1]
- Create products through `/api/products`. [1]
- Create orders through `/api/orders`. [1]
- Mark an order as paid through `/api/orders/{orderNumber}/pay`. [1]
- Fetch order details through `/api/orders/{orderNumber}`. [1]
- Fetch paginated orders by status through `/api/orders?status=...&page=...&size=...`. [1]
- Fetch all orders for a customer through `/api/orders/customer/{customerId}`. [1]
- Validate incoming payloads using Jakarta Bean Validation annotations in request DTOs. [1]

## API Endpoints

### Customer API

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/customers` | Create a new customer. [1] |

### Product API

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/products` | Create a new product. [1] |

### Order API

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders` | Create a new order. [1] |
| PATCH | `/api/orders/{orderNumber}/pay` | Mark an order as paid. [1] |
| GET | `/api/orders/{orderNumber}` | Get detailed order information. [1] |
| GET | `/api/orders?status={status}&page={page}&size={size}` | Get paginated orders filtered by status. [1] |
| GET | `/api/orders/customer/{customerId}` | Get all orders belonging to a customer. [1] |

## Request Payloads

### Create Customer

`CreateCustomerRequest` defines two fields: `fullName` and `email`, and both are required, while `email` must also be in a valid email format. [1]

```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com"
}
```

### Create Product

`CreateProductRequest` defines `sku`, `name`, `unitPrice`, and `availableQuantity`, where `unitPrice` must be greater than zero and `availableQuantity` cannot be negative. [1]

```json
{
  "sku": "SKU-1001",
  "name": "Wireless Mouse",
  "unitPrice": 799.00,
  "availableQuantity": 50
}
```

### Create Order

`CreateOrderRequest` defines `customerId` and `items`, and it requires at least one order item in the request. [1]

```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 101,
      "quantity": 2
    }
  ]
}
```

The order request uses `List<OrderItemRequest>` for `items`, so each order item is expected to contain item-level product and quantity information. [1]

## Build Configuration

The Maven build uses `spring-boot-starter-parent` version `3.5.14`, targets Java 21, and includes dependencies for Spring Web, Spring Data JPA, Validation, PostgreSQL, Lombok, and Spring Boot Test. [1]

## Running Locally

### Prerequisites

- Java 21 [1]
- Maven 3.9+
- PostgreSQL [1]

### Clone and Run

```bash
git clone https://github.com/Venkat0629/order-management-service.git
cd order-management-service
mvn spring-boot:run
```

The application is configured to run on port `8080`. [1]

## Database Configuration

The current `application.yaml` config uses PostgreSQL, enables `ddl-auto: update`, turns on SQL logging, and formats SQL output. [1]

Example pattern:

```yaml
spring:
  application:
    name: order-management-service
  datasource:
    url: jdbc:postgresql://localhost:5432/order_management
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
server:
  port: 8080
```

## Security Note

The public repository currently exposes a PostgreSQL connection URL, username, and password in `application.yaml`, so those credentials should be rotated immediately and removed from version control. The safer pattern is to externalize them through environment variables or profile-specific local config. [1]

Recommended replacement:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

## Implementation Notes

- `CustomerController` accepts `CreateCustomerRequest` and returns `CustomerResponse`. [1]
- `ProductController` accepts `CreateProductRequest` and returns `ProductResponse`. [1]
- `OrderController` works with `CreateOrderRequest`, `OrderResponse`, `OrderDetailResponse`, `OrderSummaryResponse`, and `OrderStatus`. [1]
- The codebase also includes `JpaAuditConfig`, `GlobalExceptionHandler`, `ResourceNotFoundException`, and `ResourceAlreadyExistsException`, which indicates the project already includes shared persistence and error-handling concerns. [1]

## Suggested Next Improvements

- Add Swagger/OpenAPI documentation.
- Add Docker Compose for the app and PostgreSQL.
- Add integration tests for controller and service layers.
- Add sample seed data.
- Add environment-specific profiles such as `application-dev.yaml` and `application-prod.yaml`.
- Replace secrets in Git with environment variables immediately. [1]

## Repository

GitHub: [Venkat0629/order-management-service](https://github.com/Venkat0629/order-management-service) [1]
