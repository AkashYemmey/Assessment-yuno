package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.dto.PaymentRequest;
import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class PaymentService {

    private IdempotencyService idempotencyService;
    private PaymentRepository paymentRepository;
    private ObjectMapper objectMapper;

    public PaymentService(IdempotencyService idempotencyService, PaymentRepository paymentRepository, ObjectMapper objectMapper) {
        this.idempotencyService = idempotencyService;
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
    }

    public PaymentResponse process(@Valid PaymentRequest request) {
//        String value=objectMapper.writeValueAsString(request);
//        idempotencyService.save(, value);
//        System.out.println(idempotencyService.get("tinku"));
//        PaymentRequest tinku = objectMapper.readValue(idempotencyService.get("tinku"), PaymentRequest.class);
//        System.out.println(tinku.toString());
//        System.out.println(idempotencyService.get("tinku"));

        //idempotencyService.save("123", "hello2");
        Payment payment=new Payment();
        payment.setAmount(request.getAmount());
        payment.setType(request.getType());
        payment.setIdempotencyKey(request.getIdempotencyKey());
        paymentRepository.save(payment);
        return null;
    }

    public Payment get(String id) {
        return null;
    }
}
