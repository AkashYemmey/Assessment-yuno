package com.AssessmentYuno.PaymentService.controllers;

import com.AssessmentYuno.PaymentService.dto.PaymentRequest;
import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.services.IdempotencyService;
import com.AssessmentYuno.PaymentService.services.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
public class PaymentController {
    private PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentResponse create(@Valid @RequestBody PaymentRequest request) {
        return service.process(request);
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable String id) {
        return service.get(id);
    }
}
