package com.aj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimitService {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitService.class);
    private static final String RATE_LIMIT_PREFIX = "rate:limit:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${app.rate-limit.max-requests:5}")
    private int maxRequests;

    @Value("${app.rate-limit.window-seconds:3600}")
    private int windowSeconds;

    public boolean allowRequest(String identifier) {
        String key = RATE_LIMIT_PREFIX + identifier;
        Long requests = redisTemplate.opsForValue().increment(key);
        if (requests == 1) {
            redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS);
        }
        boolean allowed = requests <= maxRequests;
        if (!allowed) {
            logger.warn("Rate limit exceeded for identifier: {}. Max requests: {}, Window: {}s", 
                identifier, maxRequests, windowSeconds);
        }
        return allowed;
    }
}