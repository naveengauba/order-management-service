package com.example.order.management.handler;

import com.example.order.management.Greeting;
import com.example.order.management.domain.Order;
import com.example.order.management.service.OrderManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class OrderManagementHandler {

    private final OrderManagementService service;

    public OrderManagementHandler(OrderManagementService service) {
        this.service = service;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Greeting("Hello, Spring!")));
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.findById(id)
                .flatMap(order -> ok().contentType(APPLICATION_JSON).bodyValue(order))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getMany(ServerRequest serverRequest) {
        Flux<Order> orders = service.findAll();
        return ok().contentType(APPLICATION_JSON).body(orders, Order.class);
    }

    public Mono<ServerResponse> createOne(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Order.class)
                .flatMap(service::saveOrder)
                .flatMap(p -> ok().contentType(APPLICATION_JSON).bodyValue(p))
                .doOnError(ex -> ServerResponse.status(HttpStatus.BAD_REQUEST).contentType(APPLICATION_JSON));
    }

}
