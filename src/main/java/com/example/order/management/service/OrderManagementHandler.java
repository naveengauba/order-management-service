package com.example.order.management.service;

import com.example.order.management.service.dto.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class OrderManagementHandler {

    private OrderManagementRepository service;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Greeting("Hello, Spring!")));
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        String id = request.pathVariable("id");
        //service.findById(id).

    }

    public Mono<ServerResponse> getMany(ServerRequest serverRequest) {
        return null;
    }
    public Mono<ServerResponse> createOne(ServerRequest serverRequest) {
        return null;
    }

}
