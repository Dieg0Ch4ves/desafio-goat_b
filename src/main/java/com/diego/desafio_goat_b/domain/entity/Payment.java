package com.diego.desafio_goat_b.domain.entity;

import com.diego.desafio_goat_b.domain.base.AuditableEntity;
import com.diego.desafio_goat_b.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(optional = false)
    private AccountPayable account;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 15, fraction = 2)
    @Column(name = "amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 20)
    private PaymentMethod method;

    @Size(max = 300)
    @Column(name = "note", length = 300)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private User processedBy;
}
