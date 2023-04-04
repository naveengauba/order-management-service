package com.example.order.management.repository;

import com.example.order.management.domain.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderManagementRepository extends ReactiveMongoRepository<Order, String> {}
