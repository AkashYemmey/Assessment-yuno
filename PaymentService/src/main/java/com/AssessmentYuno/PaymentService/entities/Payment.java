package com.AssessmentYuno.PaymentService.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Payment {

    @Id
    private ObjectId id;
    private Double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String provider;
    private String idempotencyKey;
    public Payment(){

    }

    public Payment(ObjectId id, Double amount, PaymentType type, PaymentStatus status, String provider, String idempotencyKey) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.provider = provider;
        this.idempotencyKey = idempotencyKey;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
