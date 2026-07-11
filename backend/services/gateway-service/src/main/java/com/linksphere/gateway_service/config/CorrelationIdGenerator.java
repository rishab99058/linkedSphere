package com.linksphere.gateway_service.config;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.linksphere.gateway_service.constant.RequestHeaders;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdGenerator implements GlobalFilter, Ordered {

    public String generate() {
        return UUID.randomUUID().toString();
    }

    // Make sure that it has the higer Precedence
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = exchange.getRequest()
                .getHeaders()
                .getFirst(RequestHeaders.CORRELATION_ID);

        final String finalCorrelationId = StringUtils.hasText(correlationId) ? correlationId : generate();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.header(RequestHeaders.CORRELATION_ID, finalCorrelationId))
                .build();

        return chain.filter(mutatedExchange);

    }

}
