package com.banking.ai.account.controller;

import com.banking.ai.account.dto.LoginRequest;
import com.banking.ai.account.dto.RegisterRequest;
import com.banking.ai.account.dto.RegisterResponse;
import com.banking.ai.account.dto.UserResponse;
import com.banking.ai.account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse("User registered successfully", user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        if (!userService.verifyPassword(request.email(), request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.findUserByEmail(request.email()));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }
}
