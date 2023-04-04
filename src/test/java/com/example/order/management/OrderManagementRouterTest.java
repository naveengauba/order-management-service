package com.example.order.management;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.order.management.domain.Customer;
import com.example.order.management.domain.Item;
import com.example.order.management.domain.Order;
import com.example.order.management.service.OrderManagementService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//  We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60000")
public class OrderManagementRouterTest {

    @Autowired private WebTestClient webTestClient;

    @MockBean private OrderManagementService service;

    @Test
    public void createOrderCreatesTheOrderCorrectly() {

        Order mockOrder = validOrder();
        when(service.saveOrder(mockOrder)).thenReturn(Mono.just(mockOrder));
        webTestClient
                .post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(mockOrder)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Order.class)
                .isEqualTo(mockOrder);
    }

    @Test
    public void createOrderFailsOrderCreationWhenNoItemsAreSupplied() {

        Order orderWithNoItems = invalidOrderWithNoItems();
        when(service.saveOrder(orderWithNoItems)).thenReturn(Mono.just(orderWithNoItems));
        webTestClient
                .post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderWithNoItems)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void createOrderFailsOrderCreationWhenInvalidItemQuantityIsSupplied() {

        Order orderWithItemHavingInvalidQuantity = invalidOrderWithItemHavingInvalidQuantity();
        when(service.saveOrder(orderWithItemHavingInvalidQuantity))
                .thenReturn(Mono.just(orderWithItemHavingInvalidQuantity));
        webTestClient
                .post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(orderWithItemHavingInvalidQuantity)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void getOrderCorrectlyReturnsOrderById() {

        Order mockOrder = validOrder();
        when(service.findById("1")).thenReturn(Mono.just(mockOrder));

        webTestClient
                .get()
                .uri("/orders/" + "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Order.class)
                .isEqualTo(mockOrder);

        verify(service).findById("1");
    }

    @Test
    public void whenFindAllIsCalledItReturnsAllOrders() {

        Order mockOrder = validOrder();
        when(service.findAll()).thenReturn(Flux.just(mockOrder));
        webTestClient
                .get()
                .uri("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Order.class)
                .contains(mockOrder);
    }

    private Order validOrder() {
        return new Order(
                "1",
                mockCustomer(),
                Arrays.asList(new Item("1", "item1", 2), new Item("2", "item2", 12)));
    }

    private Order invalidOrderWithNoItems() {
        return new Order("1", mockCustomer(), Collections.EMPTY_LIST);
    }

    private Order invalidOrderWithItemHavingInvalidQuantity() {
        return new Order(
                "1",
                mockCustomer(),
                Arrays.asList(new Item("1", "item1", 0), new Item("2", "item2", 12)));
    }

    private Customer mockCustomer() {
        return new Customer("1", "John", "Doe");
    }
}
