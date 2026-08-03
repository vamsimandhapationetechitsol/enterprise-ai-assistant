package com.banking.ai.document.controller;

import com.banking.ai.document.dto.DocumentResponse;
import com.banking.ai.document.entity.DocumentMetadata;
import com.banking.ai.document.exception.GlobalExceptionHandler;
import com.banking.ai.document.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    @Mock DocumentService documentService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DocumentController(documentService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createDocumentReturnsCreatedMetadata() throws Exception {
        DocumentResponse response = new DocumentResponse(
                1L,
                "Quarterly Report",
                "PDF",
                "financial",
                "manager@bankingai.local",
                "Quarterly financial performance summary",
                List.of("quarterly", "finance"),
                DocumentMetadata.Status.INDEXED,
                null,
                null
        );
        when(documentService.createDocument(any())).thenReturn(response);

        String request = objectMapper.writeValueAsString(new DocumentPayload(
                "Quarterly Report",
                "PDF",
                "financial",
                "manager@bankingai.local",
                "Quarterly financial performance summary",
                List.of("quarterly", "finance")
        ));

        mockMvc.perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Quarterly Report"))
                .andExpect(jsonPath("$.status").value("INDEXED"));
    }

    @Test
    void restoreDocumentReturnsIndexedMetadata() throws Exception {
        DocumentResponse response = new DocumentResponse(
                1L, "Quarterly Report", "PDF", "financial", "manager@bankingai.local",
                "Quarterly financial performance summary", List.of("quarterly"),
                DocumentMetadata.Status.INDEXED, null, null);
        when(documentService.restoreDocument(1L)).thenReturn(response);

        mockMvc.perform(put("/api/documents/1/restore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("INDEXED"));
    }

    private record DocumentPayload(
            String title,
            String documentType,
            String category,
            String ownerEmail,
            String summary,
            List<String> tags
    ) {
    }
}
