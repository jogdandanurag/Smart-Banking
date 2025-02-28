package com.aj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class ResetTokenService {
    private static final Logger logger = LoggerFactory.getLogger(ResetTokenService.class);
    private static final String RESET_TOKEN_PREFIX = "reset:token:";
    private static final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${app.reset-token-expiry:900}")
    private int tokenExpirySeconds;

    public String generateResetToken(String email) {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        String key = RESET_TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, email, tokenExpirySeconds, TimeUnit.SECONDS);
        logger.info("Generated reset token for email: {} with expiry: {}s", email, tokenExpirySeconds);
        return token;
    }

    public String validateAndGetEmail(String token) {
        String key = RESET_TOKEN_PREFIX + token;
        String email = redisTemplate.opsForValue().get(key);
        if (email == null) {
            logger.warn("Invalid or expired reset token: {}", token);
            return null;
        }
        redisTemplate.delete(key); // Single-use token
        logger.info("Validated and consumed reset token for email: {}", email);
        return email;
    }
}