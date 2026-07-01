package com.banking.ai.account.service;

import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.entity.User;
import com.banking.ai.account.repository.UserRepository;
import com.banking.ai.account.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;

    @Test
    void registersUserWithNormalizedEmailAndEncodedPassword() {
        UserService service = new UserServiceImpl(userRepository, passwordEncoder);
        RegisterRequest request = new RegisterRequest("Vamsi", "Mandhapati", " VAMSI@example.com ", "password123");
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserResponse response = service.registerUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getEmail()).isEqualTo("vamsi@example.com");
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("encoded");
        assertThat(response.id()).isEqualTo(1L);
    }
}
