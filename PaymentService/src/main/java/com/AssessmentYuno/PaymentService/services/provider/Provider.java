package com.AssessmentYuno.PaymentService.services.provider;

import com.AssessmentYuno.PaymentService.entities.Payment;

public interface Provider {
    void process(Payment payment);
    String getName();
}
