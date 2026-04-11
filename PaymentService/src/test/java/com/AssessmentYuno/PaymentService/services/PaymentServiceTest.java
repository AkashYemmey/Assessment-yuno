package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.dto.PaymentRequest;
import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import com.AssessmentYuno.PaymentService.entities.PaymentStatus;
import com.AssessmentYuno.PaymentService.entities.PaymentType;
import com.AssessmentYuno.PaymentService.entities.ProviderType;
import com.AssessmentYuno.PaymentService.repository.PaymentRepository;
import com.AssessmentYuno.PaymentService.services.provider.Provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private IdempotencyService idempotencyService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RoutingEngine routingEngine;

    @Mock
    private ProviderFactory providerFactory;

    @Mock
    private Provider providerA;

    @Mock
    private Provider providerB;

    @InjectMocks
    private PaymentService service;

    private PaymentRequest request;

    @BeforeEach
    void setup() {
        request = new PaymentRequest();
        request.setAmount(100.0);
        request.setCurrency("INR");
        request.setType(PaymentType.CARD);
    }

    //  1. Idempotency Test
    @Test
    void shouldReturnCachedResponse_whenIdempotencyKeyExists() {

        PaymentResponse cached = PaymentResponse.builder()
                .status(PaymentStatus.SUCCESS)
                .build();

        when(idempotencyService.exists("key-1")).thenReturn(true);
        when(idempotencyService.get("key-1")).thenReturn(cached);

        PaymentResponse response = service.process(request, "key-1");

        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        verify(idempotencyService, never()).save(any(), any());
    }

    // 2. Successful Payment
    @Test
    void shouldProcessPaymentSuccessfully() {

        when(idempotencyService.exists(any())).thenReturn(false);

        when(routingEngine.route(PaymentType.CARD))
                .thenReturn(List.of(ProviderType.PROVIDER_A));

        when(providerFactory.getProvider(ProviderType.PROVIDER_A))
                .thenReturn(providerA);

        when(providerA.process(any())).thenReturn(true);

        when(paymentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        PaymentResponse response = service.process(request, "key-2");

        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
    }

    //  3. Retry Success
    @Test
    void shouldRetryAndSucceed() {

        when(idempotencyService.exists(any())).thenReturn(false);

        when(routingEngine.route(PaymentType.CARD))
                .thenReturn(List.of(ProviderType.PROVIDER_A));

        when(providerFactory.getProvider(any()))
                .thenReturn(providerA);

        when(providerA.process(any()))
                .thenReturn(false) // first attempt
                .thenReturn(true); // retry success

        PaymentResponse response = service.process(request, "key-3");

        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
    }

    // 4. Failover Test (A → B)
    @Test
    void shouldFailoverToSecondProvider() {

        when(idempotencyService.exists(any())).thenReturn(false);

        when(routingEngine.route(PaymentType.CARD))
                .thenReturn(List.of(
                        ProviderType.PROVIDER_A,
                        ProviderType.PROVIDER_B
                ));

        when(providerFactory.getProvider(ProviderType.PROVIDER_A))
                .thenReturn(providerA);

        when(providerFactory.getProvider(ProviderType.PROVIDER_B))
                .thenReturn(providerB);

        when(providerA.process(any())).thenReturn(false);
        when(providerB.process(any())).thenReturn(true);

        PaymentResponse response = service.process(request, "key-4");

        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        assertEquals(ProviderType.PROVIDER_B, response.getProvider());
    }

    //  5. All Providers Fail
    @Test
    void shouldFailWhenAllProvidersFail() {

        when(idempotencyService.exists(any())).thenReturn(false);

        when(routingEngine.route(PaymentType.CARD))
                .thenReturn(List.of(ProviderType.PROVIDER_A));

        when(providerFactory.getProvider(any()))
                .thenReturn(providerA);

        when(providerA.process(any())).thenReturn(false);

        PaymentResponse response = service.process(request, "key-5");

        assertEquals(PaymentStatus.FAILED, response.getStatus());
    }

    //  6. Invalid Routing
    @Test
    void shouldThrowExceptionForInvalidRouting() {

        when(idempotencyService.exists(any())).thenReturn(false);

        when(routingEngine.route(PaymentType.CARD))
                .thenThrow(new IllegalArgumentException("Invalid method"));

        assertThrows(IllegalArgumentException.class,
                () -> service.process(request, "key-6"));
    }
}