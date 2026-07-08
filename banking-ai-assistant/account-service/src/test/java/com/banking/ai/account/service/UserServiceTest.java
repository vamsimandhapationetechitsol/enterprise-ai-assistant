package com.banking.ai.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ai.account.dto.LoginRequest;
import com.banking.ai.account.dto.LoginResponse;
import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.RegisterResponse;
import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.entity.User;
import com.banking.ai.account.exception.EmailAlreadyExistsException;
import com.banking.ai.account.exception.InvalidCredentialsException;
import com.banking.ai.account.mapper.UserMapper;
import com.banking.ai.account.repository.UserRepository;
import com.banking.ai.account.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditService auditService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, new UserMapper(), auditService);
    }

    @Test
    void registerCreatesUserWithEncodedPassword() {
        RegisterRequest request = registerRequest();
        when(userRepository.existsByEmail("vamsi@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            user.setCreatedDate(LocalDateTime.now());
            user.setUpdatedDate(LocalDateTime.now());
            return user;
        });

        RegisterResponse response = userService.register(request);

        assertThat(response.getMessage()).isEqualTo("User registered successfully");
        assertThat(response.getUser().getEmail()).isEqualTo("vamsi@example.com");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getPassword()).isNotEqualTo("secret123");
        verify(auditService).log(eq("REGISTER"), eq("vamsi@example.com"), any(String.class));
    }

    @Test
    void registerRejectsDuplicateEmail() {
        RegisterRequest request = registerRequest();
        when(userRepository.existsByEmail("vamsi@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void loginReturnsTokenForValidCredentials() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = user();
        user.setPassword(encoder.encode("secret123"));
        when(userRepository.findByEmail("vamsi@example.com")).thenReturn(Optional.of(user));

        LoginResponse response = userService.login(loginRequest("secret123"));

        assertThat(response.getMessage()).isEqualTo("Login successful");
        assertThat(response.getToken()).isNotBlank();
        assertThat(response.getUser().getEmail()).isEqualTo("vamsi@example.com");
        verify(auditService).log(eq("LOGIN"), eq("vamsi@example.com"), any(String.class));
    }

    @Test
    void loginRejectsInvalidCredentials() {
        User user = user();
        user.setPassword(new BCryptPasswordEncoder().encode("secret123"));
        when(userRepository.findByEmail("vamsi@example.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.login(loginRequest("wrongpass")))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void getProfileReturnsUserResponse() {
        when(userRepository.findByEmail("vamsi@example.com")).thenReturn(Optional.of(user()));

        UserResponse response = userService.getProfile("vamsi@example.com");

        assertThat(response.getFirstName()).isEqualTo("Vamsi");
        assertThat(response.getEmail()).isEqualTo("vamsi@example.com");
    }

    private RegisterRequest registerRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Vamsi");
        request.setLastName("Mandhapati");
        request.setEmail("Vamsi@Example.com");
        request.setPassword("secret123");
        request.setRole("USER");
        return request;
    }

    private LoginRequest loginRequest(String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail("vamsi@example.com");
        request.setPassword(password);
        return request;
    }

    private User user() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Vamsi");
        user.setLastName("Mandhapati");
        user.setEmail("vamsi@example.com");
        user.setRole("USER");
        user.setStatus(true);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return user;
    }
}
