package com.auth0.spring.security.api;

import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class JwtAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> {
            String token = tokenFromRequest(exchange.getRequest());
            return PreAuthenticatedAuthenticationJsonWebToken.usingToken(token);
        });
    }


    private String tokenFromRequest(ServerHttpRequest request) {
        String value = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (value == null || !value.toLowerCase().startsWith("bearer")) {
            return null;
        }

        String[] parts = value.split(" ");

        if (parts.length < 2) {
            return null;
        }

        return parts[1].trim();
    }

}
