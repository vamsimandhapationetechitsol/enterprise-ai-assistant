# BankingAI Assistant

AI-powered enterprise RAG platform that enables banking professionals to search, retrieve, summarize, and interact with financial documents.

---

## Project Overview

**Project Name:** BankingAI Assistant  
**Duration:** 5 Months  
**Developer:** Vamsi Chowdary Mandhapati  
**Reporting Period:** Week 1  

### Objective

The goal of BankingAI Assistant is to build an enterprise-grade AI-powered platform that enables banking professionals to search, retrieve, summarize, and interact with financial documents using Retrieval Augmented Generation (RAG), Large Language Models (LLMs), and modern cloud-native technologies.

---

## Technology Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JWT

### Frontend
- React
- TypeScript

### Database
- PostgreSQL
- pgvector

### AI Components
- OpenAI GPT-4
- RAG Architecture

### Infrastructure
- Docker
- Kubernetes
- AWS

---

## Project Structure

| Folder | Purpose |
|--------|----------|
| `backend-auth-service` | Authentication and authorization service |
| `account-service` | Week 4 user registration, login, and profile service |
| `backend-chat-service` | AI chat functionality and LLM integration |
| `backend-document-service` | Document ingestion, retrieval, and processing |
| `frontend-react` | React-based user interface |
| `docker` | Containerization resources |
| `kubernetes` | Deployment manifests |
| `docs` | Project documentation |

---

## Documentation

| File | Purpose |
|------|----------|
| `README.md` | Project overview and onboarding information |
| `PROJECT_STATUS.md` | Track project progress and milestones |
| `ARCHITECTURE.md` | Document system architecture and design decisions |
| `API_DOCUMENTATION.md` | Define API contracts and endpoint specifications |
| `CHANGELOG.md` | Track repository changes over time |

---

## Week 1 Summary

**Status:** Completed  
**Overall Progress:** 5%  
**Risk Level:** Low  
**Blockers:** None  

### Work Completed
- GitHub repository created
- Project folder structure established
- Documentation files created (README, PROJECT_STATUS, ARCHITECTURE, API_DOCUMENTATION, CHANGELOG)
- Week 1 planning issue created
- Architectural planning for core services completed

### Core Services Identified
1. **Authentication Service** - User registration, login, JWT token generation, role management
2. **Document Service** - Document upload, metadata storage, content search, document indexing
3. **Chat Service** - AI interactions, context retrieval, prompt orchestration, response generation
4. **Frontend Application** - User dashboard, search interface, chat interface, authentication pages

---

## Upcoming Weeks

- Spring Boot development
- React development
- Database implementation
- OpenAI integration
- Docker configuration
- Kubernetes deployment
- Authentication APIs
- Document ingestion APIs

---

## Week 4 Progress

The User Management Module is implemented in `account-service` with a layered Spring Boot architecture, PostgreSQL persistence, request validation, centralized exception handling, password hashing, OpenAPI documentation, and unit tests.

### Architecture

Requests flow from `AuthController` to `UserService`, then to `UserRepository` and PostgreSQL. DTOs define the API contract, the entity package owns persistence models, Spring Security provides password encoding, and the exception layer produces consistent error responses.

### How to Run

1. Install Java 17, Maven, and PostgreSQL.
2. Create a PostgreSQL database named `account_db`.
3. Optionally set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
4. Run `cd account-service` followed by `mvn spring-boot:run`.
5. Open Swagger UI at `http://localhost:8081/swagger-ui.html`.

### REST APIs

| Method | Endpoint | Purpose |
|--------|----------|---------|
| `POST` | `/register` | Register a new user |
| `POST` | `/login` | Validate user credentials |
| `GET` | `/profile?email={email}` | Retrieve a user profile |

### Future Work

- Add JWT access and refresh tokens.
- Require authenticated access to profile endpoints.
- Add role-based authorization and account lifecycle APIs.
- Add database migrations and containerized integration tests.

---

## License

MIT License
