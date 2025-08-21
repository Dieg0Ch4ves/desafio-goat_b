package com.diego.desafio_goat_b.dto;

public record ResetPasswordRequestDTO(String token, String newPassword) {
}