package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.entity.Payment;
import com.diego.desafio_goat_b.domain.entity.User;
import com.diego.desafio_goat_b.dto.PaymentDTO;
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
    private final UserRepository userRepository;
    private final AccountPayableService accountPayableService;


    public PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getAccount().getId(),
                payment.getPaymentDate(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getNote(),
                payment.getProcessedBy().getId()
        );
    }

    public Payment toEntity(Payment payment, PaymentDTO dto) {
        User processedBy = userRepository.findById(dto.processedBy()).orElseThrow();
        AccountPayable account = accountPayableService.findById(dto.accountId());
        payment.setAccount(account);
        payment.setPaymentDate(dto.paymentDate());
        payment.setAmount(dto.amount());
        payment.setMethod(dto.method());
        payment.setNote(dto.note());
        payment.setProcessedBy(processedBy);
        return payment;
    }


    public PaymentDTO create(PaymentDTO dto) {
        Payment payment = new Payment();
        payment = toEntity(payment, dto);
        paymentRepository.save(payment);
        return dto;
    }

    public PaymentDTO update(UUID id, PaymentDTO dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment = toEntity(payment, dto);
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
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}