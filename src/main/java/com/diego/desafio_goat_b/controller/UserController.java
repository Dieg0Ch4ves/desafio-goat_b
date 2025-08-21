package com.diego.desafio_goat_b.controller;

import com.diego.desafio_goat_b.dto.*;
import com.diego.desafio_goat_b.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class UserController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO data) {
        AuthenticationResponseDTO response = authService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO data) {
        String message = authService.register(data);
        return ResponseEntity.status(201).body(message);
    }

    @PatchMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam("token") String token) {
        String message = authService.activate(token);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUserFromToken(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        UserResponseDTO dto = authService.getUserFromToken(token);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UserResponseDTO dto = authService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id) {
        String message = authService.deleteUserById(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDTO data) {
        String message = authService.forgotPassword(data);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO data) {
        String message = authService.resetPassword(data);
        return ResponseEntity.ok(message);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
