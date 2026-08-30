package com.banking.ai.document.service.impl;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentPageResponse;
import com.banking.ai.document.dto.DocumentResponse;
import com.banking.ai.document.dto.DocumentSearchRequest;
import com.banking.ai.document.dto.DocumentStatusSummary;
import com.banking.ai.document.dto.DocumentSortField;
import com.banking.ai.document.entity.DocumentMetadata;
import com.banking.ai.document.exception.DocumentNotFoundException;
import com.banking.ai.document.mapper.DocumentMapper;
import com.banking.ai.document.repository.DocumentRepository;
import com.banking.ai.document.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentResponse createDocument(DocumentRequest request) {
        DocumentMetadata document = DocumentMapper.toEntity(request);
        return DocumentMapper.toResponse(documentRepository.save(document));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocuments(DocumentMetadata.Status status, String category) {
        String normalizedCategory = category == null || category.isBlank() ? null : category.trim();
        List<DocumentMetadata> documents;
        if (status != null && normalizedCategory != null) {
            documents = documentRepository.findByStatusAndCategoryIgnoreCase(status, normalizedCategory);
        } else if (status != null) {
            documents = documentRepository.findByStatus(status);
        } else if (normalizedCategory != null) {
            documents = documentRepository.findByCategoryIgnoreCase(normalizedCategory);
        } else {
            documents = documentRepository.findAll();
        }
        return documents
                .stream()
                .map(DocumentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentPageResponse getDocumentsPage(
            DocumentMetadata.Status status, String category, int page, int size,
            DocumentSortField sortBy, boolean descending) {
        List<DocumentResponse> documents = getDocuments(status, category);
        Comparator<DocumentResponse> comparator = sortBy == DocumentSortField.UPDATED_DATE
                ? Comparator.comparing(DocumentResponse::updatedDate, Comparator.nullsLast(Comparator.naturalOrder()))
                : Comparator.comparing(DocumentResponse::title, String.CASE_INSENSITIVE_ORDER);
        if (descending) {
            comparator = comparator.reversed();
        }
        documents = documents.stream().sorted(comparator).toList();
        int totalElements = documents.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = Math.min(page * size, totalElements);
        int toIndex = Math.min(fromIndex + size, totalElements);
        return new DocumentPageResponse(documents.subList(fromIndex, toIndex), page, size, totalElements, totalPages);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocument(Long id) {
        return DocumentMapper.toResponse(findEntity(id));
    }

    @Override
    public DocumentResponse updateDocument(Long id, DocumentRequest request) {
        DocumentMetadata document = findEntity(id);
        DocumentMapper.updateEntity(document, request);
        return DocumentMapper.toResponse(documentRepository.save(document));
    }

    @Override
    public void deleteDocument(Long id) {
        DocumentMetadata document = findEntity(id);
        document.setStatus(DocumentMetadata.Status.ARCHIVED);
        documentRepository.save(document);
    }

    @Override
    public DocumentResponse restoreDocument(Long id) {
        DocumentMetadata document = findEntity(id);
        document.setStatus(DocumentMetadata.Status.INDEXED);
        return DocumentMapper.toResponse(documentRepository.save(document));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> searchDocuments(DocumentSearchRequest request) {
        String keyword = request.keyword().trim();
        return documentRepository
                .findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSummaryContainingIgnoreCase(
                        keyword, keyword, keyword)
                .stream()
                .map(DocumentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentStatusSummary getStatusSummary() {
        return new DocumentStatusSummary(
                documentRepository.countByStatus(DocumentMetadata.Status.UPLOADED),
                documentRepository.countByStatus(DocumentMetadata.Status.INDEXED),
                documentRepository.countByStatus(DocumentMetadata.Status.ARCHIVED));
    }

    private DocumentMetadata findEntity(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));
    }
}
