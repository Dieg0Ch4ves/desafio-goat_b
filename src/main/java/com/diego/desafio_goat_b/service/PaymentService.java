package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.entity.Payment;
import com.diego.desafio_goat_b.domain.entity.User;
import com.diego.desafio_goat_b.dto.PaymentDTO;
import com.diego.desafio_goat_b.exception.ResourceNotFoundException;
import com.diego.desafio_goat_b.mapper.PaymentMapper;
import com.diego.desafio_goat_b.repository.PaymentRepository;
import com.diego.desafio_goat_b.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;


    public PaymentDTO create(PaymentDTO dto) {
        User processedBy = userRepository.findById(dto.processedBy()).orElseThrow(() ->
                new ResourceNotFoundException("User with ID " + dto.processedBy() + " not found"));
        Payment payment = new Payment();
        payment = paymentMapper.toEntity(payment, dto);
        payment.setProcessedBy(processedBy);
        paymentRepository.save(payment);
        return dto;
    }

    public PaymentDTO update(UUID id, PaymentDTO dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment = paymentMapper.toEntity(payment, dto);
        paymentRepository.save(payment);
        return dto;
    }

    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }

    public Payment findById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}