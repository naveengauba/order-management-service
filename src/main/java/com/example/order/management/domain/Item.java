package com.example.order.management.domain;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String id;

    private String desc;

    private int quantity;
}
