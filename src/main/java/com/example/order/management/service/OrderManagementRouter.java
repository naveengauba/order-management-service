package com.example.order.management.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class OrderManagementRouter {
    @Bean
    public RouterFunction<ServerResponse> route(OrderManagementHandler orderManagementHandler) {

        return RouterFunctions
                .route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), orderManagementHandler::hello)
                .andRoute(GET("/orders/{id}").and(accept(MediaType.APPLICATION_JSON)), orderManagementHandler::getOne)
                .andRoute(GET("/orders/").and(accept(MediaType.APPLICATION_JSON)), orderManagementHandler::getMany)
                .andRoute(POST("/orders/").and(accept(MediaType.APPLICATION_JSON)).and(contentType(MediaType.APPLICATION_JSON)), orderManagementHandler::createOne);
    }

}
