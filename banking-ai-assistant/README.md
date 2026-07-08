# Banking AI Assistant

Banking AI Assistant is a Spring Boot microservices project for banking workflows. The current implementation focus is the `account-service`, which provides user registration, login, profile retrieval, account management, audit logging, validation, Swagger documentation, and unit tests. User records are stored in the `banking_users` table.

## Services

- `account-service`
- `auth-service`
- `gateway-service`
- `ai-service`
- `transaction-service`
- `eureka-server`

## Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Cloud 2023
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Validation
- Springdoc OpenAPI / Swagger UI
- PostgreSQL
- Maven
- JUnit 5 and Mockito

## Account Service API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/register` | Register a new user |
| `POST` | `/login` | Authenticate a user |
| `GET` | `/profile?email={email}` | Get a user profile |
| `GET` | `/api/accounts/user/{userId}` | Get accounts for a user |
| `GET` | `/api/accounts/{accountId}` | Get account by ID |
| `GET` | `/api/accounts/number/{accountNumber}` | Get account by account number |
| `POST` | `/api/accounts` | Create an account |
| `PATCH` | `/api/accounts/{accountId}/status` | Update account status |

## Build Instructions

From the project root:

```bash
mvn clean install
```

To build only the account service and required parent modules:

```bash
mvn -pl account-service -am clean install
```

## Run Instructions

Start PostgreSQL and create the `account_db` database, then run:

```bash
mvn -pl account-service spring-boot:run
```

The account service runs on:

```text
http://localhost:8082
```

Swagger UI is available at:

```text
http://localhost:8082/swagger-ui/index.html
```

## Sample Requests

Register:

```http
POST /register
Content-Type: application/json

{
  "firstName": "Vamsi",
  "lastName": "Mandhapati",
  "email": "vamsi@example.com",
  "password": "secret123",
  "role": "USER"
}
```

Login:

```http
POST /login
Content-Type: application/json

{
  "email": "vamsi@example.com",
  "password": "secret123"
}
```

Profile:

```http
GET /profile?email=vamsi@example.com
```

## Progress Update Email

Subject: Development Progress Update - July 8, 2026

Hi [Manager Name],

I wanted to share an update on my progress as of July 8, 2026.

I completed the implementation and integration work for the Account Service module of the Banking AI Assistant project. The work includes the User entity, DTOs, repositories, service layer, REST controllers, validation, exception handling, mapping logic, and API endpoints for user registration, login, and profile retrieval.

I also verified the project build, updated the documentation, completed testing of the implemented APIs, and pushed the latest code changes to the repository.

The following items have been completed:

- User entity implementation
- Request and response DTOs
- Repository layer
- Service layer implementation
- REST API endpoints
- Validation and exception handling
- API testing
- Documentation updates
- Successful project build and code commit

Please let me know if there are any additional tasks or improvements you would like me to work on. I appreciate your feedback and look forward to continuing the project.

Thank you,
Vamsi Chowdary Mandhapati
