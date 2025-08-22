package com.diego.desafio_goat_b.controller;

import com.diego.desafio_goat_b.dto.PaymentDTO;
import com.diego.desafio_goat_b.mapper.PaymentMapper;
import com.diego.desafio_goat_b.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody PaymentDTO dto) {
        PaymentDTO created = paymentService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable UUID id, @RequestBody PaymentDTO dto) {
        PaymentDTO updated = paymentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentMapper.toDTO(paymentService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/get-by-account/{accountId}")
    public ResponseEntity<List<PaymentDTO>> findByAccountId(@PathVariable UUID accountId) {
        return ResponseEntity.ok(paymentService.findByAccountId(accountId));
    }
}
