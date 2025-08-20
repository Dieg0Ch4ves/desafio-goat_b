package com.diego.desafio_goat_b.dto;

import java.util.UUID;

public record SupplierDTO(
        UUID id,
        String name,
        String taxId,
        String email,
        String phone
) {
}
