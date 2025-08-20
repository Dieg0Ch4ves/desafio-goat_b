package com.diego.desafio_goat_b.mapper;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SupplierMapper implements BaseMapper<Supplier, SupplierDTO> {

    private final AccountPayableMapper accountPayableMapper;

    @Override
    public SupplierDTO toDTO(Supplier entity) {
        List<AccountPayableDTO> listAccountsDto = entity.getAccounts()
                .stream()
                .map(accountPayableMapper::toDTO)
                .toList();

        return new SupplierDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getTaxId(),
                entity.getPhone(),
                listAccountsDto

        );
    }

    @Override
    public Supplier toEntity(Supplier entity, SupplierDTO dto) {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setTaxId(dto.taxId());
        entity.setPhone(dto.phone());
        return entity;
    }

}
