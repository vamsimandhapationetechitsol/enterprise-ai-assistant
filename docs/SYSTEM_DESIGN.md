# BankingAI Assistant - System Design

## High Level Architecture

Frontend
- React Dashboard

Backend Microservices

1. Authentication Service
2. Document Service
3. AI Chat Service

Database
- PostgreSQL
- pgvector

External Integrations
- OpenAI GPT-4

Infrastructure
- Docker
- Kubernetes

## User Flow

User Login

↓

Upload Documents

↓

Documents Indexed

↓

Embedding Generated

↓

Vector Search

↓

AI Response Generated
