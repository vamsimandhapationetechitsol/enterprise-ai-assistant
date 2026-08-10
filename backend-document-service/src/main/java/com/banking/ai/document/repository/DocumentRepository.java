package com.banking.ai.document.repository;

import com.banking.ai.document.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentMetadata, Long> {

    List<DocumentMetadata> findByStatus(DocumentMetadata.Status status);

    List<DocumentMetadata> findByCategoryIgnoreCase(String category);

    List<DocumentMetadata> findByStatusAndCategoryIgnoreCase(DocumentMetadata.Status status, String category);

    long countByStatus(DocumentMetadata.Status status);

    List<DocumentMetadata> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSummaryContainingIgnoreCase(
            String titleKeyword,
            String categoryKeyword,
            String summaryKeyword
    );
}
