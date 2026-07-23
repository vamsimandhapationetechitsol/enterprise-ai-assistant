package com.banking.ai.document.repository;

import com.banking.ai.document.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentMetadata, Long> {

    List<DocumentMetadata> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSummaryContainingIgnoreCase(
            String titleKeyword,
            String categoryKeyword,
            String summaryKeyword
    );
}
