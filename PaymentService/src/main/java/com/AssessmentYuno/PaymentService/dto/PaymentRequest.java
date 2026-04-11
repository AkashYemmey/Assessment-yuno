package com.AssessmentYuno.PaymentService.dto;

import com.AssessmentYuno.PaymentService.entities.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentRequest {
    @NotNull
    private PaymentType type;

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private String currency;


}
