package com.example.kaf.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    /**
     * Configure which endpoints are public/protected
     * <p>
     * IMPORTANT: This is for the USER SERVICE itself.
     * The GATEWAY will also validate JWT tokens.
     * <p>
     * FLOW:
     * 1. Request enters gateway → gateway validates JWT
     * 2. Valid request → forwarded to user service
     * 3. User service ALSO validates (defense in depth)
     */
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                // Disable CSRF (safe for stateless REST APIs with tokens)
                .csrf(csrf -> csrf.disable())

                // 2. Set Session Management to Stateless
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

                // Configure endpoint access
                .authorizeExchange(auth -> auth
                        // PUBLIC ENDPOINTS - no authentication needed
                        .pathMatchers("/api/auth/**").permitAll()      // Signup endpoint

                        // PROTECTED ENDPOINTS - authentication required
                        .pathMatchers("/api/logs").authenticated()  // Get all users (private)
                        .pathMatchers("/api/orders").authenticated()      // All other /api/auth/* need auth

                        // ANY other request also requires authentication
                        .anyExchange().authenticated()
                );

        return http.build();
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable()) // Typical for Gateways
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll() // Adjust as needed
                );
        return http.build();
    }
}
