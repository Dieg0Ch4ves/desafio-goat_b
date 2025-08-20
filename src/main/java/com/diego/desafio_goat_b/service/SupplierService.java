package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.SupplierDTO;
import com.diego.desafio_goat_b.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierDTO toDTO(Supplier supplier) {
        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getTaxId(),
                supplier.getPhone()
        );
    }

    public Supplier toEntity(Supplier supplier, SupplierDTO dto) {
        supplier.setName(dto.name());
        supplier.setEmail(dto.email());
        supplier.setTaxId(dto.taxId());
        supplier.setPhone(dto.phone());
        return supplier;
    }

    public SupplierDTO create(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier = toEntity(supplier, dto);
        supplierRepository.save(supplier);
        return dto;
    }

    public SupplierDTO update(UUID id, SupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplier = toEntity(supplier, dto);
        supplierRepository.save(supplier);
        return dto;
    }

    public void delete(UUID id) {
        supplierRepository.deleteById(id);
    }

    public Supplier findById(UUID id) {
        return supplierRepository.findById(id).orElseThrow();

    }

    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
