package com.diego.desafio_goat_b.mapper;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.dto.PaymentDTO;
import com.diego.desafio_goat_b.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AccountPayableMapper implements BaseMapper<AccountPayable, AccountPayableDTO> {

    private final PaymentMapper paymentMapper;

    @Override
    public AccountPayableDTO toDTO(AccountPayable account) {
        List<PaymentDTO> listPayment = account.getPayments().stream()
                .map(paymentMapper::toDTO)
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

    @Override
    public AccountPayable toEntity(AccountPayable account, AccountPayableDTO dto) {
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
}
