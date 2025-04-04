package com.aj.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final String BLACKLIST_KEY_PREFIX = "blacklist:token:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Centralized key generation
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Token generation for authentication
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, 1000 * 60 * 100); // 100 minutes
    }

    // Token generation for password reset
    public String generateResetToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "reset");
        return createToken(claims, email, 1000 * 60 * 15); // 15 minutes
    }

    // Generic token creation method
    private String createToken(Map<String, Object> claims, String subject, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract claims with exception handling
    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (JwtException e) {
            logger.error("Failed to extract username from token: {}", e.getMessage());
            throw e; // Re-throw to be handled by the caller (e.g., JwtAuthFilter)
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) throws JwtException {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (JwtException e) {
            logger.warn("Token expiration check failed: {}", e.getMessage());
            return true; // Treat as expired if we can't validate
        }
    }

    // Token validation for authentication
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            boolean isBlacklisted = isTokenBlacklisted(stripBearerPrefix(token));
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isBlacklisted;
            if (!isValid && isBlacklisted) {
                logger.debug("Token is blacklisted for user: {}", username);
            }
            return isValid;
        } catch (Exception e) {
            logger.error("Token validation failed: {}", e.getMessage());
            return false; // Fail closed for security
        }
    }

    // Token validation for reset
    public boolean validateResetToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return "reset".equals(claims.get("type")) && !isTokenExpired(token);
        } catch (JwtException e) {
            logger.error("Invalid reset token: {}", e.getMessage());
            return false;
        }
    }

    // Blacklist token with Redis fallback
    public void blacklistToken(String token) {
        String jwt = stripBearerPrefix(token);
        String key = BLACKLIST_KEY_PREFIX + jwt;
        try {
            long expirationTimeInSeconds = getExpirationTimeFromToken(jwt);
            if (expirationTimeInSeconds > 0) {
                redisTemplate.opsForValue().set(key, "blacklisted", expirationTimeInSeconds, TimeUnit.SECONDS);
                logger.info("Token blacklisted in Redis: {}", jwt);
            }
        } catch (Exception e) {
            logger.error("Failed to blacklist token in Redis: {}", e.getMessage());
            // Fallback: Proceed without blacklisting if Redis is down
        }
    }

    // Check if token is blacklisted with Redis fallback
    public boolean isTokenBlacklisted(String token) {
        String jwt = stripBearerPrefix(token);
        String key = BLACKLIST_KEY_PREFIX + jwt;
        try {
            Boolean exists = redisTemplate.hasKey(key);
            return exists != null && exists;
        } catch (Exception e) {
            logger.warn("Redis unavailable, skipping blacklist check for token: {}", jwt);
            return false; // Fallback: Assume not blacklisted if Redis is down
        }
    }

    // Utility to strip "Bearer " prefix
    private String stripBearerPrefix(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }

    // Calculate remaining expiration time in seconds
    private long getExpirationTimeFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            long expiration = claims.getExpiration().getTime();
            long now = System.currentTimeMillis();
            return Math.max((expiration - now) / 1000, 0); // Ensure non-negative
        } catch (JwtException e) {
            logger.error("Failed to calculate expiration time: {}", e.getMessage());
            return 0; // Default to immediate expiration on error
        }
    }
}