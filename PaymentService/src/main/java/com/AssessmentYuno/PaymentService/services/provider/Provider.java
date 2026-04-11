package com.AssessmentYuno.PaymentService.services.provider;

import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.entities.ProviderType;

public interface Provider {
    boolean process(Payment payment);
    ProviderType getName();
}
