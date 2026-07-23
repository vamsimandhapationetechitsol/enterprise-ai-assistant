package com.banking.ai.document.service;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentSearchRequest;
import com.banking.ai.document.entity.DocumentMetadata;
import com.banking.ai.document.repository.DocumentRepository;
import com.banking.ai.document.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock DocumentRepository documentRepository;

    @Test
    void createsDocumentMetadataWithNormalizedOwnerEmail() {
        DocumentService service = new DocumentServiceImpl(documentRepository);
        DocumentRequest request = new DocumentRequest(
                "Quarterly Report",
                "PDF",
                "financial",
                " Manager@BankingAI.Local ",
                "Quarterly financial performance summary",
                List.of("quarterly", "finance")
        );
        when(documentRepository.save(any(DocumentMetadata.class))).thenAnswer(invocation -> {
            DocumentMetadata document = invocation.getArgument(0);
            document.setId(1L);
            return document;
        });

        service.createDocument(request);

        ArgumentCaptor<DocumentMetadata> captor = ArgumentCaptor.forClass(DocumentMetadata.class);
        verify(documentRepository).save(captor.capture());
        assertThat(captor.getValue().getOwnerEmail()).isEqualTo("manager@bankingai.local");
        assertThat(captor.getValue().getStatus()).isEqualTo(DocumentMetadata.Status.INDEXED);
    }

    @Test
    void searchesDocumentsByKeyword() {
        DocumentMetadata document = new DocumentMetadata();
        document.setId(1L);
        document.setTitle("Loan Policy");
        document.setDocumentType("PDF");
        document.setCategory("policy");
        document.setOwnerEmail("manager@bankingai.local");
        document.setSummary("Loan eligibility policy");

        when(documentRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrSummaryContainingIgnoreCase(
                "loan", "loan", "loan")).thenReturn(List.of(document));

        DocumentService service = new DocumentServiceImpl(documentRepository);

        assertThat(service.searchDocuments(new DocumentSearchRequest("loan")))
                .hasSize(1)
                .first()
                .extracting("title", "category")
                .containsExactly("Loan Policy", "policy");
    }

    @Test
    void archivesDocumentInsteadOfDeletingMetadata() {
        DocumentMetadata document = new DocumentMetadata();
        document.setId(1L);
        document.setTitle("Archive Test");
        document.setDocumentType("PDF");
        document.setCategory("policy");
        document.setOwnerEmail("manager@bankingai.local");

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        DocumentService service = new DocumentServiceImpl(documentRepository);
        service.deleteDocument(1L);

        assertThat(document.getStatus()).isEqualTo(DocumentMetadata.Status.ARCHIVED);
        verify(documentRepository).save(document);
    }
}
