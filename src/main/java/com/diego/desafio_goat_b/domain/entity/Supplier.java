package com.diego.desafio_goat_b.domain.entity;

import com.diego.desafio_goat_b.domain.base.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "suppliers")
public class Supplier extends AuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Size(max = 180)
    @Column(name = "name", nullable = false, length = 180)
    private String name;

    @NotBlank
    @Size(max = 32)
    @Column(name = "tax_id", nullable = false, length = 32)
    private String taxId;

    @Size(max = 180)
    @Column(name = "email", length = 180)
    private String email;

    @Size(max = 40)
    @Column(name = "phone", length = 40)
    private String phone;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<AccountPayable> accounts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;


}

