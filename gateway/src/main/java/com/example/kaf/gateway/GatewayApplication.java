package com.example.kaf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order-service", r -> r
                        .path("/api/orders/**")   // 🔥 FIX
                        .uri("lb://ORDER"))

                .route("notify-service", r -> r
                        .path("/api/logs/**")
                        .uri("lb://NOTIFY"))

                .route("user-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://USER"))

                .build();
    }


}
