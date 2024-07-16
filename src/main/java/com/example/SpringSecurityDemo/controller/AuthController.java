package com.example.SpringSecurityDemo.controller;


import com.example.SpringSecurityDemo.dto.JwtAuthenticationResponse;
import com.example.SpringSecurityDemo.dto.SignInRequest;
import com.example.SpringSecurityDemo.dto.SignUpRequest;
import com.example.SpringSecurityDemo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public HttpEntity<?> signUp(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signUpRequest(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public HttpEntity<?> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signInRequest(request));
    }
}
