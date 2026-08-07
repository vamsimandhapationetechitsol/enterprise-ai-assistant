# API Documentation

## Overview

This document defines the API contracts and endpoint specifications for BankingAI Assistant. The document endpoints are now backed by the Document Service implementation.

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
| POST | `/` | Create document metadata |
| GET | `/?status={status}` | List document metadata, optionally filtered by `INDEXED`, `UPLOADED`, or `ARCHIVED` status |
| GET | `/{id}` | Retrieve document metadata |
| PUT | `/{id}` | Update document metadata |
| DELETE | `/{id}` | Archive document metadata |
| PUT | `/{id}/restore` | Restore an archived document to `INDEXED` status |
| POST | `/search` | Search documents by content or metadata |
| GET | `/summary` | Get document counts by lifecycle status |

### Document Upload Format

```json
{
  "title": "Quarterly Report",
  "type": "PDF",
  "ownerEmail": "manager@bankingai.local",
  "summary": "Quarterly financial performance summary",
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

- OpenAPI/Swagger documentation will be generated from the codebase
- All timestamps returned in ISO 8601 format
