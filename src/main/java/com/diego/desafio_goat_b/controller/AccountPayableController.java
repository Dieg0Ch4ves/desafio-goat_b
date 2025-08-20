package com.diego.desafio_goat_b.controller;

import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.mapper.AccountPayableMapper;
import com.diego.desafio_goat_b.service.AccountPayableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts-payable")
@RequiredArgsConstructor
public class AccountPayableController {

    private final AccountPayableService accountPayableService;
    private final AccountPayableMapper accountPayableMapper;

    @PostMapping
    public ResponseEntity<AccountPayableDTO> create(@RequestBody AccountPayableDTO dto) {
        AccountPayableDTO created = accountPayableService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountPayableDTO> update(@PathVariable UUID id, @RequestBody AccountPayableDTO dto) {
        AccountPayableDTO updated = accountPayableService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountPayableService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountPayableDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                accountPayableMapper.toDTO(accountPayableService.findById(id))
        );
    }

    @GetMapping
    public ResponseEntity<List<AccountPayableDTO>> findAll() {
        return ResponseEntity.ok(accountPayableService.findAll());
    }
}
