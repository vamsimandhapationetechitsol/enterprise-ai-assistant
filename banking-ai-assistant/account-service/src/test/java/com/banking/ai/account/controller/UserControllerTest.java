package com.banking.ai.account.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.banking.ai.account.dto.LoginResponse;
import com.banking.ai.account.dto.RegisterResponse;
import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.exception.GlobalExceptionHandler;
import com.banking.ai.account.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void registerReturnsOk() throws Exception {
        when(userService.register(any())).thenReturn(new RegisterResponse("User registered successfully", userResponse()));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerPayload())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.user.email").value("vamsi@example.com"));
    }

    @Test
    void loginReturnsOk() throws Exception {
        when(userService.login(any())).thenReturn(new LoginResponse("Login successful", "token-value", userResponse()));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"vamsi@example.com\",\"password\":\"secret123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.token").value("token-value"));
    }

    @Test
    void profileReturnsOk() throws Exception {
        when(userService.getProfile(eq("vamsi@example.com"))).thenReturn(userResponse());

        mockMvc.perform(get("/profile").param("email", "vamsi@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Vamsi"))
                .andExpect(jsonPath("$.email").value("vamsi@example.com"));
    }

    @Test
    void registerValidatesRequest() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"bad-email\",\"password\":\"123\"}"))
                .andExpect(status().isBadRequest());
    }

    private UserResponse userResponse() {
        return new UserResponse(
                1L,
                "Vamsi",
                "Mandhapati",
                "vamsi@example.com",
                "USER",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true);
    }

    private Object registerPayload() {
        return new Object() {
            public final String firstName = "Vamsi";
            public final String lastName = "Mandhapati";
            public final String email = "vamsi@example.com";
            public final String password = "secret123";
            public final String role = "USER";
        };
    }
}
