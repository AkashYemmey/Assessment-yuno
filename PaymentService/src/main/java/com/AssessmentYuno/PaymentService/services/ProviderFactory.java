package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.entities.ProviderType;
import com.AssessmentYuno.PaymentService.services.provider.Provider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProviderFactory {

    private final Map<ProviderType, Provider> providerMap;

    public ProviderFactory(List<Provider> providers) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(Provider::getName, p -> p));
    }

    public Provider getProvider(ProviderType type) {
        return providerMap.get(type);
    }
}