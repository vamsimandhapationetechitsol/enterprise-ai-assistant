package com.banking.ai.document.service.impl;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentResponse;
import com.banking.ai.document.dto.DocumentSearchRequest;
import com.banking.ai.document.entity.DocumentMetadata;
import com.banking.ai.document.exception.DocumentNotFoundException;
import com.banking.ai.document.mapper.DocumentMapper;
import com.banking.ai.document.repository.DocumentRepository;
import com.banking.ai.document.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<DocumentResponse> getDocuments() {
        return documentRepository.findAll()
                .stream()
                .map(DocumentMapper::toResponse)
                .toList();
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

    private DocumentMetadata findEntity(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));
    }
}
