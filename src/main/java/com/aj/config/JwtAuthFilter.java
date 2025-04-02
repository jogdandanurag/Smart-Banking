package com.aj.config;

import java.io.IOException;
import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aj.service.JwtService;
import com.aj.service.UserInfoService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            // Check if the Authorization header contains a Bearer token
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                logger.debug("JWT token found for user: {}", username);
            }

            // If username exists and no authentication exists in context, proceed
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    logger.debug("JWT token validated successfully for user: {}", username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    logger.warn("JWT token validation failed for user: {}", username);
                }
            }
            filterChain.doFilter(request, response); // Continue the filter chain
            logger.debug("JWT authentication filter processing completed");

        } catch (ExpiredJwtException e) {
            logger.error("JWT token has expired for user {}: {}", username, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token has expired");
            return;

        } catch (JwtException e) {
            logger.error("Invalid JWT token for user {}: {}", username, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            return;

        } catch (Exception e) {
            // Check if the root cause is a Redis connection failure
            Throwable rootCause = getRootCause(e);
            if (rootCause instanceof ConnectException || rootCause.getMessage().contains("Redis")) {
                logger.error("Failed to connect to Redis while processing JWT token for user {}: {}", username, rootCause.getMessage());
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                response.getWriter().write("Service temporarily unavailable due to Redis connection failure");
                return;
            }
            logger.error("Unexpected error processing JWT token for user {}: {}", username, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An internal error occurred while processing the JWT token");
            return;
        }
    }

    // Helper method to get the root cause of an exception
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }
}