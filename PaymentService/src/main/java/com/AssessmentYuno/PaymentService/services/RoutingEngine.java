package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.entities.PaymentType;
import com.AssessmentYuno.PaymentService.entities.ProviderType;
import com.AssessmentYuno.PaymentService.services.provider.Impl.ProviderA;
import com.AssessmentYuno.PaymentService.services.provider.Impl.ProviderB;
import com.AssessmentYuno.PaymentService.services.provider.Provider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoutingEngine {
    public List<ProviderType> route(PaymentType method) {

        if ("CARD".equalsIgnoreCase(method.toString())) {
            return List.of(ProviderType.PROVIDER_A, ProviderType.PROVIDER_B);
        }

        if ("UPI".equalsIgnoreCase(method.toString())) {
            return List.of(ProviderType.PROVIDER_B);
        }

        throw new RuntimeException("Unsupported method");
    }
}
