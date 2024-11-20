package com.example.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {
    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test-drive-service", r -> r
                        .path("/api/test-drives/**")
                        .uri("http://localhost:8081"))
                .route("vehicle-position-service", r -> r
                        .path("/api/positions/**")
                        .uri("http://localhost:8082"))
                .build();
    }
}