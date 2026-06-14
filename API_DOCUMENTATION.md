# API Documentation

## Overview

This document defines the API contracts and endpoint specifications for BankingAI Assistant. All endpoints are planned and will be finalized during API design in Week 2.

---

## Authentication Service

**Base Path:** `/api/auth`

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Register a new user account |
| POST | `/login` | Authenticate a user and return a JWT token |
| POST | `/logout` | End a user session and invalidate the token |
| GET | `/me` | Return the current authenticated user profile and roles |

### Request/Response Examples

**POST /login**
```json
{
  "email": "user@example.com",
  "password": "********"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 3600
}
```

---

## Document Service

**Base Path:** `/api/documents`

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Upload a document for ingestion |
| GET | `/` | List all indexed documents |
| GET | `/{id}` | Retrieve document metadata |
| DELETE | `/{id}` | Remove a document from the index |
| POST | `/search` | Search documents by content or metadata |

### Document Upload Format

```json
{
  "title": "Quarterly Report",
  "type": "PDF",
  "content": "base64_encoded_content",
  "category": "financial",
  "tags": ["quarterly", "finance", "2026"]
}
```

---

## Chat Service

**Base Path:** `/api/chat`

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/sessions` | Create a new chat session |
| GET | `/sessions/{id}` | Retrieve a chat session history |
| POST | `/sessions/{id}/messages` | Submit a user question and receive an AI response |
| DELETE | `/sessions/{id}` | Delete a chat session |

### Chat Message Format

**Request:**
```json
{
  "message": "Summarize the quarterly report",
  "context": ["doc_123", "doc_456"]
}
```

**Response:**
```json
{
  "response": "The quarterly report shows...",
  "sources": [
    {"docId": "doc_123", "title": "Quarterly Report"},
    {"docId": "doc_456", "title": "Financial Summary"}
  ]
}
```

---

## Audit and Observability

**Base Path:** `/api`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/audit/events` | Review user and system activity logs |
| GET | `/health` | Service health check endpoint |
| GET | `/metrics` | Service metrics for monitoring (Prometheus format) |

---

## Security

- All endpoints except health require JWT authentication
- Tokens are passed via Authorization header: `Bearer <token>`
- Role-based access control applied to document and admin endpoints
- All API communication over HTTPS

---

## Notes

- This document will be updated in Week 2 with final API specifications
- OpenAPI/Swagger documentation will be generated from the codebase
- All timestamps returned in ISO 8601 format
