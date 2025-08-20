package com.diego.desafio_goat_b.dto;

import com.diego.desafio_goat_b.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentDTO(
        UUID id,
        @NotNull UUID accountId,
        LocalDate paymentDate,
        BigDecimal amount,
        PaymentMethod method,
        String note,
        @NotNull UUID processedBy
) {
}
