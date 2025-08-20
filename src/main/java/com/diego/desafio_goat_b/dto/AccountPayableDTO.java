package com.diego.desafio_goat_b.dto;

import com.diego.desafio_goat_b.domain.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AccountPayableDTO(
        UUID id,
        @NotNull UUID supplierId,
        String description,
        String category,
        BigDecimal originalAmount,
        BigDecimal paidAmount,
        LocalDate issueDate,
        LocalDate dueDate,
        AccountStatus status,
        String notes,
        List<PaymentDTO> payments
) {
}
