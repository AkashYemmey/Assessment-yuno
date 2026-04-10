package com.AssessmentYuno.PaymentService.services;

import com.AssessmentYuno.PaymentService.dto.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IdempotencyService {
    private StringRedisTemplate redis;

    public IdempotencyService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public String get(String key) {
        return redis.opsForValue().get(key);
    }

    public void save(String key, String response) {
        redis.opsForValue().set(key, response, Duration.ofMinutes(10));
    }
}
