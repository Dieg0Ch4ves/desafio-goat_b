package com.diego.desafio_goat_b.config.seed;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.domain.enums.AccountStatus;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.dto.SupplierDTO;
import com.diego.desafio_goat_b.repository.AccountPayableRepository;
import com.diego.desafio_goat_b.repository.SupplierRepository;
import com.diego.desafio_goat_b.service.AccountPayableService;
import com.diego.desafio_goat_b.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class FullDevSeeder implements CommandLineRunner {

    private final SupplierService supplierService;
    private final SupplierRepository supplierRepository;
    private final AccountPayableRepository accountRepository;
    private final AccountPayableService accountService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String taxId = "00011122233";
        boolean exists = supplierRepository.existsByTaxId(taxId);
        if (exists) {
            return;
        }

        SupplierDTO dto = new SupplierDTO(
                null,
                "Fornecedor Seed",
                taxId,
                "seed@supplier.com",
                "(11) 90000-0000",
                null
        );

        Supplier savedSupplier = supplierService.create(dto);

        boolean accountExists = accountRepository.existsByDescriptionAndSupplierId("Serviço de consultoria", savedSupplier.getId());
        if (!accountExists) {
            AccountPayableDTO ap = new AccountPayableDTO(
                    null,
                    savedSupplier.getId(),
                    "Serviço de consultoria",
                    "SERVICOS",
                    new BigDecimal("1500.00"),
                    new BigDecimal("0.0"),
                    LocalDate.now().minusDays(20),
                    LocalDate.now().plusDays(10),
                    AccountStatus.OPEN,
                    "seed",
                    null
            );
            accountService.create(ap);
        }
    }
}
