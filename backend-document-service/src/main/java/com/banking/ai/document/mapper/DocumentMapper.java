package com.banking.ai.document.mapper;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentResponse;
import com.banking.ai.document.entity.DocumentMetadata;

import java.util.ArrayList;
import java.util.List;

public final class DocumentMapper {

    private DocumentMapper() {
    }

    public static DocumentMetadata toEntity(DocumentRequest request) {
        DocumentMetadata document = new DocumentMetadata();
        updateEntity(document, request);
        document.setStatus(DocumentMetadata.Status.INDEXED);
        return document;
    }

    public static void updateEntity(DocumentMetadata document, DocumentRequest request) {
        document.setTitle(request.title());
        document.setDocumentType(request.documentType());
        document.setCategory(request.category());
        document.setOwnerEmail(request.ownerEmail().trim().toLowerCase());
        document.setSummary(request.summary());
        document.setTags(request.tags() == null ? new ArrayList<>() : new ArrayList<>(request.tags()));
    }

    public static DocumentResponse toResponse(DocumentMetadata document) {
        return new DocumentResponse(
                document.getId(),
                document.getTitle(),
                document.getDocumentType(),
                document.getCategory(),
                document.getOwnerEmail(),
                document.getSummary(),
                List.copyOf(document.getTags()),
                document.getStatus(),
                document.getCreatedDate(),
                document.getUpdatedDate()
        );
    }
}
