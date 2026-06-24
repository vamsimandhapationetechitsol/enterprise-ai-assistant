package com.banking.ai.aiservice.service;

import com.banking.ai.aiservice.dto.AiRequest;
import com.banking.ai.aiservice.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

    public AiResponse processRequest(AiRequest request) {
        log.info("Processing AI request: {}", request.getMessage());
        // Placeholder for actual AI/ML integration (e.g., OpenAI, Azure AI)
        String responseText = "AI response to: " + request.getMessage();
        return AiResponse.builder()
                .response(responseText)
                .timestamp(LocalDateTime.now())
                .sessionId(request.getSessionId())
                .build();
    }
}
