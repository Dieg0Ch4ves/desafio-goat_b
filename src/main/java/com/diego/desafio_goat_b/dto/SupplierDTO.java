package com.diego.desafio_goat_b.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SupplierDTO(
        UUID id,
        @NotNull String name,
        String taxId,
        @NotNull String email,
        @NotNull String phone
) {
}
