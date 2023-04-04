package com.example.order.management.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import com.example.order.management.domain.Order;
import com.example.order.management.handler.OrderManagementHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OrderManagementRouter {

    @RouterOperations({
        @RouterOperation(
                path = "/orders/{id}",
                method = RequestMethod.GET,
                produces = {MediaType.APPLICATION_JSON_VALUE},
                beanClass = OrderManagementHandler.class,
                beanMethod = "getOne",
                operation =
                        @Operation(
                                operationId = "getOne",
                                parameters = {@Parameter(in = ParameterIn.PATH, name = "id")},
                                responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            content =
                                                    @Content(
                                                            schema =
                                                                    @Schema(
                                                                            implementation =
                                                                                    Order.class))),
                                    @ApiResponse(responseCode = "404", description = "Note found")
                                })),
        @RouterOperation(
                path = "/orders",
                method = RequestMethod.GET,
                produces = {MediaType.APPLICATION_JSON_VALUE},
                beanClass = OrderManagementHandler.class,
                beanMethod = "getMany",
                operation =
                        @Operation(
                                operationId = "getMany",
                                responses = {
                                    @ApiResponse(responseCode = "200", description = "Success"),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal Server Error")
                                })),
        @RouterOperation(
                path = "/orders",
                method = RequestMethod.POST,
                consumes = {MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE},
                beanClass = OrderManagementHandler.class,
                beanMethod = "createOne",
                operation =
                        @Operation(
                                operationId = "createOne",
                                requestBody =
                                        @RequestBody(
                                                content =
                                                        @Content(
                                                                schema =
                                                                        @Schema(
                                                                                implementation =
                                                                                        Order
                                                                                                .class))),
                                responses = {
                                    @ApiResponse(responseCode = "200", description = "Success"),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid data supplied"),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal Server Error")
                                }))
    })
    @Bean
    public RouterFunction<ServerResponse> route(OrderManagementHandler orderManagementHandler) {

        return RouterFunctions.route(
                        GET("/orders/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        orderManagementHandler::getOne)
                .andRoute(
                        GET("/orders").and(accept(MediaType.APPLICATION_JSON)),
                        orderManagementHandler::getMany)
                .andRoute(
                        POST("/orders")
                                .and(accept(MediaType.APPLICATION_JSON))
                                .and(contentType(MediaType.APPLICATION_JSON)),
                        orderManagementHandler::createOne);
    }
}
