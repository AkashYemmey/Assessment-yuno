package com.AssessmentYuno.PaymentService.services.provider.Impl;

import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.services.provider.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderA implements Provider {
    public void process(Payment payment) {
        if (Math.random() < 0.3) throw new RuntimeException("A failed");
    }
    public String getName() { return "ProviderA"; }
}
