package com.diego.desafio_goat_b.mapper;

import com.diego.desafio_goat_b.domain.entity.AccountPayable;
import com.diego.desafio_goat_b.domain.entity.Payment;
import com.diego.desafio_goat_b.domain.entity.Supplier;
import com.diego.desafio_goat_b.domain.entity.User;
import com.diego.desafio_goat_b.dto.AccountPayableDTO;
import com.diego.desafio_goat_b.dto.PaymentDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper implements BaseMapper<Payment, PaymentDTO> {

    @Override
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

    @Override
    public Payment toEntity(Payment payment, PaymentDTO dto) {
        payment.setPaymentDate(dto.paymentDate());
        payment.setAmount(dto.amount());
        payment.setMethod(dto.method());
        payment.setNote(dto.note());
        return payment;
    }



}
