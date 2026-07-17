# BankingAI Assistant

Enterprise AI-powered Banking Assistant built using Java Spring Boot Microservices, React, PostgreSQL, OpenAI, and Retrieval-Augmented Generation (RAG).

---

# Project Overview

**Project Name:** BankingAI Assistant

**Developer:** Vamsi Chowdary Mandhapati

**Project Duration:** 5 Months

**Current Phase:** – User Management & Microservices Foundation

---

# Project Objective

BankingAI Assistant is an enterprise-grade AI platform designed to help banking professionals securely search, retrieve, summarize, and interact with financial documents using Large Language Models (LLMs), Retrieval-Augmented Generation (RAG), and cloud-native microservices.

The platform is designed with scalability, security, and maintainability in mind using modern enterprise architecture patterns.

---

# Technology Stack

## Backend

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Spring Cloud
- Spring Cloud Gateway
- Netflix Eureka
- OpenFeign
- Maven

## Frontend

- React
- TypeScript
- Material UI
- Axios

## Database

- PostgreSQL
- pgvector
- MongoDB (planned)

## AI & GenAI

- OpenAI GPT
- Retrieval-Augmented Generation (RAG)
- Vector Search
- Prompt Engineering

## Infrastructure

- Docker
- Kubernetes
- AWS
- GitHub Actions (planned)

---

# Current Project Structure

```
banking-ai-assistant
│
├── account-service
├── auth-service
├── transaction-service
├── ai-service
├── gateway-service
├── eureka-server
│
├── docs
│
├── docker
│
├── kubernetes
│
└── README.md
```

---

# Architecture

```
                    React Frontend

                           │

                    API Gateway

                           │

      -----------------------------------------

      │           │            │             │

 Auth Service  Account    Transaction    AI Service

                Service      Service

      │

 PostgreSQL

      │

 OpenAI + RAG

      │

 Vector Database (Future)
```

---

# Features Implemented

## Week 1

- Project planning
- Repository setup
- Folder structure
- Documentation
- Architecture planning

---

## Week 2

- High-level system design
- Database design
- Authentication workflow
- Service interaction design
- Sprint backlog

---

## Week 3

- Multi-module Maven project
- Spring Boot microservices initialization
- Eureka Discovery Server
- API Gateway module
- Account Service
- Auth Service
- AI Service
- Transaction Service
- Spring Cloud configuration

---

## Week 4

- User Management module foundation
- JPA entity design
- Repository layer
- Service layer
- REST controller
- Exception handling
- Validation
- PostgreSQL configuration
- Swagger/OpenAPI setup
- Technical documentation updates

---

## July 17 Project Update

- PostgreSQL-backed audit logging foundation
- AuditLog JPA entity and repository
- Audit service layer for storing and retrieving user activity
- Audit REST API: `GET /api/audit`
- Audit logging integrated with registration, login, profile lookup, and user update flows
- Unit tests added for audit service behavior
- Manager submission notes added in `JULY_17_PROJECT_UPDATE.md`

---

# Services

## Eureka Server

Responsible for service discovery and registration.

---

## API Gateway

Single entry point for all client requests.

---

## Auth Service

Handles:

- User Registration
- Login
- JWT Authentication (In Progress)
- Authorization

---

## Account Service

Handles:

- Customer Accounts
- Profile Management
- Account Operations

---

## Transaction Service

Handles:

- Banking Transactions
- Transaction History
- Payment Operations

---

## AI Service

Handles:

- Chat Assistant
- Document Summarization
- Retrieval-Augmented Generation
- OpenAI Integration

---

# Documentation

| Document | Purpose |
|----------|---------|
| README.md | Project overview |
| PROJECT_STATUS.md | Weekly project progress |
| ARCHITECTURE.md | System architecture |
| API_DOCUMENTATION.md | REST API specifications |
| DATABASE_DESIGN.md | Database schema |
| AUTHENTICATION_FLOW.md | Authentication workflow |
| SERVICE_INTERACTIONS.md | Service communication |
| SPRINT_1_BACKLOG.md | Sprint planning |
| CHANGELOG.md | Repository changes |

---

# Build

```bash
mvn clean install
```

---

# Run Eureka Server

```bash
cd eureka-server

mvn spring-boot:run
```

---

# Run Account Service

```bash
cd account-service

mvn spring-boot:run
```

---

# Planned REST APIs

## Authentication

```
POST /api/auth/register

POST /api/auth/login

GET /api/auth/profile
```

## Account

```
GET /api/accounts

POST /api/accounts

PUT /api/accounts/{id}
```

## Transactions

```
GET /api/transactions

POST /api/transactions
```

## AI

```
POST /api/chat/query

GET /api/chat/history
```

---

# Current Progress

| Week | Status |
|-------|--------|
| Week 1 | ✅ Completed |
| Week 2 | ✅ Completed |
| Week 3 | ✅ Completed |
| Week 4 | ✅ Completed |
| July 17 Project Update | ✅ Completed |

Overall Project Progress: **35%**

---

# Upcoming Milestones

## Week 5

- JWT Authentication
- Password Encryption
- Login API

## Week 6

- Role-Based Authorization
- Security Filters

## PostgreSQL Integration and CRUD Operations

- PostgreSQL Integration
- CRUD Operations

## Week 8

- Document Service

## Week 9

- AI Chat Service

## Week 10

- OpenAI Integration

## Week 11–20

- Kafka
- Redis
- Docker
- Kubernetes
- AWS Deployment
- CI/CD
- Monitoring
- Production Release

---

# Development Methodology

- Agile Scrum
- Weekly Sprint Planning
- GitHub Issues
- Feature Branch Workflow
- Pull Requests
- Code Reviews
- Weekly Progress Reports

---

# License

MIT License

---

## Author

**Vamsi Chowdary Mandhapati**

Enterprise Java Full Stack & Generative AI Developer
