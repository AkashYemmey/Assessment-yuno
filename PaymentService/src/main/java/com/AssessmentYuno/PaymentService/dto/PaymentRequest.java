package com.AssessmentYuno.PaymentService.dto;

import com.AssessmentYuno.PaymentService.entities.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotNull
    private PaymentType type;

    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String idempotencyKey;
}
