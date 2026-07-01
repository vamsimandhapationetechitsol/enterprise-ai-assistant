package com.banking.ai.account.controller;

import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.entity.User;
import com.banking.ai.account.exception.GlobalExceptionHandler;
import com.banking.ai.account.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock UserService userService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(userService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void registerReturnsCreatedUser() throws Exception {
        UserResponse response = new UserResponse(1L, "Vamsi", "Mandhapati", "vamsi@example.com",
                User.Role.USER, null, null, User.Status.ACTIVE);
        when(userService.registerUser(any())).thenReturn(response);

        String request = objectMapper.writeValueAsString(new RegisterPayload(
                "Vamsi", "Mandhapati", "vamsi@example.com", "password123"));

        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.user.email").value("vamsi@example.com"));
    }

    private record RegisterPayload(String firstName, String lastName, String email, String password) { }
}
