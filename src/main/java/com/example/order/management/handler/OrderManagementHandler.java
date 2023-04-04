package com.example.order.management.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.example.order.management.domain.Order;
import com.example.order.management.service.OrderManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OrderManagementHandler {

    private final OrderManagementService service;
    private final OrderValidator validator;

    public OrderManagementHandler(OrderManagementService service, OrderValidator validator) {
        this.service = service;
        this.validator = validator;
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
        Validator validator = new OrderValidator();
        Mono<Order> responseBody =
                serverRequest
                        .bodyToMono(Order.class)
                        .flatMap(
                                body -> {
                                    Errors errors =
                                            new BeanPropertyBindingResult(
                                                    body, Order.class.getName());
                                    validator.validate(body, errors);
                                    if (errors == null || errors.getAllErrors().isEmpty()) {
                                        return service.saveOrder(body);
                                    } else {
                                        throw new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST,
                                                errors.getAllErrors().toString());
                                    }
                                });
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody, Order.class);
    }
}
