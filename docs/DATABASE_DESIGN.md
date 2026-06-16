# Database Design

Users Table

- user_id
- name
- email
- password
- role
- created_at

Documents Table

- document_id
- file_name
- uploaded_by
- upload_date
- document_type

Embeddings Table

- embedding_id
- document_id
- vector_data

Chat History Table

- chat_id
- user_id
- question
- response
- timestamp
