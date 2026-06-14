# Architecture

## Overview

Enterprise AI Assistant is a microservices-based RAG platform designed for enterprise knowledge access. The system supports secure document ingestion, semantic search, conversational AI, and a React-based user experience.

---

## Planned Technology Stack

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

## Core Services

### Authentication Service
**Path:** `backend-auth-service`

**Responsibilities:**
- User registration
- Login
- JWT token generation
- Role management

### Document Service
**Path:** `backend-document-service`

**Responsibilities:**
- Upload documents
- Store metadata
- Search content
- Document indexing

### Chat Service
**Path:** `backend-chat-service`

**Responsibilities:**
- AI interactions
- Context retrieval
- Prompt orchestration
- Response generation

### Frontend Application
**Path:** `frontend-react`

**Responsibilities:**
- User dashboard
- Search interface
- Chat interface
- Authentication pages

---

## Data Flow

1. User authenticates via the Authentication Service
2. User uploads documents through the Document Service
3. Document text is extracted, chunked, and embedded
4. Embeddings are stored in PostgreSQL with pgvector
5. User submits queries through the Frontend
6. Chat Service retrieves relevant chunks using vector search
7. Retrieved context is sent to OpenAI GPT-4
8. Response is returned to the user with document references
9. All activity is logged for audit purposes

---

## Deployment Architecture

### Local Development
- Docker Compose orchestrates all services
- PostgreSQL with pgvector extension for vector search
- OpenAI API for LLM interactions

### Production (AWS)
- Kubernetes cluster for container orchestration
- Amazon RDS for PostgreSQL
- Amazon EKS for Kubernetes management
- AWS S3 for document storage
- AWS Secrets Manager for credential management

---

## Service Communication

- Services communicate via REST APIs
- Internal service calls are authenticated using JWT tokens
- API Gateway pattern planned for centralized routing

---

## Design Decisions

| Decision | Rationale |
|----------|----------|
| Microservices architecture | Independent scaling, clear service boundaries |
| PostgreSQL with pgvector | Native vector search without external dependencies |
| Spring Boot | Java ecosystem, security features, enterprise maturity |
| React + TypeScript | Strong typing, component reusability, large ecosystem |
| Docker | Consistent environments across dev/staging/production |
| Kubernetes | Production-grade orchestration and scaling |
| AWS | Reliable cloud infrastructure with managed services |

---

## Next Steps (Week 2)

- Finalize architecture design
- Create API specifications
- Design PostgreSQL schema
- Define pgvector indexing strategy
- Design authentication flow
- Create Architecture Diagram
