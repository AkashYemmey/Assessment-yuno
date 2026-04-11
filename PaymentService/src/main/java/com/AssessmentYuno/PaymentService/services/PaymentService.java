package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.dto.PaymentRequest;
import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import com.AssessmentYuno.PaymentService.entities.Payment;
import com.AssessmentYuno.PaymentService.entities.PaymentStatus;
import com.AssessmentYuno.PaymentService.entities.ProviderType;
import com.AssessmentYuno.PaymentService.repository.PaymentRepository;
import com.AssessmentYuno.PaymentService.services.provider.Provider;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentService {

    private final IdempotencyService idempotencyService;
    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper;
    private final RoutingEngine routingEngine;
    private final ProviderFactory providerFactory;



    public PaymentService(IdempotencyService idempotencyService, PaymentRepository paymentRepository, ObjectMapper objectMapper, RoutingEngine routingEngine, ProviderFactory providerFactory) {
        this.idempotencyService = idempotencyService;
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
        this.routingEngine = routingEngine;
        this.providerFactory = providerFactory;
    }

    public PaymentResponse process(@Valid PaymentRequest request, String key) {
        //1. Idempotence check, keys are stored and managed in redis cache
        if (idempotencyService.exists(key)) {
            return idempotencyService.get(key);
        }

        //2. start the payment process
        PaymentResponse response = processPayment(request,key);

        //3. save payment in redis to ensure idempotency.
        idempotencyService.save(key, response);

        return response;
    }

    private PaymentResponse processPayment(@Valid PaymentRequest request, String key) {
        List<ProviderType> providers = routingEngine.route(request.getType());
        if (providers.isEmpty()) {
            throw new RuntimeException("No provider available");
        }
        log.info("Processing payment with key: {}", key);
        Payment payment=Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .type(request.getType())
                .idempotencyKey(key)
                .status(PaymentStatus.CREATED)
                .build();

        // Iterate over list of providers, Obtained from routing engine class
        for (ProviderType providerType : providers) {
            //Get each individual provider from provider Factory
            Provider provider = providerFactory.getProvider(providerType);

            log.info("Trying provider: {}", providerType);

            boolean success = retry(provider, payment);

            if (success) {
                payment.setStatus(PaymentStatus.SUCCESS);
                payment.setProvider(ProviderType.valueOf(providerType.name()));
                paymentRepository.save(payment);

                return buildResponse(payment);
            }
        }

        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);

        return buildResponse(payment);
    }

    private boolean retry(Provider provider, Payment payment) {
        int MAX_RETRY=2;// we can consider it as an application property, but during testing this will cause failure. reason spring application context is not loaded.
        for (int i = 0; i < 2; i++) {
            log.warn("Retry attempt {}", i);
            if (provider.process(payment)) {
                return true;
            }
        }

        return false;
    }

    private PaymentResponse buildResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .status(payment.getStatus())
                .provider(payment.getProvider())
                .build();

    }

    public Payment get(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
