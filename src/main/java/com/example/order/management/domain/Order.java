package com.example.order.management.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id private String id;

    private Customer customer;

    private List<Item> items = new ArrayList<>();
}
