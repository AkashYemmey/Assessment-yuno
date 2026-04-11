package com.AssessmentYuno.PaymentService.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment {

    @Id
    private String id;
    private Double amount;
    private PaymentType type;
    private PaymentStatus status;
    private ProviderType provider;
    private String idempotencyKey;
    private String currency;
    private String message;
    @CreatedDate
    private LocalDateTime createdAt;


    public Payment(String noPaymentIdFound) {
    }
}
