package com.example.order.management.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Order {

    String id;

    Customer customer;

    List<Item> items = new ArrayList<>();
}
