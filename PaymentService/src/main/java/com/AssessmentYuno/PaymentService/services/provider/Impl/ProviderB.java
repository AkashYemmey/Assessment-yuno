package com.AssessmentYuno.PaymentService.services.provider.Impl;

import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.entities.ProviderType;
import com.AssessmentYuno.PaymentService.services.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Slf4j
@Component
public class ProviderB implements Provider {
    @Transactional
    public boolean process(Payment payment) {
        if (Math.random() < 0.9) {
            log.info("Processing via Provider B");

            return Math.random() > 0.5;
        }
        return false;
    }
    public ProviderType getName() { return ProviderType.PROVIDER_B; }
}
