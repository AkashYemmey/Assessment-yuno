package com.AssessmentYuno.PaymentService.dto;

import com.AssessmentYuno.PaymentService.entities.PaymentStatus;
import com.AssessmentYuno.PaymentService.entities.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentResponse {
    private String paymentId;
    private PaymentStatus status;
    private ProviderType provider;


}
