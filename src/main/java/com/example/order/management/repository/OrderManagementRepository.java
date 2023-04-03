package com.example.order.management.repository;

import com.example.order.management.domain.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderManagementRepository extends ReactiveMongoRepository<Order, String> {

}
