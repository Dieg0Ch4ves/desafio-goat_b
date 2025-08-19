package com.diego.desafio_goat_b.repository;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    List<Supplier> findByNameContainingIgnoreCase(String name);

    boolean existsByTaxId(String taxId);
}

