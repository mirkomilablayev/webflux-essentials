package com.example.webfluxessentials.error;

import com.example.webfluxessentials.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@ControllerAdvice
@Component
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(GenericException.class)
    public Mono<ResponseEntity<CommonResult>> handleCommonResult(GenericException e, ServerHttpRequest request) {
        return Mono.just(ResponseEntity.ok(new CommonResult(
                e.getErrors().getCode(),
                e.getErrors().getMessage(),
                null
        )));
    }
}
