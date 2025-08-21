package com.diego.desafio_goat_b.dto;

import com.diego.desafio_goat_b.domain.enums.UserRole;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email, UserRole role) {

}
