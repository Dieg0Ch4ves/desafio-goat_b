package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.entity.Payment;
import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.dto.PaymentDTO;
import com.diego.desafio_goat_b.repository.AccountPayableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountPayableService {

    private final AccountPayableRepository accountPayableRepository;
    private final SupplierService supplierService;
    private final PaymentService paymentService;

    public AccountPayableDTO toDTO(AccountPayable account) {
        List<PaymentDTO> listPayment = account.getPayments().stream()
                .map(paymentService::toDTO)
                .collect(Collectors.toList());
        return new AccountPayableDTO(
                account.getId(),
                account.getSupplier().getId(),
                account.getDescription(),
                account.getCategory(),
                account.getOriginalAmount(),
                account.getPaidAmount(),
                account.getIssueDate(),
                account.getDueDate(),
                account.getStatus(),
                account.getNotes(),
                listPayment
        );
    }

    public AccountPayable toEntity(AccountPayable account, AccountPayableDTO dto) {
        Supplier supplier = supplierService.findById(dto.supplierId());
        account.setSupplier(supplier);
        account.setDescription(dto.description());
        account.setCategory(dto.category());
        account.setOriginalAmount(dto.originalAmount());
        account.setPaidAmount(dto.paidAmount());
        account.setIssueDate(dto.issueDate());
        account.setDueDate(dto.dueDate());
        account.setStatus(dto.status());
        account.setNotes(dto.notes());
        return account;
    }

    public AccountPayableDTO create(AccountPayableDTO dto) {
        AccountPayable account = new AccountPayable();
        account = toEntity(account, dto);
        accountPayableRepository.save(account);
        return dto;
    }

    public AccountPayableDTO update(UUID id, AccountPayableDTO dto) {
        AccountPayable account = accountPayableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountPayable not found"));
        account = toEntity(account, dto);
        accountPayableRepository.save(account);
        return dto;
    }

    public void delete(UUID id) {
        accountPayableRepository.deleteById(id);
    }

    public AccountPayable findById(UUID id) {
        return accountPayableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountPayable not found"));
    }

    public List<AccountPayableDTO> findAll() {
        return accountPayableRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

