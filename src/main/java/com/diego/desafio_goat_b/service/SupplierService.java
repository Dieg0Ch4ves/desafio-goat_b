package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.domain.entity.User;
import com.diego.desafio_goat_b.dto.SupplierDTO;
import com.diego.desafio_goat_b.exception.ResourceNotFoundException;
import com.diego.desafio_goat_b.exception.UserNotFoundException;
import com.diego.desafio_goat_b.mapper.SupplierMapper;
import com.diego.desafio_goat_b.repository.SupplierRepository;
import com.diego.desafio_goat_b.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierMapper mapper;

    public Supplier create(UUID userId, SupplierDTO dto) {
        Optional<User> createdByOpt = userRepository.findById(userId);

        if(createdByOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User createdBy = createdByOpt.get();
        Supplier supplier = new Supplier();
        supplier = mapper.toEntity(supplier, dto);
        supplier.setCreatedBy(createdBy);
        return supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier update(UUID id, SupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Supplier with ID " + id + " not found"));
        supplier = mapper.toEntity(supplier, dto);
        return supplierRepository.save(supplier);
    }

    public void delete(UUID id) {
        supplierRepository.deleteById(id);
    }

    public Supplier findById(UUID id) {
        return supplierRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Supplier with ID " + id + " not found"));

    }

    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<SupplierDTO> findAllWithCreatorByCreatorId(UUID userId) {
        return supplierRepository.findAllWithCreatorByCreatorId(userId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
