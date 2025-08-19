package com.diego.desafio_goat_b.repository;

import com.diego.desafio_goat_b.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByAccount_Id(UUID accountId);
}

