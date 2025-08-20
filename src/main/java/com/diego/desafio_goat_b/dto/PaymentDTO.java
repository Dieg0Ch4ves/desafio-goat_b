package com.diego.desafio_goat_b.dto;

import com.diego.desafio_goat_b.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentDTO(
        UUID id,
        UUID accountId,
        LocalDate paymentDate,
        BigDecimal amount,
        PaymentMethod method,
        String note,
        UUID processedBy
) {
}
