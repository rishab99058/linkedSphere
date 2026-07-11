package com.linksphere.gateway_service.config;

import com.linksphere.gateway_service.constant.RequestHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdGenerator implements GlobalFilter, Ordered {

    public String generate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var headers = request.getHeaders();
        String correlationId = headers.getFirst(RequestHeaders.CORRELATION_ID);

        if (!StringUtils.hasText(correlationId)) {
            correlationId = generate();
            var mutatedRequest = request.mutate()
                    .header(RequestHeaders.CORRELATION_ID, correlationId)
                    .build();
            exchange = exchange.mutate().request(mutatedRequest).build();
        }

        // Add correlation ID to the response headers
        exchange.getResponse().getHeaders().add(RequestHeaders.CORRELATION_ID, correlationId);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
