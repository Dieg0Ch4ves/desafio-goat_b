package com.diego.desafio_goat_b.domain.entity;

import com.diego.desafio_goat_b.domain.base.AuditableEntity;
import com.diego.desafio_goat_b.domain.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts_payable")
public class AccountPayable extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(optional = false)
    private Supplier supplier;

    @NotBlank
    @Size(max = 200)
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Size(max = 60)
    @Column(name = "category", length = 60)
    private String category;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 15, fraction = 2)
    @Column(name = "original_amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal originalAmount;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 15, fraction = 2)
    @Column(name = "paid_amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal paidAmount;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    @Size(max = 500)
    @Column(name = "notes", length = 500)
    private String notes;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    public BigDecimal getOutstandingAmount() {
        return originalAmount.subtract(paidAmount == null ? BigDecimal.ZERO : paidAmount);
    }
}