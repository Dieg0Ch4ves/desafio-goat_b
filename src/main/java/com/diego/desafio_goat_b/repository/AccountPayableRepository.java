package com.diego.desafio_goat_b.repository;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AccountPayableRepository extends JpaRepository<AccountPayable, UUID> {
    List<AccountPayable> findByStatus(AccountStatus status);
    List<AccountPayable> findByDueDateBetween(LocalDate from, LocalDate to);
    List<AccountPayable> findBySupplier_Id(UUID supplierId);
}
