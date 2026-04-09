package com.AssessmentYuno.PaymentService.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Payment {

    @Id
    private ObjectId id;
    private String paymentId;
    private Double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String provider;
    private String idempotencyKey;

}
