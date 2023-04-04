package com.example.order.management.service;

import com.example.order.management.domain.Order;
import com.example.order.management.repository.OrderManagementRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderManagementService {

    private final OrderManagementRepository repository;

    public OrderManagementService(OrderManagementRepository repository) {
        this.repository = repository;
    }

    public Mono<Order> findById(String id) {
        return repository.findById(id);
    }

    public Flux<Order> findAll() {
        return repository.findAll();
    }

    public Mono<Order> saveOrder(Order order) {
        return repository.save(order);
    }
}
