package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
public class IdempotencyService {
    private StringRedisTemplate redis;
    private ObjectMapper objectMapper;

    public IdempotencyService(StringRedisTemplate redis, ObjectMapper objectMapper) {
        this.redis = redis;
        this.objectMapper = objectMapper;
    }

    public PaymentResponse get(String key) {
        return objectMapper.readValue(redis.opsForValue().get(key), PaymentResponse.class);
    }

    public void save(String key, PaymentResponse response) {
        String value=objectMapper.writeValueAsString(response);
        redis.opsForValue().set(key, value, Duration.ofMinutes(10));
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redis.hasKey(key));
    }
}
