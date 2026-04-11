package com.AssessmentYuno.PaymentService.controllers;

import com.AssessmentYuno.PaymentService.dto.PaymentRequest;
import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.services.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    // Create Payment
    @PostMapping
    public ResponseEntity<PaymentResponse> create(
            @Valid @RequestBody PaymentRequest request,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {
        log.info("Started creating payment");
        PaymentResponse response = service.process(request, idempotencyKey);
        log.info("Response from DB after creation: "+ response);
        return ResponseEntity
                .status(201)
                .body(response);
    }

    //Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable String id) {
        log.info("Value from Path parameter"+ id);
        Payment response = service.get(id);
        if(response!=null) return ResponseEntity.ok(response);

        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(Payment.builder().message("No PaymentssssId Found").build());
    }
}
