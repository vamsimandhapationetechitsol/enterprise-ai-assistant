package com.banking.ai.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiRequest {
    private String message;
    private String sessionId;
    private String userId;
    private String context;
}
