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
import java.util.function.Function;
import java.util.concurrent.TimeUnit;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final String BLACKLIST_KEY_PREFIX = "blacklist:token:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Existing JWT methods
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 100)) // 100 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token);
        if (!isValid && isTokenBlacklisted(token)) {
            logger.debug("Token is blacklisted for user: {}", username);
        }
        return isValid;
    }

    public void blacklistToken(String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        long expirationTimeInSeconds = getExpirationTimeFromToken(jwt);
        String key = BLACKLIST_KEY_PREFIX + jwt;
        redisTemplate.opsForValue().set(key, "blacklisted", expirationTimeInSeconds, TimeUnit.SECONDS);
        logger.info("Token blacklisted in Redis: {}", jwt);
    }

    public boolean isTokenBlacklisted(String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String key = BLACKLIST_KEY_PREFIX + jwt;
        Boolean exists = redisTemplate.hasKey(key);
        return exists != null && exists;
    }

    private long getExpirationTimeFromToken(String token) {
        Claims claims = extractAllClaims(token);
        long expiration = claims.getExpiration().getTime();
        long now = System.currentTimeMillis();
        return (expiration - now) / 1000;
    }

    // New methods for password reset token
    public String generateResetToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "reset"); // Differentiate from auth tokens
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes expiration
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateResetToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return "reset".equals(claims.get("type")) && !isTokenExpired(token);
        } catch (Exception e) {
            logger.error("Invalid reset token: {}", e.getMessage());
            return false;
        }
    }
}