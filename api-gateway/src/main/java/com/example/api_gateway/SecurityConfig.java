
package com.example.api_gateway;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/test-drives/**").hasRole("EMPLEADO")
                        .pathMatchers("/api/positions/**").hasRole("VEHICULO")
                        .pathMatchers("/api/reports/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                        )
                );
        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter grantedAuthoritiesExtractor() {
        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            try {
                System.out.println("JWT Claims: " + jwt.getClaims());
                Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
                if (realmAccess == null || !realmAccess.containsKey("roles")) {
                    System.out.println("No realm_access or roles found");
                    return Flux.fromIterable(Collections.emptyList());
                }

                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) realmAccess.get("roles");
                if (roles == null) {
                    System.out.println("Roles list is null");
                    return Flux.fromIterable(Collections.emptyList());
                }

                System.out.println("Found roles: " + roles);
                return Flux.fromIterable(roles)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            } catch (Exception e) {
                System.err.println("Error processing token: " + e.getMessage());
                return Flux.fromIterable(Collections.emptyList());
            }
        });
        return jwtAuthenticationConverter;
    }
}
