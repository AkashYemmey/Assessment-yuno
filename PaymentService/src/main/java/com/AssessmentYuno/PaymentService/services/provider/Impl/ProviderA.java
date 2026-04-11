package com.AssessmentYuno.PaymentService.services.provider.Impl;

import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.entities.ProviderType;
import com.AssessmentYuno.PaymentService.services.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ProviderA implements Provider {
    @Transactional
    public boolean process(Payment payment) {
        if (Math.random() < 0.3){
            log.info("Processing via Provider A");

            boolean success = Math.random() > 0.7;

            if (!success) {
                log.error("Provider A failed");
            }

            return success;
        }
        return false;
    }
    public ProviderType getName() { return ProviderType.PROVIDER_A; }
}
