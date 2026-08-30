package com.banking.ai.document.service;

import com.banking.ai.document.dto.DocumentRequest;
import com.banking.ai.document.dto.DocumentSearchRequest;
import com.banking.ai.document.dto.DocumentSortField;
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

    @Test
    void restoresArchivedDocumentToIndexedStatus() {
        DocumentMetadata document = new DocumentMetadata();
        document.setId(1L);
        document.setStatus(DocumentMetadata.Status.ARCHIVED);
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(document)).thenReturn(document);

        DocumentService service = new DocumentServiceImpl(documentRepository);
        service.restoreDocument(1L);

        assertThat(document.getStatus()).isEqualTo(DocumentMetadata.Status.INDEXED);
        verify(documentRepository).save(document);
    }

    @Test
    void filtersDocumentsByStatusWhenRequested() {
        DocumentMetadata document = new DocumentMetadata();
        document.setId(1L);
        document.setTitle("Archived Policy");
        document.setDocumentType("PDF");
        document.setCategory("policy");
        document.setOwnerEmail("manager@bankingai.local");
        document.setStatus(DocumentMetadata.Status.ARCHIVED);
        when(documentRepository.findByStatus(DocumentMetadata.Status.ARCHIVED)).thenReturn(List.of(document));

        DocumentService service = new DocumentServiceImpl(documentRepository);

        assertThat(service.getDocuments(DocumentMetadata.Status.ARCHIVED, null))
                .singleElement()
                .extracting("status")
                .isEqualTo(DocumentMetadata.Status.ARCHIVED);
    }

    @Test
    void filtersDocumentsByCategoryWhenRequested() {
        DocumentMetadata document = new DocumentMetadata();
        document.setId(1L);
        document.setTitle("Financial Report");
        document.setDocumentType("PDF");
        document.setCategory("financial");
        document.setOwnerEmail("manager@bankingai.local");
        when(documentRepository.findByCategoryIgnoreCase("financial")).thenReturn(List.of(document));

        DocumentService service = new DocumentServiceImpl(documentRepository);

        assertThat(service.getDocuments(null, " financial "))
                .singleElement()
                .extracting("category")
                .isEqualTo("financial");
    }

    @Test
    void returnsARequestedDocumentPage() {
        DocumentMetadata first = new DocumentMetadata();
        first.setId(1L); first.setTitle("First"); first.setDocumentType("PDF"); first.setCategory("policy"); first.setOwnerEmail("manager@bankingai.local");
        DocumentMetadata second = new DocumentMetadata();
        second.setId(2L); second.setTitle("Second"); second.setDocumentType("PDF"); second.setCategory("policy"); second.setOwnerEmail("manager@bankingai.local");
        when(documentRepository.findAll()).thenReturn(List.of(first, second));

        DocumentService service = new DocumentServiceImpl(documentRepository);

        assertThat(service.getDocumentsPage(null, null, 1, 1, DocumentSortField.TITLE, false))
                .extracting("page", "size", "totalElements", "totalPages")
                .containsExactly(1, 1, 2L, 2);
    }

    @Test
    void sortsDocumentPagesByTitleInDescendingOrder() {
        DocumentMetadata alpha = new DocumentMetadata();
        alpha.setId(1L); alpha.setTitle("Alpha"); alpha.setDocumentType("PDF"); alpha.setCategory("policy"); alpha.setOwnerEmail("manager@bankingai.local");
        DocumentMetadata zebra = new DocumentMetadata();
        zebra.setId(2L); zebra.setTitle("Zebra"); zebra.setDocumentType("PDF"); zebra.setCategory("policy"); zebra.setOwnerEmail("manager@bankingai.local");
        when(documentRepository.findAll()).thenReturn(List.of(alpha, zebra));

        DocumentService service = new DocumentServiceImpl(documentRepository);

        assertThat(service.getDocumentsPage(null, null, 0, 20, DocumentSortField.TITLE, true).documents())
                .extracting("title")
                .containsExactly("Zebra", "Alpha");
    }

    @Test
    void returnsDocumentCountsByStatus() {
        when(documentRepository.countByStatus(DocumentMetadata.Status.UPLOADED)).thenReturn(2L);
        when(documentRepository.countByStatus(DocumentMetadata.Status.INDEXED)).thenReturn(8L);
        when(documentRepository.countByStatus(DocumentMetadata.Status.ARCHIVED)).thenReturn(1L);

        DocumentService service = new DocumentServiceImpl(documentRepository);

        assertThat(service.getStatusSummary())
                .extracting("uploaded", "indexed", "archived")
                .containsExactly(2L, 8L, 1L);
    }
}
