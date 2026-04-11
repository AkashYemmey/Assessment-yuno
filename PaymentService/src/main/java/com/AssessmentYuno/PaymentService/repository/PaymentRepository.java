package com.AssessmentYuno.PaymentService.repository;

import com.AssessmentYuno.PaymentService.entities.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
}
