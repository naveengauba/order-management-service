package com.example.order.management.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    private Customer customer;

    private List<Item> items = new ArrayList<>();
}
