package com.diego.desafio_goat_b.controller;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.dto.SupplierDTO;
import com.diego.desafio_goat_b.mapper.SupplierMapper;
import com.diego.desafio_goat_b.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<SupplierDTO> create(@PathVariable UUID userId, @RequestBody SupplierDTO dto) {
        Supplier created = supplierService.create(userId, dto);
        return new ResponseEntity<>(supplierMapper.toDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> update(@PathVariable UUID id, @RequestBody SupplierDTO dto) {
            Supplier updated = supplierService.update(id, dto);
            return ResponseEntity.ok(supplierMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(supplierMapper.toDTO(supplierService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> findAll() {
        return ResponseEntity.ok(supplierService.findAll());
    }

    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<List<SupplierDTO>> findAllWithCreatorByCreatorId(@PathVariable UUID userId) {
        return ResponseEntity.ok(supplierService.findAllWithCreatorByCreatorId(userId));
    }
}
