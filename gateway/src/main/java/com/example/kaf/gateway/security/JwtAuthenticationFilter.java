package com.example.kaf.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerWebExchange modifiedExchange = exchange;

            try {
                // Get Authorization header
                String authHeader = exchange.getRequest()
                        .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                // Check if header exists and starts with "Bearer "
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);  // Remove "Bearer " prefix

                    // Validate token
                    if (validateToken(token)) {
                        String email = getEmailFromToken(token);

                        // Add email to request headers for downstream services
                        modifiedExchange = exchange.mutate()
                                .request(exchange.getRequest().mutate()
                                        .header("X-User-Email", email)
                                        .build())
                                .build();

                        System.out.println("JWT token validated for user: {}" + email);
                    } else {
                        System.out.println("Invalid or expired JWT token");
                        return this.onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    System.out.println("Missing or invalid Authorization header");
                    return this.onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                System.out.println("Error processing JWT token" + e);
                return this.onError(exchange, "Error processing token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(modifiedExchange);
        };
    }

    /**     * Validate JWT token     */
    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    /**     * Get email from JWT token     */
    private String getEmailFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("Error extracting email from token: " + e.getMessage());
            return null;
        }
    }

    /**     * Handle authentication errors     */
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // Configuration placeholder if needed in future
    }
}
