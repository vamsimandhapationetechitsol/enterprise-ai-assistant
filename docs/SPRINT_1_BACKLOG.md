# Sprint 1 Backlog

## Sprint Overview

| Field | Details |
|---|---|
| **Sprint Number** | Sprint 1 |
| **Sprint Duration** | 2 Weeks |
| **Sprint Goal** | Initialize all backend microservices with Spring Boot, implement JWT-based authentication, set up PostgreSQL with pgvector, and establish the development environment |
| **Project** | BankingAI Assistant |
| **Developer** | Vamsi Chowdary Mandhapati |
| **Status** | In Progress |

---

## Sprint Goal

By the end of Sprint 1, the team will have a fully functional authentication system, initialized Spring Boot microservices for document and chat handling, a PostgreSQL database with pgvector extension configured, and a working Docker Compose environment for local development.

---

## User Stories

### US-001: User Registration

**As a** banking professional,
**I want to** register an account with my email and password,
**So that** I can securely access the BankingAI Assistant platform.

**Acceptance Criteria:**
- [ ] User can submit registration form with name, email, and password
- [ ] Password is validated for minimum length and complexity
- [ ] Duplicate email registrations are rejected with a clear error message
- [ ] Successful registration returns a JWT access token
- [ ] User record is persisted in PostgreSQL

**Story Points:** 5  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-002: User Login

**As a** registered user,
**I want to** log in with my credentials,
**So that** I can receive a JWT token to authenticate future requests.

**Acceptance Criteria:**
- [ ] User can log in with valid email and password
- [ ] Invalid credentials return a 401 Unauthorized response
- [ ] Successful login returns a signed JWT access token and refresh token
- [ ] JWT token expires after a configured duration (e.g., 1 hour)
- [ ] Refresh token is stored securely

**Story Points:** 5  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-003: JWT Token Validation

**As a** backend service,
**I want to** validate JWT tokens on every protected request,
**So that** only authenticated users can access secured endpoints.

**Acceptance Criteria:**
- [ ] All protected API endpoints require a valid Bearer token
- [ ] Expired or malformed tokens return a 401 response
- [ ] Token validation is handled by a Spring Security filter
- [ ] User roles/authorities are extracted from the token claims

**Story Points:** 3  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-004: Role-Based Access Control

**As an** administrator,
**I want to** assign roles (ADMIN, USER) to accounts,
**So that** access to sensitive operations is restricted by role.

**Acceptance Criteria:**
- [ ] User entity has a roles field supporting multiple roles
- [ ] ADMIN role can access management endpoints
- [ ] USER role is limited to standard document and chat endpoints
- [ ] Unauthorized role access returns a 403 Forbidden response

**Story Points:** 3  
**Priority:** Medium  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-005: Spring Boot Project Initialization (Auth Service)

**As a** developer,
**I want to** initialize the `backend-auth-service` Spring Boot project,
**So that** the authentication microservice has a clean, production-ready foundation.

**Acceptance Criteria:**
- [ ] Spring Boot project created with Java 17
- [ ] Dependencies added: Spring Security, Spring Data JPA, PostgreSQL Driver, JWT library, Lombok
- [ ] Application properties configured for database connection and JWT secret
- [ ] Health check endpoint `/actuator/health` is accessible
- [ ] Project runs successfully via `mvn spring-boot:run`

**Story Points:** 2  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-006: Spring Boot Project Initialization (Document Service)

**As a** developer,
**I want to** initialize the `backend-document-service` Spring Boot project,
**So that** document ingestion and management has a dedicated, isolated microservice.

**Acceptance Criteria:**
- [ ] Spring Boot project created with Java 17
- [ ] Dependencies added: Spring Data JPA, PostgreSQL Driver, Spring Web, Lombok
- [ ] Application properties configured for database and port
- [ ] Health check endpoint is accessible
- [ ] Project compiles and starts without errors

**Story Points:** 2  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-007: Spring Boot Project Initialization (Chat Service)

**As a** developer,
**I want to** initialize the `backend-chat-service` Spring Boot project,
**So that** AI chat interactions have a dedicated microservice scaffold ready for development.

**Acceptance Criteria:**
- [ ] Spring Boot project created with Java 17
- [ ] Dependencies added: Spring Web, Spring Data JPA, PostgreSQL Driver, OpenAI client library, Lombok
- [ ] Application properties configured for OpenAI API key and database
- [ ] Health check endpoint is accessible
- [ ] Project compiles and starts without errors

**Story Points:** 2  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-008: PostgreSQL Database Setup with pgvector

**As a** developer,
**I want to** configure PostgreSQL with the pgvector extension,
**So that** the system can store and query document embeddings using vector similarity search.

**Acceptance Criteria:**
- [ ] PostgreSQL instance runs via Docker Compose
- [ ] pgvector extension is enabled (`CREATE EXTENSION vector`)
- [ ] Initial schema migrations are created using Flyway or Liquibase
- [ ] `users`, `documents`, and `embeddings` tables are created
- [ ] Vector column is defined with the correct dimension (e.g., 1536 for OpenAI embeddings)

