# July 17 Project Update - PostgreSQL Integration and Audit CRUD

## Submission Date

July 17, 2026

## Developer

Vamsi Chowdary Mandhapati

## Sprint Goal

Implement database-backed audit logging in the Account Service and expose a REST API that allows recent user activity to be reviewed by the development team.

## Work Completed

- Added `AuditLog` JPA entity mapped to the `audit_logs` PostgreSQL table.
- Added `AuditLogRepository` using Spring Data JPA.
- Added `AuditService` and `AuditServiceImpl` to save and retrieve audit events.
- Added `AuditController` endpoint: `GET /api/audit`.
- Added `AuditLogResponse` DTO and `AuditMapper`.
- Integrated audit logging into user registration, login, profile lookup, and user update flows.
- Added unit tests for audit service behavior.
- Added `.gitignore` rules to avoid committing Maven build output.

## REST APIs Demonstrated

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/register` | Register a new account-service user |
| POST | `/login` | Validate user login |
| GET | `/profile?email={email}` | Retrieve user profile |
| GET | `/api/audit` | Retrieve latest audit logs |

## Code Snippets to Show

### Audit Entity

```java
@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String performedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;
}
```

### Repository

```java
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findTop25ByOrderByTimestampDesc();
}
```

### Service Method

```java
@Transactional
public void logAction(String action, String performedBy) {
    auditLogRepository.save(new AuditLog(action, performedBy));
}
```

### Controller Endpoint

```java
@GetMapping
public List<AuditLogResponse> getAuditLogs() {
    return auditService.getAuditLogs();
}
```

## Validation

- Maven unit tests executed for `account-service`.
- Swagger UI used to confirm REST API visibility.
- PostgreSQL-backed endpoints tested locally.

## Screenshots Checklist

- GitHub repository home
- GitHub commit history
- Account service package structure
- `AuditLog.java`
- `AuditLogRepository.java`
- `AuditController.java`
- Swagger UI with Audit endpoint
- Postman API response for `/api/audit`
- Application running in terminal
- July 17 project update document / README

## Future Work

- Add JWT authentication to secure audit endpoints.
- Add role-based access control so only admins can view audit logs.
- Add pagination and filtering for audit history.
- Add integration tests with PostgreSQL Testcontainers.
- Add CI workflow for automated Maven builds and tests.
