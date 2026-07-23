# July 31 Project Update - Document Service

## Submission Date

July 31, 2026

## Developer

Vamsi Chowdary Mandhapati

## Sprint Goal

Implement the Document Service foundation for BankingAI Assistant so document metadata can be created, retrieved, updated, archived, and searched through REST APIs.

## Work Completed

- Added `backend-document-service` Spring Boot module.
- Added PostgreSQL configuration for document metadata persistence.
- Added `DocumentMetadata` JPA entity.
- Added `DocumentRepository` using Spring Data JPA.
- Added request/response DTOs for document create, update, list, and search operations.
- Added `DocumentService` and `DocumentServiceImpl`.
- Added `DocumentController` with REST endpoints under `/api/documents`.
- Added validation for title, document type, category, owner email, and summary.
- Added global exception handling for validation and missing document records.
- Added Swagger/OpenAPI configuration.
- Added unit tests for service and controller layers.

## REST APIs Demonstrated

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/documents` | Create document metadata |
| GET | `/api/documents` | List document metadata |
| GET | `/api/documents/{id}` | Retrieve document metadata by id |
| PUT | `/api/documents/{id}` | Update document metadata |
| DELETE | `/api/documents/{id}` | Archive document metadata |
| POST | `/api/documents/search` | Search document metadata |

## Code Snippets to Show

### Document Entity

```java
@Entity
@Table(name = "document_metadata")
public class DocumentMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String documentType;
}
```

### Repository

```java
public interface DocumentRepository extends JpaRepository<DocumentMetadata, Long> {
    List<DocumentMetadata> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSummaryContainingIgnoreCase(
            String titleKeyword,
            String categoryKeyword,
            String summaryKeyword
    );
}
```

### Service Method

```java
public DocumentResponse createDocument(DocumentRequest request) {
    DocumentMetadata document = DocumentMapper.toEntity(request);
    return DocumentMapper.toResponse(documentRepository.save(document));
}
```

### Controller Endpoint

```java
@PostMapping
public ResponseEntity<DocumentResponse> createDocument(@Valid @RequestBody DocumentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(request));
}
```

## Validation

- Maven unit tests executed for `backend-document-service`.
- Swagger UI used to confirm REST API visibility.
- Document metadata endpoints tested locally.

## Screenshots Checklist

- GitHub repository home
- GitHub commit history
- Document Service package structure
- `DocumentMetadata.java`
- `DocumentRepository.java`
- `DocumentController.java`
- Swagger UI with Document APIs
- API testing response for `/api/documents`
- Application running proof
- July 31 project update document

## Future Work

- Add file upload support with multipart requests.
- Add cloud object storage integration.
- Add OCR and text extraction pipeline.
- Add vector indexing for retrieval-augmented generation.
- Add JWT and role-based authorization for document endpoints.