**Story Points:** 5  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-009: Docker Compose Local Development Setup

**As a** developer,
**I want to** run the entire stack locally using Docker Compose,
**So that** all services and the database can be started with a single command.

**Acceptance Criteria:**
- [ ] `docker-compose.yml` defines services for `auth-service`, `document-service`, `chat-service`, and `postgres`
- [ ] Services communicate over a shared Docker network
- [ ] Environment variables are managed via `.env` file
- [ ] `docker-compose up` starts all services without manual intervention
- [ ] Port mappings are documented in the README

**Story Points:** 3  
**Priority:** High  
**Assignee:** Vamsi Chowdary Mandhapati

---

### US-010: React Frontend Project Initialization

**As a** developer,
**I want to** initialize the `frontend-react` project with TypeScript,
**So that** the user interface has a clean, type-safe foundation ready for feature development.

**Acceptance Criteria:**
- [ ] React + TypeScript project created using Vite or Create React App
- [ ] React Router configured for page-level navigation
- [ ] Axios or Fetch configured for API communication
- [ ] Placeholder pages created: Login, Register, Dashboard
- [ ] Project runs via `npm start` and displays a welcome page

**Story Points:** 3  
**Priority:** Medium  
**Assignee:** Vamsi Chowdary Mandhapati

---

## Technical Tasks

| Task ID | Task Description | Linked Story | Estimate | Status |
|---|---|---|---|---|
| T-001 | Configure Spring Security filter chain for JWT | US-003 | 3h | To Do |
| T-002 | Implement `JwtTokenProvider` utility class | US-002, US-003 | 2h | To Do |
| T-003 | Create `UserDetailsServiceImpl` for Spring Security | US-001, US-002 | 2h | To Do |
| T-004 | Design and apply Flyway migration scripts | US-008 | 3h | To Do |
| T-005 | Create `User`, `Role`, `Document`, `Embedding` JPA entities | US-008 | 3h | To Do |
| T-006 | Write unit tests for AuthService (register, login) | US-001, US-002 | 3h | To Do |
| T-007 | Configure CORS for frontend-to-backend communication | US-009 | 1h | To Do |
| T-008 | Set up GitHub Actions CI pipeline (build + test) | All | 2h | To Do |
| T-009 | Write integration tests for `/auth/register` and `/auth/login` | US-001, US-002 | 2h | To Do |
| T-010 | Create Dockerfiles for all three backend services | US-009 | 2h | To Do |

---

## Sprint Backlog Summary

| Story ID | Title | Points | Priority | Status |
|---|---|---|---|---|
| US-001 | User Registration | 5 | High | To Do |
| US-002 | User Login | 5 | High | To Do |
| US-003 | JWT Token Validation | 3 | High | To Do |
| US-004 | Role-Based Access Control | 3 | Medium | To Do |
| US-005 | Auth Service Initialization | 2 | High | To Do |
| US-006 | Document Service Initialization | 2 | High | To Do |
| US-007 | Chat Service Initialization | 2 | High | To Do |
| US-008 | PostgreSQL + pgvector Setup | 5 | High | To Do |
| US-009 | Docker Compose Setup | 3 | High | To Do |
| US-010 | React Frontend Initialization | 3 | Medium | To Do |
| **Total** | | **33** | | |

---

## Definition of Done

A user story is considered **Done** when:

- [ ] All acceptance criteria are met
- [ ] Code is written and peer-reviewed (or self-reviewed for solo development)
- [ ] Unit tests are written and passing
- [ ] No critical or high-severity bugs remain open
- [ ] Code is merged into the `main` branch via pull request
- [ ] Relevant documentation is updated
- [ ] Feature is verified running in the Docker Compose local environment

---

## Sprint Risks and Mitigations

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| pgvector setup complexity | Medium | High | Follow official pgvector Docker image docs; test early |
| JWT security misconfiguration | Low | High | Use well-tested JJWT library; follow OWASP JWT best practices |
| Docker networking issues between services | Medium | Medium | Define explicit service names and network aliases in docker-compose.yml |
| OpenAI API key not available for chat service | Low | Medium | Use mock/stub for Chat Service in Sprint 1; real integration in Sprint 2 |

---

## Sprint Notes

- Sprint 1 focuses on **infrastructure and authentication** - no AI/RAG features are expected in this sprint.
- The Chat Service initialization in this sprint is scaffold only; OpenAI integration is planned for Sprint 2.
- All database schema changes must go through migration scripts (Flyway) - no manual DDL changes.
- Refer to `ARCHITECTURE.md` for service boundaries and `API_DOCUMENTATION.md` for endpoint contracts.
- Refer to `docs/AUTHENTICATION_FLOW.md` for the detailed JWT authentication sequence diagram.
- Refer to `docs/DATABASE_DESIGN.md` for the full database schema and pgvector index strategy.

---

*Last Updated: Week 2 | BankingAI Assistant Project*
