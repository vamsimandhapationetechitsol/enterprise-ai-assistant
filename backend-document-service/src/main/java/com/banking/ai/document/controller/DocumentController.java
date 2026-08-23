package com.banking.ai.document.controller;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentPageResponse;
import com.banking.ai.document.dto.DocumentResponse;
import com.banking.ai.document.dto.DocumentSearchRequest;
import com.banking.ai.document.dto.DocumentStatusSummary;
import com.banking.ai.document.entity.DocumentMetadata;
import com.banking.ai.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@Tag(name = "Documents", description = "Document metadata CRUD and search APIs")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    @Operation(summary = "Create document metadata")
    public ResponseEntity<DocumentResponse> createDocument(@Valid @RequestBody DocumentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(request));
    }

    @GetMapping
    @Operation(summary = "List document metadata, optionally filtered by status and category")
    public ResponseEntity<List<DocumentResponse>> getDocuments(
            @RequestParam(required = false) DocumentMetadata.Status status,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(documentService.getDocuments(status, category));
    }

    @GetMapping("/page")
    @Operation(summary = "List document metadata in pages with optional status and category filters")
    public ResponseEntity<DocumentPageResponse> getDocumentsPage(
            @RequestParam(required = false) DocumentMetadata.Status status,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(documentService.getDocumentsPage(status, category, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get document metadata by id")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update document metadata")
    public ResponseEntity<DocumentResponse> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(documentService.updateDocument(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Archive document metadata")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/restore")
    @Operation(summary = "Restore an archived document")
    public ResponseEntity<DocumentResponse> restoreDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.restoreDocument(id));
    }

    @PostMapping("/search")
    @Operation(summary = "Search document metadata")
    public ResponseEntity<List<DocumentResponse>> searchDocuments(@Valid @RequestBody DocumentSearchRequest request) {
        return ResponseEntity.ok(documentService.searchDocuments(request));
    }

    @GetMapping("/summary")
    @Operation(summary = "Get document counts by lifecycle status")
    public ResponseEntity<DocumentStatusSummary> getStatusSummary() {
        return ResponseEntity.ok(documentService.getStatusSummary());
    }
}
