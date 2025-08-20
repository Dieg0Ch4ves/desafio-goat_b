package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.entity.Payment;
import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.dto.PaymentDTO;
import com.diego.desafio_goat_b.exception.ResourceNotFoundException;
import com.diego.desafio_goat_b.mapper.AccountPayableMapper;
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
    private final AccountPayableMapper accountPayableMapper;
    private final SupplierService supplierService;


    public AccountPayableDTO create(AccountPayableDTO dto) {
        Supplier supplier = supplierService.findById(dto.supplierId());
        AccountPayable account = new AccountPayable();
        account = accountPayableMapper.toEntity(account, dto);
        account.setSupplier(supplier);
        accountPayableRepository.save(account);
        return dto;
    }

    public AccountPayableDTO update(UUID id, AccountPayableDTO dto) {
        AccountPayable account = accountPayableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountPayable not found"));
        account = accountPayableMapper.toEntity(account, dto);
        accountPayableRepository.save(account);
        return dto;
    }

    public void delete(UUID id) {
        accountPayableRepository.deleteById(id);
    }

    public AccountPayable findById(UUID id) {
        return accountPayableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountPayable not found"));
    }

    public List<AccountPayableDTO> findAll() {
        return accountPayableRepository.findAll()
                .stream()
                .map(accountPayableMapper::toDTO)
                .collect(Collectors.toList());
    }
}

