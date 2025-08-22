package com.diego.desafio_goat_b.mapper;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountPayableMapper implements BaseMapper<AccountPayable, AccountPayableDTO> {

    @Override
    public AccountPayableDTO toDTO(AccountPayable account) {
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
                account.getNotes()
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
