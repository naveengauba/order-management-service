package com.example.order.management.repository;

import com.example.order.management.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderManagementRepository extends ReactiveCrudRepository<Order, String> {

}
