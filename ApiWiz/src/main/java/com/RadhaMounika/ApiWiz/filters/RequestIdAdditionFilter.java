package com.RadhaMounika.ApiWiz.filters;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@Component
@Order(Integer.MAX_VALUE)
public class RequestIdAdditionFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        setRequestId();
        return chain.filter(exchange);
    }

    private void setRequestId() {
        String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        ThreadContext.put("X-Request-Id", uuid);
    }
}
