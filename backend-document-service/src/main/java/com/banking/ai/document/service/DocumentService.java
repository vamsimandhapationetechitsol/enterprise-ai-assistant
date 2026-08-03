package com.banking.ai.document.service;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentResponse;
import com.banking.ai.document.dto.DocumentSearchRequest;
import com.banking.ai.document.entity.DocumentMetadata;

import java.util.List;

public interface DocumentService {

    DocumentResponse createDocument(DocumentRequest request);

    List<DocumentResponse> getDocuments(DocumentMetadata.Status status);

    DocumentResponse getDocument(Long id);

    DocumentResponse updateDocument(Long id, DocumentRequest request);

    void deleteDocument(Long id);

    DocumentResponse restoreDocument(Long id);

    List<DocumentResponse> searchDocuments(DocumentSearchRequest request);
}
