package com.diego.desafio_goat_b.dto;

import com.diego.desafio_goat_b.domain.enums.UserRole;

public record RegisterRequestDTO(String name, String email, String password, UserRole role) {

}