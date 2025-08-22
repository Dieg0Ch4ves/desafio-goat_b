package com.diego.desafio_goat_b.mapper;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SupplierMapper implements BaseMapper<Supplier, SupplierDTO> {

    private final AccountPayableMapper accountPayableMapper;

    @Override
    public SupplierDTO toDTO(Supplier entity) {
        return new SupplierDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getTaxId(),
                entity.getPhone()

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
